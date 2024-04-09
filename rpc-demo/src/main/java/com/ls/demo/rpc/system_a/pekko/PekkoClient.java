package com.ls.demo.rpc.system_a.pekko;

import com.ls.demo.rpc.common.InfoQuerier;
import com.ls.demo.rpc.common.RpcRequest;
import com.ls.demo.rpc.common.RpcResponse;
import com.ls.demo.rpc.common.UserQuerier;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSelection;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.pattern.Patterns;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class PekkoClient {

    private final ActorSystem actorSystem;

    public PekkoClient() {
        HashMap<String, Object> overrides = new HashMap<>();
        overrides.put("pekko.remote.artery.canonical.port", 17336);
        Config config = ConfigFactory.parseMap(overrides).withFallback(ConfigFactory.load());

        this.actorSystem = ActorSystem.create("rpc-client", config);
    }

    public ActorRef connect(String host, int port) throws ExecutionException, InterruptedException {
        ActorSelection actorSelection = this.actorSystem.actorSelection("pekko://rpc-server@" + host + ":" + port + "/user/server-actor");
        return actorSelection.resolveOne(Duration.ofSeconds(2)).toCompletableFuture().get();
    }

    public <T> Object getInstance(Class<T> clz, ActorRef actorRef) {
        return Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest(
                        clz.getName(),
                        method.getName(),
                        method.getParameterTypes(),
                        args);
                return handleRequest(actorRef, request);
            }
        });
    }

    public Object handleRequest(ActorRef actorRef, RpcRequest request) throws ExecutionException, InterruptedException {
        CompletionStage<Object> res = Patterns.ask(actorRef, request, Duration.ofSeconds(2));
        RpcResponse response = (RpcResponse) res.toCompletableFuture().get();
        return response.getData();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        PekkoClient client = new PekkoClient();
        ActorRef actorRef = client.connect("127.0.0.1", 17338);

        InfoQuerier infoQuerier = (InfoQuerier) client.getInstance(InfoQuerier.class, actorRef);
        UserQuerier userQuerier = (UserQuerier) client.getInstance(UserQuerier.class, actorRef);

        System.out.println(infoQuerier.getBalance(1));
        System.out.println(userQuerier.getUserName());
    }
}
