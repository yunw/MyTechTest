package com.test.example.base.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpGetTest {

	public static void main(String[] args) throws Exception {
//		httpGet();
		post();
	}

	public static void httpGet() throws Exception {
		// (1) 创建HttpGet实例
		HttpGet get = new HttpGet("http://10.25.20.104:8080/view/Devops/job/tracereport/lastBuild/api/json");

		BasicCredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope("10.25.20.104", 8080),
				new UsernamePasswordCredentials("abfme", "Pass1234"));

		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultCredentialsProvider(provider);
		CloseableHttpClient client = builder.build();

		HttpResponse response = client.execute(get);

		// (3) 读取返回结果
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();

			InputStream in = entity.getContent();
			readResponse(in);
		}
	}

	public static void post() throws ClientProtocolException, IOException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("account", "abfme"));
		formparams.add(new BasicNameValuePair("password", "Pass1234"));
		HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, "utf-8");

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://10.25.20.104:8080/view/Devops/job/tracereport/lastBuild/api/json");
		post.setEntity(reqEntity);
		post.setConfig(requestConfig);
		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity resEntity = response.getEntity();
			String message = EntityUtils.toString(resEntity, "utf-8");
			System.out.println(message);
		} else {
			System.out.println("请求失败");
		}
	}

	private static void readResponse(InputStream in) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}

}
