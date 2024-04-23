package com.liuujun.class2dml.utils;

/**
 * @author liujun
 */
public class ABCUtils {

    public static String lowerCamel2SnakeCase(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                if (i == 0){
                    sb.append(String.valueOf((char) c).toLowerCase());
                }else {
                    sb.append('_');
                    sb.append(String.valueOf((char) c).toLowerCase());
                }
            }else {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(lowerCamel2SnakeCase("aaaAddd"));
        System.out.println(lowerCamel2SnakeCase("DaaaAddd"));
        System.out.println(lowerCamel2SnakeCase("Daasss34aAddd444"));
    }
}
