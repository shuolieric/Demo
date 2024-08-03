package com.ls.demo.algorithm.study.chapter04_mergesort;

import java.util.Arrays;

public class MergeSort {

    public static void merge_sort_recursive(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

       process(arr, 0, arr.length -1);
    }

    public static void merge_sort_iterative(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int length = arr.length;
        int step = 1;
        while (step < length) {
            int L = 0;
            while (L < length) {
                int M = L + step - 1;
                if (step > length - M) {
                    break;
                }
                int R = M + Math.min(step, length - M -1);
                merge(arr, L, M, R);
                L = M +1;
            }

            if (step > length >> 1) {
                break;
            }
            step <<= 1;
        }
    }

    private static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }

        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    private static void merge(int[] arr, int L, int mid, int R) {
        int p1 = L;
        int p2 = mid + 1;
        int[] temp = new int[R - L + 1];
        int i = 0;

        while (p1 <= mid && p2 <= R) {
            if (arr[p1] <= arr[p2]) {
                temp[i++] = arr[p1++];
            } else {
                temp[i++] = arr[p2++];
            }
        }

        while (p1 <= mid) {
            temp[i++] = arr[p1++];
        }

        while (p2 <= R) {
            temp[i++] = arr[p2++];
        }

        for (int j = 0; j < temp.length; j++) {
            arr[L + j] = temp[j];
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 6, 3, 2, 1, 7, 4, 9};
//        merge_sort_recursive(arr);
        merge_sort_iterative(arr);
        System.out.println(Arrays.toString(arr));
    }
}
