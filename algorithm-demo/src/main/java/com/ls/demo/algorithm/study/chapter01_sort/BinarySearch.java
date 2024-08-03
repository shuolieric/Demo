package com.ls.demo.algorithm.study.chapter01_sort;

public class BinarySearch {

    public static int binary_search(int[] arr, int x) {
        if (arr == null) {
            return -1;
        }

        if (arr.length < 2) {
            return arr[0] == x ? 0 : -1;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (arr[middle] == x) {
                return middle;
            } else if (arr[middle] > x) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 7, 7, 8, 9, 10};
        System.out.println(binary_search(arr, 10));
    }
}
