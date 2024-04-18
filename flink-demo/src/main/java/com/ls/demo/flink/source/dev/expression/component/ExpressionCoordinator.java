package com.ls.demo.flink.source.dev.expression.component;

import com.ls.demo.flink.source.dev.expression.dto.ExpressionChangeRequest;
import com.ls.demo.flink.source.dev.expression.dto.ReleaseBlockRequest;
import com.ls.demo.flink.source.dev.expression.dto.SuccessResponse;
import com.ls.demo.flink.source.dev.expression.event.SubtaskRegEvent;
import com.ls.demo.flink.source.dev.expression.event.FlushSuccessEvent;
import org.apache.flink.runtime.operators.coordination.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ExpressionCoordinator implements OperatorCoordinator, CoordinationRequestHandler {

    private Map<Integer, Object> subtasks = new HashMap<>();
    private int flushSuccessCount = 0;
    CompletableFuture<CoordinationResponse> responseFuture = new CompletableFuture<>();

    @Override
    public void start() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void handleEventFromOperator(int subtask, int attemptNumber, OperatorEvent event) throws Exception {

        if (event instanceof SubtaskRegEvent) {
            SubtaskRegEvent subtaskRegEvent = (SubtaskRegEvent) event;
            System.out.println("[Coordinator] Received subtask reg, subtaskId: " +  subtaskRegEvent.getSubtaskId());
            this.subtasks.put(subtaskRegEvent.getSubtaskId(), "");
        } else if (event instanceof FlushSuccessEvent) {
            System.out.println("[Coordinator] Received flush success event");
            if (++flushSuccessCount >= this.subtasks.keySet().size()) {
                this.responseFuture.complete(new SuccessResponse());
                this.flushSuccessCount = 0;
            }
        }
    }

    @Override
    public CompletableFuture<CoordinationResponse> handleCoordinationRequest(CoordinationRequest request) {

        if (request instanceof ExpressionChangeRequest) {
            System.out.println("[Coordinator] Received expression change request");
            return requestExpressionChange((ExpressionChangeRequest) request);
        } else if (request instanceof ReleaseBlockRequest) {
            System.out.println("[Coordinator] Received release block request");
            return requestRelease((ReleaseBlockRequest) request);
        }
        return CompletableFuture.completedFuture(new SuccessResponse());
    }

    private CompletableFuture<CoordinationResponse> requestRelease(ReleaseBlockRequest request) {
        return this.responseFuture;
    }

    private CompletableFuture<CoordinationResponse> requestExpressionChange(ExpressionChangeRequest request) {
        return CompletableFuture.completedFuture(new SuccessResponse());
    }

    @Override
    public void checkpointCoordinator(long checkpointId, CompletableFuture<byte[]> resultFuture) throws Exception {

    }

    @Override
    public void notifyCheckpointComplete(long checkpointId) {

    }

    @Override
    public void resetToCheckpoint(long checkpointId, @Nullable byte[] checkpointData) throws Exception {

    }

    @Override
    public void subtaskReset(int subtask, long checkpointId) {

    }

    @Override
    public void executionAttemptFailed(int subtask, int attemptNumber, @Nullable Throwable reason) {

    }

    @Override
    public void executionAttemptReady(int subtask, int attemptNumber, SubtaskGateway gateway) {

    }
}
