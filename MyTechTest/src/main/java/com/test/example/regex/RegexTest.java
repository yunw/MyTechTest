package com.test.example.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (?s)开启单行模式：单行模式下 . 可以匹配任意字符（包括换行符\n）；如果关闭单行模式， . 匹配除换行符之外的任意字符 
 * 捕获组： 
 * (pattern)：匹配pattern并捕获结果，自动设置组号。使用小括号指定一个子表达式后，匹配这个子表达式的文本(也就是此分组捕获的内容)
 * 可以在表达式或其它程序中作进一步的处理。默认情况下，每个捕获组会自动拥有一个组号，
 * 规则是：从左向右，以分组的左括号为标志，第一个出现的分组的组号为1，第二个为2，以此类推。
 * 例如： (\d{4})-(\d{2}-(\d{2})) 
 *      1     1 2      3     32    --数字表示组号（后一个相同数字表示该组结束位置）
 * 
 * 非捕获组：非捕获组只匹配结果，但不捕获结果，也不会分配组号，当然也不能在表达式和程序中做进一步处理。 
 * (?:pattern): 匹配pattern，但不捕获匹配结果。
 * 
 * (?=pattern)：零宽度正向预查，不捕获匹配结果。
 * 
 * @author Administrator
 *
 */
public class RegexTest {

	public static void main(String args[]) {
//		 capturing1("http://reg-test-server:8080/download/file1.html");
//		 nonCapturing6("abcbcm");
//		nonCapturing5("12aabbaaa");
		// nonCapturing4("12aaaab");
		// nonCapturing3("12aabbaaaa");
//		 nonCapturing2("12aaaaabaa");
//		 nonCapturing1("1234.526$124￥");
//		 nonCapturing0("abm");
//		 noRepeatString("aaaabbbcccdddeeeasdfsdfjsd;lfkjsd;fksd123adf");
//		 getNum("aaa234bbb344");
		 removeDigit("aaa1233bsdjfd345klddj79");
	}

	/**
	 * 分组
	 * @param s
	 */
	public static void capturing1(String s) {
		Pattern p = Pattern
				.compile("^(\\w+)(://)((?:\\w+-)*\\w+)(:\\d+)?(.*)$");
		Matcher m = p.matcher(s);
		if (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println(String.format("%d : %s",i, m.group(i)));
			}
		}
	}

	/**
	 * (?>X) X,非捕获组,不回溯
	 * 
	 * 例如，输入："abcbm" 输出： 输入："abcm" 输出：abcm
	 */
	public static void nonCapturing6(String s) {
		Pattern p = Pattern.compile("a(?>bc)*m");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
		System.out.println();
	}

	/**
	 * (?:X) X, as a non-capturing group 非捕获组
	 * 
	 * 例如，输入："abcm" 输出：abcm
	 */
	public static void nonCapturing0(String s) {
		Pattern p = Pattern.compile("a(?:b|bc)m");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
		System.out.println();
	}

	/**
	 * (?:X) X, as a non-capturing group 非捕获组
	 * 
	 * 输入一个金额和货币类型，输出金额的整数部分和货币类型。 例如，输入："1234.56￥" 输出：1234￥ 输入："123.45$"
	 * 输出：123$
	 */
	public static void nonCapturing1(String s) {
		System.out.println(s);
		//非捕获组
		System.out.println("非捕获组，打印第一、二组。。。。");
		System.out.println("(\\d+)(?:\\.?\\d*)([￥$])");
		Pattern p = Pattern.compile("(\\d+)(?:\\.?\\d*)([￥$])");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group(1) + m.group(2));
		}
		System.out.println();
		
		//捕获组
				System.out.println("捕获组，打印第一、二组。。。。");
				System.out.println("(\\d+)(\\.?\\d*)([￥$])");
				p = Pattern.compile("(\\d+)(\\.?\\d*)([￥$])");
				m = p.matcher(s);
				while (m.find()) {
					System.out.println(m.group(1) + m.group(2));
				}
				System.out.println();
	}

	/**
	 * (?=X) X, 非捕获组，零宽度正向前查找
	 * 例如，正则表达式：[0-9a-z]{2}(?=aa)的含义是：查找两位字符（数字，或字母），它的后面是aa 例如，输入："12aaaa"
	 * 输出：12 aa 为什么输出了aa呢？因为第一次查到了12，然后接着往后查aaaa也是满足要求的。
	 */
	public static void nonCapturing2(String s) {
		System.out.println("input: " + s);
		Pattern p = Pattern.compile("[0-9a-z]{2}(?=aa)");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
		System.out.println();
	}

	/**
	 * (?!X) X, 非捕获组，零宽度负向前查找
	 * 例如，正则表达式：[0-9a-z]{2}(?!aa)的含义是：查找两位字符（数字，或字母），它的后面不能是aa 例如，输入："12aaaab"
	 * 输出：aa ab
	 */
	public static void nonCapturing4(String s) {
		Pattern p = Pattern.compile("[0-9a-z]{2}(?!aa)");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
		System.out.println();
	}

	/**
	 * (?<=X) X, 非捕获组，零宽度正向后查找
	 * 例如，正则表达式：(?<=aa)[0-9a-z]{2}的含义是：查找两位字符（数字，或字母），它的前面是aa 例如，输入："12aabbaaaa"
	 * 输出：aa
	 */
	public static void nonCapturing3(String s) {
		Pattern p = Pattern.compile("(?<=aa)[0-9a-z]{2}");
		Matcher m = p.matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
		System.out.println();
	}

	/**
	 * (?<!X) X, 非捕获组，零宽度负向后查找
	 * 例如，正则表达式：[0-9a-z]{2}(?<!aa)的含义是：查找两位字符（数字，或字母），它的前面不能是aa
	 * 例如，输入："12aabbaaa" 输出：12 aa ba aa
	 */
	public static void nonCapturing5(String s) {
		Matcher m = Pattern.compile("(?<!aa)[0-9a-z]{2}").matcher(s);
		while (m.find()) {
			System.out.println(m.group());
		}
		System.out.println();
	}

	/**
	 * (?s)(.)(?=.*\\1) (?s)开启单行模式：单行模式下 .
	 * 可以匹配任意字符（包括换行符\n）；如果关闭单行模式，　.　　　匹配除换行符之外的任意字符
	 * 
	 * @param s
	 * @return
	 */
	public static void noRepeatString(String s) {
		// Pattern p = Pattern.compile("(.)(?=.*\\1)", Pattern.DOTALL);
		//
		//
		// String[] str = p.split(s);
		// StringBuilder sb = new StringBuilder();
		// for (int i = 0; i < str.length; i++) {
		// sb.append(str[i]);
		// }
		// System.out.println(sb.toString());

		 System.out.println(s.replaceAll("(?s)(.)(?=.*\\1)", ""));

		System.out.println(s);
		StringBuilder sb = new StringBuilder(new StringBuilder(s).reverse()
				.toString().replaceAll("(?s)(.)(?=.*\\1)", ""));
		System.out.println(sb.reverse().toString());
		System.out.println();
	}

	/**
	 * 取出字符串中的数字。
	 * 
	 * @param s
	 */
	public static void getNum(String s) {
		System.out.println("input: " + s);
		Matcher m = Pattern.compile("\\d+").matcher(s);
		String r = "";
		while (m.find()) {
			r += m.group();
		}
		System.out.println(r);
	}

	/**
	 * 去掉字符串中的数字
	 * 
	 * @param s
	 */
	public static void removeDigit(String s) {
		System.out.println(s.replaceAll("\\d+", ""));
		System.out.println();
	}

}
