package com.test.example.base.forkjoin.sample3;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.RecursiveAction;

import com.test.example.base.breakpoint.FileAccessI;

/**
 * 负责整个文件的抓取，控制内部线程
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class FileHttpDownTask extends RecursiveAction {

	SiteInfoBean bean = null;

	private long threshold = 1024 * 1024 * 10;// 10M

	FileSplitterFetch fileSplitterFetch = null; // 文件信息Bean

	long start; // File Snippet Start Position

	long end; // File Snippet End Position

	File tmpFile; // 文件下载的临时信息

	FileAccessI fileAccessI = null; // File Access interface

	DataOutputStream output; // 输出到文件的输出流

	private final int bufferSize = 4096;

	public FileHttpDownTask(SiteInfoBean bean, long start, long end) {
		this.bean = bean;
		this.start = start;
		this.end = end;
	}

	@Override
	protected void compute() {
		if (end - start < threshold) {
			exe();
		} else {
			long middle = (start + end) / 2;
			FileHttpDownTask lower = new FileHttpDownTask(bean, start, middle);
			FileHttpDownTask higher = new FileHttpDownTask(bean, middle + 1,
					end);
			invokeAll(lower, higher);
		}
	}

	private void exe() {
		try {
			fileAccessI = new FileAccessI(bean.getSFilePath() + File.separator
					+ bean.getSFileName(), start);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (start < end) {
			HttpURLConnection httpConnection = null;
			InputStream input = null;
			try {
				URL url = new URL(bean.getSSiteURL());
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");
				String sProperty = "bytes=" + start + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				Utility.log(sProperty + "---" + end);
				input = httpConnection.getInputStream();
				byte[] b = new byte[bufferSize];
				int nRead;
				while ((nRead = input.read(b, 0, bufferSize)) > 0
						&& start < end) {
					start += fileAccessI.write(b, 0, nRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				httpConnection.disconnect();
			}
		}
		System.out.println("start == " + start + ", end == " + end
				+ ", start == end ? " + (start == end)
				+ ", this thread is over.");

	}
}
