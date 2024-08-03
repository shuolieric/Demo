package com.ls.demo.algorithm.study.chapter02_bitwish;

public class FindOdd {
    // 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
    public static int findOdd(int[] arr) {
        if (arr == null) {
            return -1;
        }
        if (arr.length == 1) {
            return arr[0];
        }

        int x = 0;
        for (int i = 0; i < arr.length; i++) {
            x ^= arr[i];
        }

        return x;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 2, 1, 1};
        System.out.println(findOdd(arr));
    }
}
