package com.ls.demo.algorithm.study.chapter02_bitwish;

public class FindTwoOdd {
    // 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
    public static String findTwoOdd(int[] arr) {
        if (arr == null) {
            return null;
        }

        int x = 0;
        for (int i = 0; i < arr.length; i++) {
            x ^= arr[i];
        }

        int rightOne = x & (-x);

        int a = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rightOne) == rightOne) {
                a ^= arr[i];
            }
        }

        int b = x ^ a;

        return "first num : " + a + ", second num: " + b;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 3, 3, 2, 2, 4, 4, 5};
        System.out.println(findTwoOdd(arr));
    }
}
