package com.ls.demo.algorithm.study.chapter01_sort;

public class BSAwesome {

    public static int BSSearch(int[] arr) {
        if (arr == null || arr.length < 2) {
            return -1;
        }

        if (arr[0] < arr[1]) {
            return 0;
        }

        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }

        int left = 1;
        int right = arr.length - 2;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 2, 0, 1, 2, 3};
        System.out.println(BSSearch(arr));
    }
}
