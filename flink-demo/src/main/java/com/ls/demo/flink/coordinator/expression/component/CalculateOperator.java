package com.ls.demo.flink.coordinator.expression.component;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.ls.demo.flink.coordinator.expression.event.*;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.jobgraph.tasks.TaskOperatorEventGateway;
import org.apache.flink.streaming.api.graph.StreamConfig;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.operators.Output;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.runtime.tasks.StreamTask;
import org.apache.flink.util.SerializedValue;

import java.util.HashMap;
import java.util.Map;

public class CalculateOperator extends AbstractStreamOperator<Double> implements OneInputStreamOperator<Event, Double> {

    TaskOperatorEventGateway coordinatorEventGateway;
    OperatorID operatorID;
    Integer subtaskId;
    Expression expression;

    @Override
    public void setup(StreamTask<?, ?> containingTask, StreamConfig config, Output<StreamRecord<Double>> output) {
        super.setup(containingTask, config, output);
        coordinatorEventGateway = containingTask.getEnvironment().getOperatorCoordinatorEventGateway();
    }

    @Override
    public void open() throws Exception {
        super.open();
        subtaskId = getRuntimeContext().getIndexOfThisSubtask();
    }

    @Override
    public void processElement(StreamRecord<Event> element) throws Exception {

        PartitionedEvent partitionedEvent = (PartitionedEvent) element.getValue();
        Event event = partitionedEvent.getEvent();

        if (event instanceof OperatorIdEvent) {
            operatorID = ((OperatorIdEvent) event).getOperatorID();
            System.out.println("[Calculate operator] Received operator id: " + operatorID.toHexString());
            coordinatorEventGateway.sendOperatorEventToCoordinator(operatorID,
                    new SerializedValue<>(new SubtaskRegEvent(subtaskId)));

        } else if (event instanceof FlushEvent) {
            System.out.println("[Calculate operator] Received flush event");
            coordinatorEventGateway.sendOperatorEventToCoordinator(operatorID,
                    new SerializedValue<>(new FlushSuccessEvent()));

        } else if (event instanceof DataEvent) {
            DataEvent dataEvent = (DataEvent) event;
            Double f1 = (double) dataEvent.getF1();
            Double f2 = (double) dataEvent.getF2();
            Double f3 = (double) dataEvent.getF3();
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("f1", f1);
            dataMap.put("f2", f2);
            dataMap.put("f3", f3);
            if (expression != null) {
                Double result = (Double) expression.execute(dataMap);
                output.collect(new StreamRecord<>(result));
            }

        } else if (event instanceof ExpressionEvent){
            ExpressionEvent expressionEvent = (ExpressionEvent) event;
            System.out.println("[Calculate operator] Received expression change event: " + expressionEvent);
            expression = AviatorEvaluator.compile(expressionEvent.getExpression());
        }
    }
}
