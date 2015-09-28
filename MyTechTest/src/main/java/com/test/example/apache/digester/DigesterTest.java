package com.test.example.apache.digester;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class DigesterTest {

	/**
	 * 本例的对象解析和生成过程为：
	 * 首先解析<foo>元素生成Foo对象，并压入对象栈。然后弹出最上层的对象（即Foo对象），并将其从栈中移除，为其设置属性；将设置好属性的对象重新压入对象栈。
	 * 然后解析第一个嵌套的<bar>元素，生成第一个Bar对象，并压入对象栈。
	 * 解析到达<bar>元素的结尾时，弹出最上层的对象（即第一个Bar对象），并将其从栈中移除，为其设置属性，并继续弹出栈中的对象（即Foo对象），
	 * 同时将Foo对象从栈中移除。 然后执行Foo的addBar方法，执行完后将Foo重新压入栈中。
	 * 接着解析第二个<bar>元素，生成第二个Bar对象，重复一个Bar对象的动作，为其设置属性，调用Foo的addBar方法。
	 * 最后，解析到达<foo>元素的结尾时，将对象栈最顶层的对象弹出（即Foo对象，这时战中也只有这一个对象）。完成解析过程。
	 * @param args
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws IOException, SAXException {
		Digester digester = new Digester();
		digester.setValidating(false);
		//当遇到最外层的<Foo>元素的时候，创建一个com.test.example.apache.digester.Foo的实例并将该实例放到到对象栈中（object stack）。
		//在<Foo>元素结束的时候，这个对象将从栈中弹出。
		digester.addObjectCreate("foo", "com.test.example.apache.digester.Foo");
		//为对象栈中最上面的对象设置属性。
		digester.addSetProperties("foo");
		//当遇到嵌套的<bar>元素的时候，创建一个新的com.test.example.apache.digester.Bar实例，并将该实例放入对象栈中。
		//在<bar>元素结束的时候，这个对象从对象栈中弹出，执行剩余的匹配规则“foo/bar”所指定的处理。
		//本例的剩余规则有两个：1、为bar实例设置属性，2、执行addBar方法。
		digester.addObjectCreate("foo/bar",
				"com.test.example.apache.digester.Bar");
		digester.addSetNext("foo/bar", "addBar",
				"com.test.example.apache.digester.Bar");
		digester.addSetProperties("foo/bar");
		
		String path = DigesterTest.class.getResource("").getPath().substring(1);
		File file = new File(path + "Foo.xml");
		Foo foo = (Foo) digester.parse(file);
		System.out.println(foo.getName());
	}

}
