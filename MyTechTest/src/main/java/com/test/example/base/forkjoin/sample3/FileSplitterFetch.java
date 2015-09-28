package com.test.example.base.forkjoin.sample3;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 负责部分文件的抓取
 * 
 * @author Administrator
 *
 */
public class FileSplitterFetch {

	SiteInfoBean bean = null;

	long start; // File Snippet Start Position

	long end; // File Snippet End Position

	long size;

	boolean bDownOver = false; // Downing is over

	boolean bStop = false; // Stop identical

	FileAccessI fileAccessI = null; // File Access interface

	private final int bufferSize = 4096;

	public FileSplitterFetch(SiteInfoBean bean, long start, long end) {
		this.bean = bean;
		this.start = start;
		this.end = end;
		this.size = end - start;
	}

	public void run() {
		while (start < end && !bStop) {
			try {
				URL url = new URL(bean.getSSiteURL());
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");
				String sProperty = "bytes=" + start + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				Utility.log(sProperty);
				InputStream input = httpConnection.getInputStream();
				byte[] b = new byte[bufferSize];
				int nRead;
				while ((nRead = input.read(b, 0, bufferSize)) > 0
						&& start < end && !bStop) {
					start += fileAccessI.write(b, 0, nRead);
				}
				bDownOver = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FileSplitterFetch subTask(long start, long end) {
		return new FileSplitterFetch(bean, start, end);
	}

	// 打印回应的头信息
	public void logResponseHead(HttpURLConnection con) {
		for (int i = 1;; i++) {
			String header = con.getHeaderFieldKey(i);
			if (header != null) {
				Utility.log(header + " : " + con.getHeaderField(header));
			} else {
				break;
			}
		}
	}

	public void splitterStop() {
		bStop = true;
	}

	public SiteInfoBean getBean() {
		return bean;
	}

	public void setBean(SiteInfoBean bean) {
		this.bean = bean;
	}

}
