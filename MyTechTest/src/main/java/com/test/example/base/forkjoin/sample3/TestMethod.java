package com.test.example.base.forkjoin.sample3;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ForkJoinPool;

/**
 * 测试类
 * 
 * @author Administrator
 *
 */
public class TestMethod {

	// 获得文件长度
	private long getFileSize(SiteInfoBean bean) {
		long nFileLength = -1;
		try {
			URL url = new URL(bean.getSSiteURL());
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				// processErrorCode(responseCode);
				return -2; // -2 represent access is error
			}
			String sHeader;
			for (int i = 1;; i++) {
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Long.parseLong(httpConnection
								.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utility.log(String.valueOf(nFileLength));
		return nFileLength;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		 TestMethod test = new TestMethod();
		 try {
				SiteInfoBean bean = new SiteInfoBean(
						"http://localhost:8080/CMS/yinsl.rar", "d:\\cmstemp",
						"yinsl.rar");
				FileHttpDownTask task = new FileHttpDownTask(bean, 0,
						test.getFileSize(bean));
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		 long end = System.currentTimeMillis();
		 System.out.println("\n 耗时：" + (end - start) / 1000 + "秒.");
	}
}
