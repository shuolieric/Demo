package com.ls.demo.algorithm.study.chapter03_datastructure.linklist;

public class DeleteGivenValue {
    public static Node deleteGivenValue(Node head, int target) {

        while (head != null) {
            if (head.value != target) {
                break;
            }
            head = head.next;
        }

        Node pre = head;
        Node cur = head;
        while (cur != null) {

            if (cur.value == target) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }

            cur = cur.next;
        }

        return head;
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

        Node res = deleteGivenValue(node01, 1);
        while (res != null) {
            System.out.println(res.value);
            res = res.next;
        }

    }
}
