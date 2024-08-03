package com.ls.demo.algorithm.study.chapter03_datastructure.stack_queue;

public class DoubleEndsQueueToStackAndQueue {

    public static class  Node<T> {
        T data;
        Node<T> next;
        Node<T> last;

        public Node(T data) {
            this.data = data;
        }
    }

    public static class DoubleEndsQueue<T> {
        private Node<T> head;
        private Node<T> tail;

        public void addFromHead(T val) {

            Node<T> cur = new Node<>(val);

            if (head == null) {
                this.head = cur;
                this.tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }

        }

        public void addFromBottom(T val) {

            Node<T> cur = new Node<>(val);

            if (tail == null) {
                this.tail = cur;
                this.head = cur;
            } else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        public T popFromHead() {

            if (this.head == null) {
                return null;
            }

            Node<T> cur = this.head;
            if (this.head == this.tail) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = this.head.next;
                cur.next = null;
                this.head.last = null;
            }

            return cur.data;
        }

        public T popFromBottom() {
            if (this.tail == null) {
                return null;
            }

            Node<T> cur = this.tail;
            if (this.head == this.tail) {
                this.head = null;
                this.tail = null;
            } else {
                this.tail = this.tail.last;
                cur.last = null;
                this.tail.next = null;
            }

            return cur.data;
        }

        public T peekFromHead() {
            if (this.head == null) {
                return null;
            }
            return this.head.data;
        }

        public T peekFromBottom() {
            if (this.tail == null) {
                return null;
            }
            return this.tail.data;
        }

        public boolean isEmpty() {
            return this.head == null;
        }
    }

    public static class MyStack<T> {
        private DoubleEndsQueue<T> queue;

        public MyStack() {
            this.queue = new DoubleEndsQueue<>();
        }

        public void push(T val) {
            this.queue.addFromHead(val);
        }

        public T pop() {
            return this.queue.popFromHead();
        }

        public T peek() {
            return this.queue.popFromHead();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    public static class MyQueue<T> {
        private DoubleEndsQueue<T> queue;

        public MyQueue() {
            this.queue = new DoubleEndsQueue<>();
        }

        public void add(T val) {
            this.queue.addFromHead(val);
        }

        public T poll() {
            return this.queue.popFromBottom();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

        System.out.println("===============================");

        MyQueue<Integer> queue = new MyQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
