package com.test.example.base.breakpoint;

/**
 * 测试类
 * @author Administrator
 *
 */
public class TestMethod {
	
	public TestMethod() {
		try {
			SiteInfoBean bean = new SiteInfoBean(
					"http://localhost:8080/CMS/yinsl.rar", "d:\\cmstemp",
					"yinsl.rar", 4);
			SiteFileFetch fileFetch = new SiteFileFetch(bean);
			fileFetch.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TestMethod();
	}
}
