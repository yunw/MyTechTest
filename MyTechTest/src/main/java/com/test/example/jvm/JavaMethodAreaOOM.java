package com.test.example.jvm;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * -XX:MaxPermSize=5m -XX:MaxMetaspaceSize=5m
 * @author Administrator
 *
 */
public class JavaMethodAreaOOM {

	public static void main(String[] args) {
		while(true) {
			Enhancer en = new Enhancer();
			en.setSuperclass(OOMObject.class);
			en.setUseCache(false);
			en.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					return proxy.invokeSuper(obj, args);
				}
			});
			en.create();
		}
	}
	
	static class OOMObject {
		
	}

}
