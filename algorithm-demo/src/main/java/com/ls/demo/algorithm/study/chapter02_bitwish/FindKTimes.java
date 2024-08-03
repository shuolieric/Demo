package com.ls.demo.algorithm.study.chapter02_bitwish;

public class FindKTimes {

    // 一个数组中有一种数出现K次，其他数都出现了M次，
    // M > 1,  K < M
    // 找到，出现了K次的数，
    // 要求，额外空间复杂度O(1)，时间复杂度O(N)
    public static int find_x_times(int[] arr, int k, int m) {

        int[] t = new int[32];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 32; j++) {
                t[j] += (arr[i] >> j) & 1;
            }
        }

        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0){
                ans |= 1 << i;
            }
        }

        return ans;
    }
    public static void main(String[] args) {
        int[] arr = {1,1,1,2,2,2,3,3,3,4,4};
        System.out.println(find_x_times(arr, 2, 3));
    }
}
