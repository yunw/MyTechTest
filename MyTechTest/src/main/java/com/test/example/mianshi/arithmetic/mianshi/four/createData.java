package com.test.example.mianshi.arithmetic.mianshi.four;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class createData {

	public static void main(String[] args) throws IOException {
		long len = 10000000;
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\bigFile1.txt"));) {
			String lineSeparator = System.getProperty("line.separator");
			for (long i = 0; i < len; i++) {
				long a = Math.round((Math.random() * len));
				bw.write(a + lineSeparator);
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
