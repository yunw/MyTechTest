package com.test.example.network;

public class Util2Bytes {

	public static int bytes2int(byte[] b) {
		return ((b[0] & 0x000000ff) << 24) | ((b[1] & 0x000000ff) << 16) | ((b[2] & 0x000000ff) << 8)
				| (b[3] & 0x000000ff);
	}
	
	public static byte[] int2byte(int a) {
		byte[] b = new byte[4];
		b[0] = (byte) (a >> 24);
		b[1] = (byte) (a >> 16);
		b[2] = (byte) (a >> 8);
		b[3] = (byte) (a);
		return b;
	}

	public static void main(String args[]) {
		System.out.println(Integer.MAX_VALUE);
		byte[] b = int2byte(Integer.MAX_VALUE);
		System.out.println(b[0] + "" + b[1] + "" + b[2] + "" + b[3]);
		System.out.println(bytes2int(b));
		System.out.println(bytes2int(new byte[]{0,10,0,8}));
	}
	

}
