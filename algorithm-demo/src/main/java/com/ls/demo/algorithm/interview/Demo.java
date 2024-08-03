package com.ls.demo.algorithm.interview;


//Given an array nums, write a function that moves all zeros to the end of the array while maintaining the relative order of the non-zero elements.
public class Demo {

    public static int[] move(int[] array) {

        if (array == null || array.length == 0) {
            return null;
        }

        int j = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                swap(array, i, j++);
            }
        }
        return array;
    }

    public static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    public static void main(String[] args) {

        int[] array = {1, 0, 3, 0, 0, 0, 4, 5, 6};

        int[] result = move(array);

        for (int i = 0; i < result.length; i++) {
            System.out.printf(result[i] + "\t");
        }
    }
}
