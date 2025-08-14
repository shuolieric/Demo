package com.ls.demo.rpc.enhanced.taskmanager;

import com.ls.demo.rpc.enhanced.rpc.RpcGateway;

import java.util.concurrent.CompletableFuture;

public interface TaskExecutorGateway extends RpcGateway {

    String submitTask(String task);

    String queryTaskExecutorState();

    CompletableFuture<String> heartBeatFromJobManager(String payload);
}
