package com.test.example.base.nio.filelock;

import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockDemo {

	public static void main(String[] args) {
		try (FileOutputStream fis = new FileOutputStream("D://in.txt", true);
				FileChannel fc = fis.getChannel();
				FileLock flock = fc.tryLock();) {
			if (flock.isValid()) {
				fis.write("aaa".getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}