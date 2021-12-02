package com.example.book.utils;


import sun.security.provider.MD5;

import java.security.MessageDigest;

public class Md5Util {

    public static final String CHARSET = "UTF-8";
    private static final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    public final static String MD5(String s) {

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 六位密码破解
     */
    public static String Md5_6(String md5Password) {
        String testPassword = "";
        MD5 md5Obj = new MD5();
        for (int a = 0; a < hexDigits.length; a++) {
            testPassword = "";
            testPassword += hexDigits[a];
            for (int b = 0; b < hexDigits.length; b++) {
                testPassword = testPassword.substring(0, 1);
                testPassword += hexDigits[b];
                for (int c = 0; c < hexDigits.length; c++) {
                    testPassword = testPassword.substring(0, 2);
                    testPassword += hexDigits[c];
                    for (int d = 0; d < hexDigits.length; d++) {
                        testPassword = testPassword.substring(0, 3);
                        testPassword += hexDigits[d];
                        for (int e = 0; e < hexDigits.length; e++) {
                            testPassword = testPassword.substring(0, 4);
                            testPassword += hexDigits[e];
                            for (int f = 0; f < hexDigits.length; f++) {
                                testPassword = testPassword.substring(0, 5);
                                testPassword += hexDigits[f];
                            }
                        }
                    }
                }
            }
        }
        return testPassword;
    }

}
