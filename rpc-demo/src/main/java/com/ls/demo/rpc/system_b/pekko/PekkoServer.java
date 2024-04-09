package com.ls.demo.rpc.system_b.pekko;

import com.ls.demo.rpc.common.RpcRequest;
import com.ls.demo.rpc.common.RpcResponse;
import com.ls.demo.rpc.system_b.InfoQuerierImpl;
import com.ls.demo.rpc.system_b.UserQuerierImpl;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.apache.pekko.japi.pf.ReceiveBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PekkoServer extends AbstractActor {

    public static void main(String[] args) {

        Config config = ConfigFactory.load();
        ActorSystem actorSystem = ActorSystem.create("rpc-server", config);
        actorSystem.actorOf(Props.create(PekkoServer.class), "server-actor");
    }

    private final Map<String, Object> servicesMap;

    public PekkoServer() {
        this.servicesMap = new HashMap<>();
        this.servicesMap.put("com.ls.demo.rpc.common.InfoQuerier", new InfoQuerierImpl());
        this.servicesMap.put("com.ls.demo.rpc.common.UserQuerier", new UserQuerierImpl());
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(RpcRequest.class, this::processRequest)
                .build();
    }

    public void processRequest(RpcRequest request) {
        System.out.println("get request from: " + getSender());

        String interfaceName = request.getInterfaceName();
        String functionName = request.getFunctionName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] args = request.getArgs();

        Object o = this.servicesMap.get(interfaceName);
        try {
            Method method = o.getClass().getMethod(functionName, parameterTypes);
            Object res = method.invoke(o, args);
            getSender().tell(new RpcResponse(res, "success"), getSelf());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
