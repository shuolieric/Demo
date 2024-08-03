package com.ls.demo.algorithm.study.chapter02_bitwish;

public class FindRightOne {
    // 怎么把一个int类型的数，提取出最右侧的1来
    public static int findRightOne(int x) {
        return x & (-x);
    }

    public static void main(String[] args) {
        System.out.println(findRightOne(6));
    }
}
