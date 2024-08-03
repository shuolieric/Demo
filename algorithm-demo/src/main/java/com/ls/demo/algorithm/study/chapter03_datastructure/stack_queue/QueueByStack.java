package com.ls.demo.algorithm.study.chapter03_datastructure.stack_queue;

import java.util.Stack;

public class QueueByStack {

    private Stack<Integer> dataStack;
    private Stack<Integer> helpStack;

    public QueueByStack() {
        this.dataStack = new Stack<>();
        this.helpStack = new Stack<>();
    }

    public void add(int val) {
        this.dataStack.push(val);
    }

    public int poll() {
        if (this.helpStack.isEmpty()) {
            this.transfer();
        }

        return this.helpStack.pop();
    }

    private void transfer() {
        while (!this.dataStack.isEmpty()) {
            this.helpStack.push(this.dataStack.pop());
        }
    }

    public int size() {
        return this.dataStack.size() + this.helpStack.size();
    }

    public static void main(String[] args) {
        QueueByStack queue = new QueueByStack();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        while (queue.size() > 0) {
            System.out.println(queue.poll());
        }
    }
}
