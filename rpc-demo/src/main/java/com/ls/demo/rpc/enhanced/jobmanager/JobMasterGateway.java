package com.ls.demo.rpc.enhanced.jobmanager;

import com.ls.demo.rpc.enhanced.rpc.RpcGateway;

import java.util.concurrent.ExecutionException;

public interface JobMasterGateway extends RpcGateway {

    String registerTaskExecutor(String taskExecutorAddress, String taskExecutorId) throws ExecutionException, InterruptedException;

}
