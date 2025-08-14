package com.ls.demo.rpc.enhanced.taskmanager;

import com.ls.demo.rpc.enhanced.jobmanager.JobMasterGateway;
import com.ls.demo.rpc.enhanced.rpc.RpcEndpoint;
import com.ls.demo.rpc.enhanced.rpc.RpcService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TaskExecutor extends RpcEndpoint implements TaskExecutorGateway {

    public TaskExecutor(RpcService rpcService, String endpointId) throws ExecutionException, InterruptedException {
        super(rpcService, endpointId);

        registerTaskExecutor();
    }

    @Override
    public String submitTask(String task) {
        return "submit task " + task;
    }

    @Override
    public String queryTaskExecutorState() {
        System.out.println("收到job-master的状态查询请求");
        return "state";
    }

    @Override
    public CompletableFuture<String> heartBeatFromJobManager(String payload) {

        System.out.println("taskExecutor收到一次心跳请求: " + payload);


        return CompletableFuture.supplyAsync(() -> getEndpointId() + "-ok");
    }

    public void registerTaskExecutor() throws ExecutionException, InterruptedException {
        JobMasterGateway jobMaster = rpcService.connect("pekko://jobmanager@127.0.0.1:17338/user/job_master", JobMasterGateway.class);
        String registerResponse = jobMaster.registerTaskExecutor(rpcService.getAddress(this.getEndpointId()), this.getEndpointId());
        System.out.println("收到注册响应： " + registerResponse);
    }
}
