package com.diaoyn.alone.util;

import java.util.Scanner;

/**
 * @author diaoyn
 * @ClassName NowCoderUtil
 * @Date 2025/6/23 13:45
 */
public class NowCoderUtil {


    static class good {
        int v; //价格
        int p; //重要度
        int q; //主件编号
        boolean isMain;

        public int a1 = 0;   //附件1ID
        public int a2 = 0;   //附件2ID

        public void setA1(int a1) {
            this.a1 = a1;
        }

        public void setA2(int a2) {
            this.a2 = a2;
        }

        public good(int v, int p, int q) {
            this.v = v;
            this.p = p;
            this.q = q;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String input = in.nextLine();
        String[] strings = input.split(";");
        int a = 0;
        int b = 0;
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i].trim();
            if (str.isEmpty()) {
                continue;
            }
            if (str.charAt(0) != 'A'
                    && str.charAt(0) != 'D'
                    && str.charAt(0) != 'W'
                    && str.charAt(0) != 'S'
            ) {
                continue;

            }
            String str2 = str.substring(1);

            try {
                Integer num = Integer.parseInt(str2);
                if (num < 0 || num >= 100) {
                    continue;
                }
                if (str.charAt(0) == 'A') {
                    a = a - num;
                }
                if (str.charAt(0) == 'D') {
                    a = a + num;
                }
                if (str.charAt(0) == 'W') {
                    b = b + num;
                }
                if (str.charAt(0) == 'S') {
                    b = b - num;
                }


            } catch (NumberFormatException e) {
                continue;
            }


        }
        System.out.println(a + "," + b);
    }
}



