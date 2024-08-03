package com.ls.demo.algorithm.study.chapter03_datastructure.linklist;

public class ReverseLinkList {

    public static Node reverse(Node head) {

        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    public static void main(String[] args) {
        Node node01 = new Node(1);
        Node node02 = new Node(2);
        Node node03 = new Node(3);
        Node node04 = new Node(4);
        Node node05 = new Node(5);
        Node node06 = new Node(6);
        node01.next = node02;
        node02.next = node03;
        node03.next = node04;
        node04.next = node05;
        node05.next = node06;
        Node result = reverse(node01);
        while (result != null) {
            System.out.println(result.value);
            result = result.next;
        }
    }
}
