package com.ls.demo.algorithm.study.chapter03_datastructure.linklist;

public class ReverseDoubleLinkList {

    public static DoubleNode reverse(DoubleNode head) {

        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }

        return pre;

    }

    public static void main(String[] args) {
        DoubleNode node01 = new DoubleNode(1);
        DoubleNode node02 = new DoubleNode(2);
        DoubleNode node03 = new DoubleNode(3);
        DoubleNode node04 = new DoubleNode(4);
        DoubleNode node05 = new DoubleNode(5);
        DoubleNode node06 = new DoubleNode(6);

        node01.next = node02;
        node02.next = node03;
        node03.next = node04;
        node04.next = node05;
        node05.next = node06;

        node06.last = node05;
        node05.last = node04;
        node04.last = node03;
        node03.last = node02;
        node02.last = node01;

        DoubleNode result = reverse(node01);
        while (result != null) {
            System.out.println(result.value);
            result = result.next;
        }
    }
}
