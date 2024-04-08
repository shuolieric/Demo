package com.ls.demo.juc;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {

//        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//        CompletableFuture<Void> voidCompletableFuture = completableFuture.thenRun(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Run...");
//            }
//        });
//
//        completableFuture.complete("test");

//       CompletableFuture.<String>runAsync(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Test");
//            }
//        });

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行supply");
            return "success";
        }).thenApply(s -> {
            System.out.println("开始执行apply");
            return s.toUpperCase();
        });
        System.out.println(completableFuture.get());
        System.out.println("*************************************");


        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务1");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("结束执行任务1");
            return "aaaa";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("开始执行任务2");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("结束执行任务2");
            return "bbbb";
        }), (s1, s2) -> {
            return "s1: +" + s1 + ", s2: " + s2;
        });
        System.out.println(future.get());
        System.out.println("*************************************");

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            return "res1";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> s + s));
        System.out.println(completableFuture1.get());
        System.out.println("*************************************");


        CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
//            return "aa";
            throw new RuntimeException("exception when supply");
        });
        CompletableFuture<String> exceptionallyFuture = exceptionFuture.exceptionally((ex -> {
            System.out.println("Exception : " + ex.getMessage());
            return "exception";
        }));
        System.out.println(exceptionallyFuture.get());
        System.out.println("*************************************");

        CompletableFuture<String> exceptionFuture2 = CompletableFuture.supplyAsync(() -> {
            return "aa";
//            throw new RuntimeException("exception when supply");
        });
        CompletableFuture<String> handleFuture = exceptionFuture2.handle((s, ex) -> {
            if (ex != null) {
                System.out.println("Handle exception: " + ex.getMessage());
                return "handle exception";
            } else {
                return s;
            }
        });
        System.out.println(handleFuture.get());
        System.out.println("*************************************");



        System.in.read();

    }
}
