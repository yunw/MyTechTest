package com.test.example.network.curl;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientTest {
	public static void main(String[] args) throws Exception {
		String url = "http://abfme:fd765a383e77efc40c0ddefee17fc827@10.25.20.104:8080/job/tracereport/lastSuccessfulBuild/api/json?tree=result";
		init(url);
	}

	public static HttpResponse init(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);

		InputStream is = response.getEntity().getContent();
		InputStreamReader isr = new InputStreamReader(is, "utf-8"); // 设置读取流的编码格式，自定义编码
		java.io.BufferedReader br = new java.io.BufferedReader(isr);
		String tempbf;
		StringBuilder sb = new StringBuilder();
		while ((tempbf = br.readLine()) != null) {
			sb.append(tempbf);
			sb.append("\r\n");
		}
		System.out.println(sb.toString());
		isr.close();

		return response;
	}
}
