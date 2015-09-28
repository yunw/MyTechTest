package com.test.example.googlemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 使用谷歌地图从地址转换得到经纬度
 * 
 * 注：国内测试使用自由门软件代理访问谷歌地图
 * 
 * @author Administrator
 *
 */
public class GoogleMapTest {

	/**
	 * 配置缓存
	 */
	private static Properties prop = new Properties();

	/**
	 * 谷歌地图url
	 */
	private static String url;

	/**
	 * 是否使用代理
	 */
	private static boolean isProxy;

	/**
	 * 代理主机
	 */
	private static String host;

	/**
	 * 代理端口
	 */
	private static Integer port;

	static {
		InputStream is = GoogleMapTest.class.getResourceAsStream("config.properties");
		try {
			prop.load(is);
			url = prop.getProperty("url");
			isProxy = Boolean.getBoolean(prop.getProperty("isProxy"));
			host = prop.getProperty("host");
			port = Integer.getInteger(prop.getProperty("port"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param language
	 *            语言
	 * @param output
	 *            输出格式
	 * @param addr
	 *            地址
	 *            
	 * @return 经纬度
	 */
	public static Coordinates getCoordinate(String language, String output, String addr) {

		StringBuffer sb = new StringBuffer();
		try {
			URLConnection httpsConn = null;
			String address = URLEncoder.encode(addr, "UTF-8");
			String urlStr = url + output + "?language=" + language + "&address=" + address + "&sensor=true_or_false";
			URL myURL = new URL(urlStr);
			if (isProxy) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
				httpsConn = myURL.openConnection(proxy);
			} else {
				httpsConn = myURL.openConnection();
			}
			if (httpsConn != null) {
				InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(insr);
				String data = null;
				while ((data = br.readLine()) != null) {
					sb.append(data);
				}
				insr.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readStringXmlOut(sb.toString());
	}

	/**
	 * 从xml文件中提取经纬度信息
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Coordinates readStringXmlOut(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);

			Element rootElt = doc.getRootElement();
			Iterator<Element> iter = rootElt.elementIterator("result");
			while (iter.hasNext()) {
				Element recordEle = iter.next();
				// 纬度
				String latitude = recordEle.element("geometry").element("location").elementTextTrim("lat");
				// 经度
				String longitude = recordEle.element("geometry").element("location").elementTextTrim("lng");

				return new Coordinates(longitude, latitude);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new Coordinates();
	}

	public static void main(String[] args) {
		Coordinates coordinates = getCoordinate("zh_CN", "xml", "中国上海延安东路201号");
		System.out.println("经度：" + coordinates.getLongitude());
		System.out.println("纬度：" + coordinates.getLatitude());
		System.out.println(new Date());
	}
	
}
