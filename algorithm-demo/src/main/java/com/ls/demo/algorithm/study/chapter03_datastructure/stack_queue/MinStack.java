package com.ls.demo.algorithm.study.chapter03_datastructure.stack_queue;

import java.util.Stack;

public class MinStack {

    private Stack<Integer> dataStack;
    private Stack<Integer> minStack;

    public MinStack() {
        this.dataStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    public void push(int val) {
        this.dataStack.push(val);
        if (this.minStack.isEmpty() || this.minStack.peek() >= val) {
            this.minStack.push(val);
        } else {
            this.minStack.push(this.minStack.peek());
        }
    }

    public int pop() {
        if (this.dataStack.isEmpty()) {
            System.out.println("Stack is empty, get failed");
            return Integer.MIN_VALUE;
        }

        this.minStack.pop();
        return this.dataStack.pop();
    }

    public int peek() {
        return this.dataStack.peek();
    }

    public int getMin() {
        return this.minStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-1);
        minStack.push(2);
        minStack.push(3);
        minStack.push(-2);
        minStack.push(4);

        System.out.println(minStack.getMin());
        System.out.println("Pop: " + minStack.pop());
        System.out.println(minStack.getMin());
        System.out.println("Pop: " + minStack.pop());
        System.out.println(minStack.getMin());
        System.out.println("Pop: " + minStack.pop());
        System.out.println(minStack.getMin());
    }
}

