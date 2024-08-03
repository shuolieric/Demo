package com.ls.demo.algorithm.study.chapter01_sort;

public class BinarySearch_min {
    public static int min_search(int[] arr, int x) {
        if (arr == null) {
            return -1;
        }

        if (arr.length < 2) {
            return arr[0] == x ? 0 : -1;
        }

        int left = 0;
        int right = arr.length - 1;
        int t = -1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (arr[middle] >= x) {
                t = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return t;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 7, 7, 8, 9, 10};
        System.out.println(min_search(arr, 11));
    }
}
