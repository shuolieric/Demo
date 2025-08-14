package com.ls.demo.rpc.enhanced.jobmanager;

import com.ls.demo.rpc.enhanced.rpc.MainThreadExecutable;
import com.ls.demo.rpc.enhanced.rpc.RpcEndpoint;
import com.ls.demo.rpc.enhanced.taskmanager.TaskExecutorGateway;

import java.util.concurrent.CompletableFuture;

public class HeartBeatMonitor {
    private TaskExecutorGateway taskExecutor;
    private String resourceId;
    private JobMaster.HeartBeatListener heartBeatListener;
    private long lastHeartBeatTime;
    private int failureCount;

    private RpcEndpoint.MainThreadExecutor executor;

    public HeartBeatMonitor(TaskExecutorGateway taskExecutor, String resourceId, JobMaster.HeartBeatListener heartBeatListener, RpcEndpoint.MainThreadExecutor executor) {
        this.taskExecutor = taskExecutor;
        this.resourceId = resourceId;
        this.heartBeatListener = heartBeatListener;
        this.executor = executor;
    }

    public void handleHeartBeatFailure() {
        this.failureCount++;

        System.out.println("心跳请求失败： " + failureCount);
        if (this.failureCount >= 3) {
            heartBeatListener.notifyHeartBeatFailure(resourceId);
        }
    }
    public void requestHeartBeat() {
        CompletableFuture<String> completableFuture = taskExecutor.heartBeatFromJobManager("你还在吗");
        completableFuture.whenCompleteAsync((result, ex) -> {
            if (ex != null) {
                handleHeartBeatFailure();
            } else {
                System.out.println("收到心跳回应： " + result);
                failureCount = 0;
                lastHeartBeatTime = System.currentTimeMillis();

            }
        }, this.executor);
    }
}
