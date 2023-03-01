package com.chase.springcloud.service.blog.second;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        while (n -- != 0){
            int x = scan.nextInt(), y = scan.nextInt();
            char[][] chs = new char[x][x];
            for (int i = 0; i < x; i ++ ){
                String s = scan.next();
                for (int j = 0; j < s.length(); j ++ ){
                    chs[i][j] = s.charAt(j);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < y; i ++ ){
                sb2.append(scan.next());
            }
            int res = -1;
            for (int i = 0; i < x; i ++ ){
                for (int j = 0; j < x; j ++ ){
                    StringBuilder sb1 = new StringBuilder();
                    if ((i + y - 1) >= x || (j + y - 1) >= x){
                        continue;
                    }
                    for (int k = 0; k < y; k ++ ){
                        for (int q = 0; q < y; q ++ ){
                            sb1.append(chs[i + k][j + q]);
                        }
                    }
//                    System.out.println(sb1.toString() + " " + sb2.toString());
                    res = sb1.indexOf(sb2.toString());
                    if (res != -1){
                        break;
                    }
                }
                if (res != -1) break;
            }
            System.out.println(res != -1 ? "Yes" : "No");
        }
    }
}
