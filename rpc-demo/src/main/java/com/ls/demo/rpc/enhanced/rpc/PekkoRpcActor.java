package com.ls.demo.rpc.enhanced.rpc;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.japi.pf.ReceiveBuilder;
import org.apache.pekko.pattern.Patterns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class PekkoRpcActor<T extends RpcEndpoint> extends AbstractActor implements RpcActor {

    final T endPoint;

    public PekkoRpcActor(T endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Receive createReceive() {

        return ReceiveBuilder.create()
                .match(RpcInvoke.class, this::invokeRpc)
                .match(RunAsync.class, this::invokeRunAsync)
                .build();

    }

    private void invokeRunAsync(RunAsync runAsync) {
        runAsync.getRunnable().run();
    }

    private void invokeRpc(RpcInvoke rpcInvoke) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ActorRef sender = getSender();
        System.out.println("收到Rpc调用请求， 调用方: " + sender);

        String methodName = rpcInvoke.getMethodName();
        Class<?>[] parameterTypes = rpcInvoke.getParameterTypes();
        Object[] parameters = rpcInvoke.getParameters();

        Method method = endPoint.getClass().getMethod(methodName, parameterTypes);
        Object invokeResult = method.invoke(endPoint, parameters);

        if (Objects.equals(method.getReturnType(), Void.TYPE)) {
            System.out.println("没有返回值");
        } else if (invokeResult instanceof CompletableFuture) {

            CompletableFuture<Object> completableFuture = (CompletableFuture<Object>) invokeResult;

            Patterns.pipe(completableFuture, getContext().getDispatcher()).to(sender);
        } else {
            sender.tell(invokeResult, getSelf());
        }
    }
}
