package com.ls.demo.rpc.enhanced.rpc;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorSystem;

public class RpcUtils {

    public static RpcService createRpcService(Configuration configuration) {

        String actorSystemName = configuration.getProperty("actor.system.name");

        Config config = ConfigFactory.load();

        ActorSystem actorSystem = ActorSystem.create(actorSystemName, config.getConfig(actorSystemName));

        return new RpcService(actorSystem);
    }
}
