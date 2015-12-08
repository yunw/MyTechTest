package com.test.example.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Security {

	private static String APP_SECRET = "secret key";

	public static void main(String[] args)
	        throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		getHmacSHA1("aaaaaaaa");
	}

	private static byte[] getHmacSHA1(String src)
	        throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec secret = new SecretKeySpec(APP_SECRET.getBytes("UTF-8"), mac.getAlgorithm());
		mac.init(secret);
		return mac.doFinal(src.getBytes());
	}

}
