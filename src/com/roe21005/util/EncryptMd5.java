package com.roe21005.util;

import java.security.MessageDigest;

public class EncryptMd5 {
	
	public static String String2MD5(String s) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = s.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        
        for (int i = 0; i < charArray.length; i++){
        	byteArray[i] = (byte) charArray[i];
        }
            
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("123456"+" "+String2MD5("123456"));
	}

}
