package com.ls.demo.rpc.enhanced.jobmanager;

import com.ls.demo.rpc.enhanced.rpc.Configuration;
import com.ls.demo.rpc.enhanced.rpc.RpcService;
import com.ls.demo.rpc.enhanced.rpc.RpcUtils;

public class JobManagerRunner {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setProperty("actor.system.name", "jobmanager");

        RpcService rpcService = RpcUtils.createRpcService(configuration);

        JobMaster jobMaster = new JobMaster(rpcService, "job_master");

    }
}
