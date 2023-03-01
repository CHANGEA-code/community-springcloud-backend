package com.chase.springcloud.service.blog.first;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        int yuanNum = 0;
        for (int i = 0; i < s.length(); i ++ ){
            char c = s.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
                yuanNum ++ ;
            }
        }
        int res = Math.min(yuanNum * 2 + 1, s.length());
        System.out.println(res);
    }
}
