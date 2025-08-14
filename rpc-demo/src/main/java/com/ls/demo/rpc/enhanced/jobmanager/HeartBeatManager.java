package com.ls.demo.rpc.enhanced.jobmanager;

import com.ls.demo.rpc.enhanced.rpc.MainThreadExecutable;
import com.ls.demo.rpc.enhanced.rpc.RpcEndpoint;
import com.ls.demo.rpc.enhanced.taskmanager.TaskExecutorGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartBeatManager implements Runnable {

    Map<String, HeartBeatMonitor> heartBeatMonitorMap = new HashMap<>();

    RpcEndpoint.MainThreadExecutor executor;

    JobMaster.HeartBeatListener heartBeatListener;

    public HeartBeatManager(JobMaster.HeartBeatListener heartBeatListener, RpcEndpoint.MainThreadExecutor executor) {

        this.heartBeatListener = heartBeatListener;

        this.executor = executor;

        this.executor.schedule(this, 0, TimeUnit.SECONDS);
    }

    @Override
    public void run() {

        System.out.println("心跳调度......." + System.currentTimeMillis());

        for (Map.Entry<String, HeartBeatMonitor> entry : heartBeatMonitorMap.entrySet()) {

            HeartBeatMonitor heartBeatMonitor = entry.getValue();
            heartBeatMonitor.requestHeartBeat();
        }

        this.executor.schedule(this, 1, TimeUnit.SECONDS);


    }

    public void monitorHeartBeatTarget(String resourceId, TaskExecutorGateway taskExecutor) {
        HeartBeatMonitor heartBeatMonitor = new HeartBeatMonitor(taskExecutor, resourceId, heartBeatListener, executor);
        heartBeatMonitorMap.put(resourceId, heartBeatMonitor);
    }

    public void removeHeartBeatTarget(String resourceId) {
        heartBeatMonitorMap.remove(resourceId);
    }
}
