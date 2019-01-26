package com.demo;

public class Test {
    public static void main(String[] args) {
        String serverUrl = "http://www.ai.com/";
        if (null != serverUrl && serverUrl.endsWith("/")) {
            System.out.println(serverUrl.substring(0, serverUrl.length() - 1));
        }else{
            System.out.println("2:"+serverUrl);
        }
    }
}
