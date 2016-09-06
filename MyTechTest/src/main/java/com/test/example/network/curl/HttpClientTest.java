package com.test.example.network.curl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONObject;

import net.iharder.base64.Base64;

@SuppressWarnings("deprecation")
public class HttpClientTest {

	private static String url = "http://abfme:Pass1234@10.25.20.104:8080/job/tracereport/lastSuccessfulBuild/api/json?tree=result";

	private static String url2 = "http://10.25.20.104:8080/job/tracereport/lastSuccessfulBuild/api/json?tree=result";

	public static void main(String[] args) throws Exception {
		doGet(url);
		test();
	}

	@SuppressWarnings({ "resource" })
	public static void doGet(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);

		System.out.println(response.getStatusLine());

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
		br.close();
		isr.close();
		is.close();
	}

	@SuppressWarnings({ "resource" })
	public static void doGet2(String url) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url2);

		HttpResponse response = httpclient.execute(httpget);

		System.out.println(response.getStatusLine());

		if (response.getEntity() != null) {
			System.out.println("Response content length: " + response.getEntity().getContentLength());
		}
	}

	public static void test() {
		try {
			System.out.println("wo kao");
			URL url = new URL(url2);
			String encoding = Base64.encodeBytes(("abfme:Pass1234").getBytes());

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonObj = JSONObject.parseObject(sb.toString());
			System.out.println(jsonObj.getString("result"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
