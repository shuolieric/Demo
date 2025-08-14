package com.ls.demo.rpc.enhanced.rpc;

import org.apache.pekko.actor.*;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class RpcService {

    private ActorSystem actorSystem;
    private Map<String, ActorRef> actors = new HashMap<>();

    public RpcService(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public <G extends RpcGateway> G connect(String address, Class<G> gatewayClass) throws ExecutionException, InterruptedException {
        ActorSelection actorSelection = this.actorSystem.actorSelection(address);
        CompletableFuture<ActorRef> completableFuture = actorSelection.resolveOne(Duration.ofSeconds(1)).toCompletableFuture();

        ActorRef remorteTargetActorRef = completableFuture.get();
        PekkoInvocationHandler h = new PekkoInvocationHandler(remorteTargetActorRef);

        @SuppressWarnings("unchecked")
        G gateway = (G) Proxy.newProxyInstance(RpcService.class.getClassLoader(), new Class[]{gatewayClass}, h);

        return gateway;
    }

    public <C extends RpcEndpoint & RpcGateway> RpcServer startServer(C rpcEndpoint) {

        ActorRef selfActorRef = this.actorSystem.actorOf(Props.create(PekkoRpcActor.class, rpcEndpoint), rpcEndpoint.getEndpointId());
        this.actors.put(rpcEndpoint.getEndpointId(), selfActorRef);

        Class<?>[] interfaces = rpcEndpoint.getClass().getInterfaces();
        HashSet<Class<?>> classes = new HashSet<>(Arrays.asList(interfaces));
        classes.add(RpcServer.class);
        Class<?>[] array = classes.toArray(new Class<?>[0]);


        PekkoInvocationHandler h = new PekkoInvocationHandler(selfActorRef);

        RpcServer rpcServer = (RpcServer) Proxy.newProxyInstance(RpcService.class.getClassLoader(), array, h);

        return rpcServer;
    }

    public String getAddress(String endpointId) {
        ActorRef actorRef = this.actors.get(endpointId);
        Address defaultAddress = actorSystem.provider().getDefaultAddress();
        return actorRef.path().toStringWithAddress(defaultAddress);
    }

}
