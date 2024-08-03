package com.ls.demo.algorithm.study.chapter01_sort;

import java.util.Arrays;

import static chapter01_sort.Common.swap;

public class InsertSort {

    public static void insert_sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }
    public static void main(String[] args) {

        int[] array = {10, 9, 5, 6, 7, 4, 3, 1, 2};
        insert_sort(array);
        System.out.println(Arrays.toString(array));

    }
}
