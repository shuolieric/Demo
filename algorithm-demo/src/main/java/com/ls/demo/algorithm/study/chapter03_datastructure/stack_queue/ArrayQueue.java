package com.ls.demo.algorithm.study.chapter03_datastructure.stack_queue;

public class ArrayQueue {

    private int[] queue;
    private int size;
    private int limit;

    private int push;
    private int pop;

    public ArrayQueue(int capacity) {
        this.queue = new int[capacity];
        this.size = 0;
        this.pop = 0;
        this.push = 0;
        this.limit = capacity;
    }

    public void add(int value) {
        if (this.size >= this.limit) {
            System.out.println("Queue is full, add failed");
        } else {
            this.queue[push] = value;
            this.size++;
            this.push = nextIndex(this.push);
        }
    }

    private int nextIndex(int index) {
        int next = index + 1;
        return next >= this.limit ? 0 : next;
    }

    public int poll() {
        if (this.size == 0) {
            System.out.println("Queue is empty, poll failed");
            return Integer.MIN_VALUE;
        } else {
            int res = this.queue[pop];
            this.size--;
            this.pop = nextIndex(this.pop);
            return res;
        }
    }

    public int length() {
        return this.size;
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(5);
        arrayQueue.add(1);
        arrayQueue.add(2);
        arrayQueue.add(3);
        arrayQueue.add(4);
        arrayQueue.add(5);

        for (int i = 0; i < 10; i++) {
            System.out.println(arrayQueue.poll());
        }
    }
}
