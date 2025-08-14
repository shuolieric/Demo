package com.ls.demo.rpc.enhanced.rpc;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.pattern.Patterns;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class PekkoInvocationHandler implements InvocationHandler, RpcServer {

    ActorRef targetEndpointActorRef;

    public PekkoInvocationHandler(ActorRef targetEndpointActorRef) {
        this.targetEndpointActorRef = targetEndpointActorRef;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Class<?> declaringClass = method.getDeclaringClass();

        Object result = null;

        if (Objects.equals(declaringClass, MainThreadExecutable.class)
        || Objects.equals(declaringClass, RpcServer.class)) {
           return method.invoke(this, args);
        }

        //
        RpcInvoke rpcInvoke = new RpcInvoke(method.getName(), method.getParameterTypes(), args);


        if (Objects.equals(method.getReturnType(), Void.TYPE)) {
            this.targetEndpointActorRef.tell(rpcInvoke, ActorRef.noSender());
        } else {
            Future<Object> scalaFuture = Patterns.ask(targetEndpointActorRef, rpcInvoke, 2000);
            CompletableFuture<Object> completableFuture = new CompletableFuture<>();
            scalaFuture.onComplete(scalaTry -> {
                if (scalaTry.isSuccess()) {
                    completableFuture.complete(scalaTry.get());
                } else {
                    completableFuture.completeExceptionally(scalaTry.failed().get());
                }
                return null;
            }, ExecutionContext.global());

            if (Objects.equals(method.getReturnType(), CompletableFuture.class)) {
                result = completableFuture;
            } else {
                result = completableFuture.get();
            }

        }

        return result;
    }

    @Override
    public String getAddress() {
        return "";
    }

    @Override
    public void runAsync(Runnable runnable) {
        scheduleRunAsync(runnable, 0);
    }

    @Override
    public <V> CompletableFuture<V> callAsync(Callable<V> callable, Duration callTimeout) {
        return null;
    }

    @Override
    public void scheduleRunAsync(Runnable runnable, long delay) {
        this.targetEndpointActorRef.tell(new RunAsync(runnable, delay), ActorRef.noSender());
    }
}
