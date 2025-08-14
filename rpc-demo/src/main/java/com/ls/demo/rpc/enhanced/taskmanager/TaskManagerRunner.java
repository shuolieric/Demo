package com.ls.demo.rpc.enhanced.taskmanager;

import com.ls.demo.rpc.enhanced.jobmanager.JobMaster;
import com.ls.demo.rpc.enhanced.rpc.Configuration;
import com.ls.demo.rpc.enhanced.rpc.RpcService;
import com.ls.demo.rpc.enhanced.rpc.RpcUtils;

import java.util.concurrent.ExecutionException;

public class TaskManagerRunner {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Configuration configuration = new Configuration();
        configuration.setProperty("actor.system.name", "taskmanager");

        RpcService rpcService = RpcUtils.createRpcService(configuration);

        TaskExecutor taskExecutor = new TaskExecutor(rpcService, "task-executor");
    }
}
