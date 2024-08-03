package com.ls.demo.algorithm.study.chapter01_sort;

import java.util.Arrays;

import static chapter01_sort.Common.swap;

public class SelectionSort {

    public static void selection_sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, minIndex, i);
        }
    }


    public static void main(String[] args) {
        int[] array = {10, 9, 5, 6, 7, 4, 3, 1, 2};
        selection_sort(array);
        System.out.println(Arrays.toString(array));
    }
}
