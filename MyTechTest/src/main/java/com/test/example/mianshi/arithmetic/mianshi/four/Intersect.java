package com.test.example.mianshi.arithmetic.mianshi.four;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Intersect {

	private static String lineSeparator = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException {
		long filePointer = 0;
		int lines = 1000000;
		boolean isEnd = false;
		RandomAccessFile rf = new RandomAccessFile("D:\\bigFile1.txt", "r");
		BufferedReader br = new BufferedReader(new FileReader("D:\\bigFile2.txt"));
		FileOutputStream fos = new FileOutputStream("D:\\bigResult.txt", true);
		int i = 0;
		// 获取交集，交集包含重复记录
		while (!isEnd) {
			long start = System.currentTimeMillis();
			System.out.println("--------------" + i);
			Helper helper = createTree(rf, filePointer, lines);
			long end = System.currentTimeMillis();
			System.out.println("tree created." + (end - start) / 1000 + "s.");
			isEnd = helper.isEnd;
			filePointer = helper.filePointer;// 从这里开始读取文件。
			exe(helper.tree, br, fos);// 获取交集
			long end2 = System.currentTimeMillis();
			System.out.println("本次比较完毕。" + (end2 - end) / 1000 + "s.");
			fos.flush();
			fos.close();
			br.close();
			br = new BufferedReader(new FileReader("D:\\bigFile2.txt"));
			fos = new FileOutputStream("D:\\bigResult.txt", true);
			i++;
		}
		fos.flush();
		fos.close();
		br.close();
		rf.close();

		// 去掉交集中的重复记录。
		rf = new RandomAccessFile("D:\\bigResult.txt", "r");
		br = new BufferedReader(new FileReader("D:\\bigFile2.txt"));
		fos = new FileOutputStream("D:\\noRepeatResult.txt", true);
		filePointer = 0;
		isEnd = false;
		while (!isEnd) {
			Helper helper = createTree(rf, filePointer, lines);
			isEnd = helper.isEnd;
			filePointer = helper.filePointer;// 从这里开始读取文件。
			System.out.println("去重。。。。");
			createNoRepeatResult(helper, rf, fos);// 去重
		}
		fos.flush();
		fos.close();
		rf.close();
		br.close();
	}

	public static void createNoRepeatResult(Helper helper, RandomAccessFile rf, FileOutputStream fos)
	        throws IOException {
		String line = null;
		rf.seek(helper.filePointer);
		while ((line = rf.readLine()) != null) {
			if (!helper.tree.has(line)) {
				fos.write(line.getBytes());
				fos.write(lineSeparator.getBytes());
			}
		}
	}

	public static Helper createTree(RandomAccessFile rf, long filePointer, int lines) throws IOException {
		Trie tree = new Trie();
		int i = 0;
		rf.seek(filePointer);
		String line = null;
		while (i < lines && (line = rf.readLine()) != null) {
			tree.insert(line);
			i++;
		}
		Helper helper = new Helper(0, false, tree);
		helper.filePointer = rf.getFilePointer();
		if (line == null) {
			helper.isEnd = true;
		}
		return helper;
	}

	public static void exe(Trie tree, BufferedReader br, FileOutputStream fos) {
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (tree.has(line)) {
					fos.write(line.getBytes());
					fos.write(lineSeparator.getBytes());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static class Helper {
		long filePointer;
		boolean isEnd;
		Trie tree;

		public Helper(long filePointer, boolean isEnd, Trie tree) {
			this.filePointer = filePointer;
			this.isEnd = isEnd;
			this.tree = tree;

		}
	}

}
