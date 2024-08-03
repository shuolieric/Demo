package com.ls.demo.algorithm.study.chapter03_datastructure.stack_queue;

import java.util.LinkedList;
import java.util.Queue;

public class StackByQueue {

    private Queue<Integer> dataQueue;
    private Queue<Integer> helpQueue;

    private int size;

    public StackByQueue() {
        this.dataQueue = new LinkedList<>();
        this.helpQueue = new LinkedList<>();
        this.size = 0;
    }

    public void push(int val) {
        this.dataQueue.add(val);
        this.size++;
    }

    public int pop() {
        this.transfer();
        int res = this.dataQueue.poll();
        Queue<Integer> temp = this.dataQueue;
        this.dataQueue = this.helpQueue;
        this.helpQueue = temp;
        this.size--;
        return res;
    }

    public int peek() {
        this.transfer();
        int res = this.dataQueue.poll();
        Queue<Integer> temp = this.dataQueue;
        this.dataQueue = this.helpQueue;
        this.helpQueue = temp;
        this.dataQueue.add(res);
        return res;
    }

    private void transfer() {
        while (this.dataQueue.size() > 1) {
            this.helpQueue.add(this.dataQueue.poll());
        }
    }

    public static void main(String[] args) {
        StackByQueue stackByQueue = new StackByQueue();

        stackByQueue.push(1);
        System.out.println("peek: " + stackByQueue.peek());
        stackByQueue.push(2);
        System.out.println("peek: " + stackByQueue.peek());
        stackByQueue.push(3);
        System.out.println("peek: " + stackByQueue.peek());
        stackByQueue.push(4);
        System.out.println("peek: " + stackByQueue.peek());
        stackByQueue.push(5);
        System.out.println("peek: " + stackByQueue.peek());

        while (stackByQueue.size > 0) {
            System.out.println(stackByQueue.pop());
        }
    }
}
