package com.ls.demo.rpc.enhanced.rpc;

import java.util.concurrent.*;

public class RpcEndpoint implements RpcGateway {

    protected RpcService rpcService;
    protected RpcServer rpcServer;

    private String endpointId;
    private MainThreadExecutor mainThreadExecutor;

    public RpcEndpoint(RpcService rpcService, String endpointId) {
        this.endpointId = endpointId;
        this.rpcService = rpcService;

        this.rpcServer = rpcService.startServer(this);

        this.mainThreadExecutor = new MainThreadExecutor(this.rpcServer);

    }

    public MainThreadExecutor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public static class MainThreadExecutor implements Executor {

        MainThreadExecutable rpcServer;
        ScheduledExecutorService mainThreadExecutor;
        public MainThreadExecutor(MainThreadExecutable rpcServer) {
            this.rpcServer = rpcServer;
            this.mainThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        }

        @Override
        public void execute(Runnable runnable) {

            mainThreadExecutor.execute(() -> {
                rpcServer.runAsync(runnable);
            });
        }

        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return mainThreadExecutor.schedule(() -> {
                rpcServer.runAsync(command);
            }, delay, unit);
        }

    }
}
