package com.ls.demo.algorithm.interview;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKNums {


    public static int[] getTopK(int[] array, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : array) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (pq.size() < k) {
                pq.add(e);
            } else if (pq.peek().getValue() < e.getValue()) {
                pq.remove();
                pq.add(e);
            }
        }

        int[] result = new int[k];
        int index = 0;
        while (pq.size() > 0) {
           result[index++] =pq.remove().getKey();
        }

        return result;
    }


    public static void main(String[] args) {
        int[] array = {1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 7};

        int[] topK = getTopK(array, 1);
        for (int num : topK) {
            System.out.printf(num + "\t");
        }
    }
}
