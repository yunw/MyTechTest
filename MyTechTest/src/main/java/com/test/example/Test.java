package com.test.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class Test {

	public static void main(String args[]) throws IOException {
	    List<String> a = new ArrayList<String>();
	    a.add("a0000000001");
	    a.add("a0000003000");
	    a.add("b0000000001");
	    a.add("b0001000000");
	    a.add("a0000000070");
	    a.add("b0000000600");
	    for (String s : a) {
	        System.out.println(s);
	    }
	    System.out.println("\n");
	    Collections.sort(a, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
	    for (String s : a) {
            System.out.println(s);
        };
//		System.out.println(System.getProperty("user.dir"));
//		String url = "https://www.qq.com/aaa/bbb.html";
//		int idx = url.indexOf("/", 8);
//		String a = url.substring(8, idx);
//		System.out.println(a);
//		httpProxyTest();
//		JsTest();
//		System.out.println(UUID.randomUUID());
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(new Date().getTime());
//		int b = '\n';
//		System.out.println(b);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		BigInteger bi = new BigInteger("s");
//		int i = 2 << 1;
//		System.out.println(i);
//		//自动获取cup数量
//		System.out.println(Runtime.getRuntime().availableProcessors());
//		System.out.println("Hello World......");
//		char c = '中';
//		System.out.println(c);
//		char c2 = '\uffff';
//		System.out.println(c2);
//		File file = new File("E:\\yinsl\\VM\\VMware-viewclient-x32-4.6.0-366101.rar");
//		File target =new File("E:\\yinsl\\VM\\VMware-viewclient-x32-4.6.0-366101.rar.bak");
//		copyFile(file, target);
	}
	
	@SuppressWarnings("unused")
    public static void httpProxyTest() {
		String responseContent = null;
		try {
			HttpRoutePlanner routePlanner = new HttpRoutePlanner() {
				public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
				        throws HttpException {
					boolean secure = "https".equalsIgnoreCase(target.getSchemeName());
					
					return new HttpRoute(target, null, null, secure);
				}
			};

			HttpClient httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();

			URIBuilder uri = new URIBuilder();
			String reqUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?1=1";
			uri.setPath(reqUrl);
			Map<String,String> parameters = new HashMap<String, String>();
			if (parameters != null) {
				for (String key : parameters.keySet()) {
					String value = URLEncoder.encode(parameters.get(key), "utf-8");
					uri.setParameter(key, value);
				}
			}
			HttpGet httpGet = new HttpGet(uri.build());
			RequestConfig conf = RequestConfig.custom().setConnectionRequestTimeout(10000)
			        .setConnectTimeout(10000).setSocketTimeout(10000)
			        .build();
			httpGet.setConfig(conf);
			
			HttpResponse res = httpClient.execute(httpGet);
			int resStatu = res.getStatusLine().getStatusCode();
			String recvEncoding = "utf-8";
			if (resStatu == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				responseContent = EntityUtils.toString(entity, recvEncoding);
			} else {
				throw new Exception(uri.toString() + " resStatu is" + resStatu);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
    public static void JsTest() {
		
		List<MorrisData> list = new ArrayList<MorrisData>();
		list.add(new MorrisData("2011 Q3", 3407, 660));
		list.add(new MorrisData("2011 Q2", 3351, 629));
		list.add(new MorrisData("2011 Q1", 3269, 618));
		list.add(new MorrisData("2010 Q4", 3246, 661));
		list.add(new MorrisData("2010 Q3", 3257, 667));
		list.add(new MorrisData("2010 Q2", 3248, 627));
		list.add(new MorrisData("2010 Q1", 3171, 660));
		list.add(new MorrisData("2009 Q4", 3171, 676));
		list.add(new MorrisData("2009 Q3", 3201, 656));
		list.add(new MorrisData("2009 Q2", 3215, 622));
		list.add(new MorrisData("2009 Q1", 3148, 632));
		list.add(new MorrisData("2008 Q4", 3155, 681));
		list.add(new MorrisData("2008 Q3", 3190, 667));
		list.add(new MorrisData("2007 Q4", 3226, 620));
		list.add(new MorrisData("2006 Q4", 3245, 0));
		list.add(new MorrisData("2005 Q4", 3289, 0));
		list.add(new MorrisData("2004 Q4", 3263, 0));
		list.add(new MorrisData("2003 Q4", 3189, 0));
		list.add(new MorrisData("2002 Q4", 3079, 0));
		list.add(new MorrisData("2001 Q4", 3085, 0));
		list.add(new MorrisData("2000 Q4", 3055, 0));
		list.add(new MorrisData("1999 Q4", 3063, 0));
		list.add(new MorrisData("1998 Q4", 2943, 0));
		list.add(new MorrisData("1997 Q4", 2806, 0));
		list.add(new MorrisData("1996 Q4", 2674, 0));
		list.add(new MorrisData("1995 Q4", 1702, 0));
		list.add(new MorrisData("1994 Q4", 1732, 0));
		
		
		MorrisLine ml = new MorrisLine("graph-quarters",list,"period",new String[] {"licensed", "sorned"},new String[] {"Licensed", "Sorn"});
		
		Gson gson = new Gson();
		String s = gson.toJson(list);
		System.out.println(s);
	}
	
	static class MorrisData {

		public MorrisData(String period, Integer licensed, Integer sorned) {
			super();
			this.period = period;
			this.licensed = licensed;
			this.sorned = sorned;
		}

		private String period = null;

		private Integer licensed = null;

		private Integer sorned = null;

		public String getPeriod() {
			return period;
		}

		public void setPeriod(String period) {
			this.period = period;
		}

		public Integer getLicensed() {
			return licensed;
		}

		public void setLicensed(Integer licensed) {
			this.licensed = licensed;
		}

		public Integer getSorned() {
			return sorned;
		}

		public void setSorned(Integer sorned) {
			this.sorned = sorned;
		}

	}

	static class MorrisLine {
		
		public MorrisLine() {
			
		}

		public MorrisLine(String element, List<MorrisData> data, String xKey, String[] yKeys, String[] labels) {
	        super();
	        this.element = element;
	        this.data = data;
	        this.xKey = xKey;
	        this.yKeys = yKeys;
	        this.labels = labels;
        }

		private String element = null;

		private List<MorrisData> data = null;

		private String xKey = null;

		private String[] yKeys = null;

		private String[] labels = null;

		public String getElement() {
			return element;
		}

		public void setElement(String element) {
			this.element = element;
		}

		public List<MorrisData> getData() {
			return data;
		}

		public void setData(List<MorrisData> data) {
			this.data = data;
		}

		public String getxKey() {
			return xKey;
		}

		public void setxKey(String xKey) {
			this.xKey = xKey;
		}

		public String[] getyKeys() {
			return yKeys;
		}

		public void setyKeys(String[] yKeys) {
			this.yKeys = yKeys;
		}

		public String[] getLabels() {
			return labels;
		}

		public void setLabels(String[] labels) {
			this.labels = labels;
		}

	}
	
	public static void copyFile(File source, File target) {

		try (FileInputStream fis = new FileInputStream(source);
				FileOutputStream fos = new FileOutputStream(target);
				FileChannel fic = fis.getChannel();
				FileChannel foc = fos.getChannel();) {
			ByteBuffer bb = ByteBuffer.allocate(1024);
			while (-1 != fic.read(bb)) {
				bb.flip();
				foc.write(bb);
				bb.clear();
			}
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
