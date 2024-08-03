package com.ls.demo.algorithm.study.chapter01_sort;

public class BinarySearch_max {

    public static int max_search(int[] arr, int x) {
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
            int mid = left + ((right - left) >> 1);
            if (arr[mid] <= x) {
                t = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return t;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 7, 7, 8, 9, 10};
        System.out.println(max_search(arr, 4));
    }
}
