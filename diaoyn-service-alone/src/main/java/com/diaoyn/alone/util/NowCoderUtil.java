package com.diaoyn.alone.util;

import java.util.Scanner;

/**
 * @author diaoyn
 * @ClassName NowCoderUtil
 * @Date 2025/6/23 13:45
 */
public class NowCoderUtil {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int count = 0;
     while (n != 0) {
         count = count + (n & 1);
            n = n >> 1;
     }
        System.out.println(count);

    }
}
