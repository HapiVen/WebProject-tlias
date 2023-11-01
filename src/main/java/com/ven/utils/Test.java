package com.ven.utils;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int [][] intevals = new int[][] {{8,10},{15,18},{1,3},{2,6}};
        //从小到大
        Arrays.sort(intevals, (x, y) -> Integer.compare(x[0], y[0]));
        System.out.println(Arrays.deepToString(intevals));
        //从大到小
        Arrays.sort(intevals, (x, y) -> Integer.compare(y[0], x[0]));
        System.out.println(Arrays.deepToString(intevals));

    }
}
