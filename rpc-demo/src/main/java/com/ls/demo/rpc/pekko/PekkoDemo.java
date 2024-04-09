package com.ls.demo.rpc.pekko;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.apache.pekko.japi.pf.ReceiveBuilder;
import org.apache.pekko.pattern.Patterns;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class PekkoDemo {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("actor-system");
        ActorRef actorA = actorSystem.actorOf(Props.create(MyActor.class), "actorA");
        actorA.tell(new Start(), ActorRef.noSender());
        System.out.println("=============");


        ActorSystem actorSystem1 = ActorSystem.create("actor-system-1");
        ActorRef act1 = actorSystem1.actorOf(Props.create(MyActor.class), "act1");
        ActorRef act2 = actorSystem1.actorOf(Props.create(MyActor.class), "act2");
        act1.tell(new ProcessData(1,2,"+"), act2);
        CompletionStage<Object> ask = Patterns.ask(act1, new ProcessData(2, 2, "-"), Duration.ofSeconds(100));
        ask.thenAccept(o -> {
            System.out.println("ask: " + o);
        });
    }

}

class MyActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(Start.class, this::start)
                .match(ProcessData.class, this::process)
                .match(Integer.class, i -> {
                    System.out.println("result from: " + getSender() + " is " + i);
                })
                .build();
    }

    public void start(Start start) {
        System.out.println(Thread.currentThread().getName() + ": start");
    }

    public int process(ProcessData processData) {
        int res = 0;
        switch (processData.getOp()) {
            case "+":
                res = processData.getA() + processData.getB();
                break;
            case "-":
                res = processData.getA() - processData.getB();
                break;
            case "*":
                res = processData.getA() * processData.getB();
                break;
            default:
        }
        getSender().tell(res, getSelf());
        return res;
    }
}

class Start {

}


@Data
@AllArgsConstructor
class ProcessData {
    private int a;
    private int b;
    private String op;
}