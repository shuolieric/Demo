package com.ls.demo.algorithm.study.chapter02_bitwish;

import java.util.Arrays;

public class Swap {
    //如何不用额外变量交换两个数
    public static void swap(int[] arr, int i, int j) {
        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        swap(arr, 1, 2);
        System.out.println(Arrays.toString(arr));
    }
}
