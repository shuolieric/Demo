package com.ls.demo.algorithm.study.chapter04_mergesort;

public class SmallSum {

    public static int small_sum(int[] arr) {
        if (arr == null || arr.length <2) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }

        int M = L + ((R - L) >> 1);
        return process(arr, L, M) + process(arr, M + 1, R) + merge(arr, L, M, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int ans = 0;

        int p1 = L;
        int p2 = M + 1;
        int i = 0;
        int[] help = new int[R - L + 1];

        while (p1 <= M && p2 <= R) {
            if (arr[p1] < arr[p2]) {
                ans += (R - p2 + 1) * arr[p1];
                help[i++] = arr[p1++];
            } else {
                help[i++] = arr[p2++];
            }

        }

        while (p1 <= M) {
            help[i++] = arr[p1++];
        }

        while (p2 <= R) {
            help[i++] = arr[p2++];
        }

        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3};
        System.out.println(small_sum(arr));
    }
}
