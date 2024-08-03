package com.ls.demo.algorithm.study.chapter03_datastructure.stack_queue;

public class ArrayStack {

    private int[] stack;
    private int size;
    private int limit;

    public ArrayStack(int length) {
        this.stack = new int[length];
        this.size = 0;
        this.limit = length;
    }

    public int pop() {
        if (this.size == 0) {
            System.out.println("Stack is empty, pop failed");
            return Integer.MIN_VALUE;
        } else {
            return this.stack[--size];
        }
    }

    public int peek() {
        if (this.size == 0) {
            System.out.println("Stack is empty, pop failed");
            return Integer.MIN_VALUE;
        } else {
            int index = size - 1;
            return this.stack[index];
        }
    }

    public void push(int data) {
        if (this.size >= this.limit) {
            System.out.println("Stack is full, push failed");
        } else {
            this.stack[size++] = data;
        }
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(5);
        stack.push(1);
        System.out.println(stack.peek());
        stack.push(2);
        System.out.println(stack.peek());
        stack.push(3);
        System.out.println(stack.peek());
        stack.push(4);
        System.out.println(stack.peek());
        stack.push(5);
        System.out.println(stack.peek());

        System.out.println("=============================");
        for (int i = 0; i < 10; i++) {
            System.out.println(stack.pop());
        }
    }
}
