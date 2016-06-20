package com.test.example.network.ipinfo;

import java.util.StringTokenizer;

public class PrintBinaryIP {
	
	public static void main(String[] args) {
		String a = tenToTwo("202.112.14.137");
		System.out.println(a);
		System.out.println(twoToTen("11111111.11111111.11111111.11000000"));
	}
	
	public static String tenToTwo(String ten) {
		StringTokenizer st = new StringTokenizer(ten, ".");
	    String ip2 = "";
	    while (st.hasMoreTokens()) {
	    	int num = Integer.parseInt(st.nextToken());
	    	String tmp = "00000000" + Integer.toBinaryString(num);
    		ip2 += tmp.substring(tmp.length() - 8) +".";
	    }
	    String ip = ip2.subSequence(0, ip2.length() -1).toString();
	    return ip;
	}
	
	public static String twoToTen(String two) {
		StringTokenizer st = new StringTokenizer(two, ".");
	    String ten = "";
	    while (st.hasMoreTokens()) {
	    	String s = st.nextToken();
	    	String temp = Integer.valueOf(s,2).toString();
	    	ten += temp +".";
	    }
	    String ip = ten.subSequence(0, ten.length() -1).toString();
	    return ip;
	}

}
