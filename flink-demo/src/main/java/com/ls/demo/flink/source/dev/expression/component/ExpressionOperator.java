package com.ls.demo.flink.source.dev.expression.component;

import com.ls.demo.flink.source.dev.expression.dto.ExpressionChangeRequest;
import com.ls.demo.flink.source.dev.expression.dto.ReleaseBlockRequest;
import com.ls.demo.flink.source.dev.expression.event.*;
import org.apache.flink.runtime.jobgraph.tasks.TaskOperatorEventGateway;
import org.apache.flink.runtime.operators.coordination.CoordinationResponse;
import org.apache.flink.runtime.operators.coordination.OperatorEvent;
import org.apache.flink.runtime.operators.coordination.OperatorEventHandler;
import org.apache.flink.streaming.api.graph.StreamConfig;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.operators.Output;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.runtime.tasks.StreamTask;
import org.apache.flink.util.SerializedValue;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExpressionOperator extends AbstractStreamOperator<Event>
        implements OneInputStreamOperator<Event, Event>, OperatorEventHandler {

    TaskOperatorEventGateway coordinatorEventGateway;
    int downstreamParallelism;

    public ExpressionOperator(int downstreamParallelism) {
        this.downstreamParallelism = downstreamParallelism;
    }

    @Override
    public void setup(StreamTask<?, ?> containingTask, StreamConfig config, Output<StreamRecord<Event>> output) {
        super.setup(containingTask, config, output);
        coordinatorEventGateway = containingTask.getEnvironment().getOperatorCoordinatorEventGateway();
    }

    @Override
    public void open() throws Exception {
        super.open();
        broadcast(new OperatorIdEvent(getOperatorID()));
    }

    @Override
    public void processElement(StreamRecord<Event> streamRecord) throws Exception {

        Event event = streamRecord.getValue();
        if (event instanceof ExpressionEvent) {
            // 1. Send expression to coordinator
            ExpressionChangeRequest request = new ExpressionChangeRequest();
            coordinatorEventGateway.sendRequestToCoordinator(getOperatorID(), new SerializedValue<>(request));

            // 2. broadcast flush to downstream
            broadcast(new FlushEvent());

            // 3. request release from coordinator
            requestRelease();

            // 4. broadcast expression to downstream
            broadcast(event);

        } else if (event instanceof DataEvent) {

            partitionedById(event);
        } else {
            System.out.println("Unknown event: " + event);
        }
    }

    private void requestRelease() throws IOException, InterruptedException, ExecutionException {
        ReleaseBlockRequest releaseBlockRequest = new ReleaseBlockRequest();
        CompletableFuture<CoordinationResponse> futureResult = coordinatorEventGateway.sendRequestToCoordinator(getOperatorID(), new SerializedValue<>(releaseBlockRequest));
        futureResult.get();
    }

    private void broadcast(Event event) {
        for (int i = 0; i < downstreamParallelism; i++) {
            PartitionedEvent partitionedEvent = new PartitionedEvent(i, event);
            output.collect(new StreamRecord<>(partitionedEvent));
        }
    }

    private void partitionedById(Event event) {
        int partitionedId = event.hashCode() % downstreamParallelism & Integer.MAX_VALUE;
        PartitionedEvent partitionedEvent = new PartitionedEvent(partitionedId, event);
        output.collect(new StreamRecord<>(partitionedEvent));
    }

    @Override
    public boolean hasKeyContext() {
        return super.hasKeyContext();
    }

    @Override
    public void setKeyContextElement(StreamRecord<Event> record) throws Exception {
        OneInputStreamOperator.super.setKeyContextElement(record);
    }

    @Override
    public void handleOperatorEvent(OperatorEvent evt) {
        if (evt instanceof CoordinatorEvent) {
            System.out.println("[ExpressionOperator] Received coordinatorEvent");
        }
    }
}
