package com.ls.demo.rpc.enhanced.jobmanager;

import com.ls.demo.rpc.enhanced.rpc.RpcEndpoint;
import com.ls.demo.rpc.enhanced.rpc.RpcService;
import com.ls.demo.rpc.enhanced.taskmanager.TaskExecutorGateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class JobMaster extends RpcEndpoint implements JobMasterGateway {

    private Map<String, TaskExecutorRegistry> registeredExecutors = new ConcurrentHashMap<>();

    private HeartBeatManager heartBeatManager;
    public JobMaster(RpcService rpcService, String endpointId) {
        super(rpcService, endpointId);

        this.heartBeatManager = new HeartBeatManager(new HeartBeatListener(), getMainThreadExecutor());
    }


    public String queryTaskExecutorState(String resourceId) {
        TaskExecutorGateway taskExecutor = registeredExecutors.get(resourceId).getTaskExecutor();
        return taskExecutor.queryTaskExecutorState();
    }

    public String submitTask(String resourceId, String task) {
        TaskExecutorGateway taskExecutor = registeredExecutors.get(resourceId).getTaskExecutor();
        return taskExecutor.submitTask(task);
    }


    @Override
    public String registerTaskExecutor(String taskExecutorAddress, String resourceId) throws ExecutionException, InterruptedException {

        if (registeredExecutors.containsKey(resourceId)) {
            return "重复注册";
        }


        String result = registerInternal(taskExecutorAddress, resourceId);

        TaskExecutorGateway taskExecutor = registeredExecutors.get(resourceId).getTaskExecutor();

        String state = taskExecutor.queryTaskExecutorState();
        System.out.println("查询到刚注册的taskExecutor注册信息： " + state);

        heartBeatManager.monitorHeartBeatTarget(resourceId, taskExecutor);


        return result;
    }

    public String registerInternal(String taskExecutorAddress, String resourceId) throws ExecutionException, InterruptedException {

        System.out.println("收到taskExecutor注册信息： " + taskExecutorAddress);

        TaskExecutorGateway taskExecutor = rpcService.connect(taskExecutorAddress, TaskExecutorGateway.class);
        registeredExecutors.put(resourceId, new TaskExecutorRegistry(resourceId, taskExecutorAddress, taskExecutor));

        return "注册taskExecutor成功";
    }

    public void disconnectTaskManager(String resourceId) {

        System.out.println(resourceId + ", 心跳失败次数超过阈值，从注册池中移除");
        this.registeredExecutors.remove(resourceId);
        this.heartBeatManager.removeHeartBeatTarget(resourceId);
    }

    public class HeartBeatListener {

        public void notifyHeartBeatFailure(String resourceId) {
            disconnectTaskManager(resourceId);
        }
    }

}
