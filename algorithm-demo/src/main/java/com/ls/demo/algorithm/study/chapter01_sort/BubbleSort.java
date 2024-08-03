package com.ls.demo.algorithm.study.chapter01_sort;


import java.util.Arrays;

import static chapter01_sort.Common.swap;

public class BubbleSort {

    public static void bubble_sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length -1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {10, 9, 5, 6, 7, 4, 3, 1, 2};
        bubble_sort(array);
        System.out.println(Arrays.toString(array));

    }
}
