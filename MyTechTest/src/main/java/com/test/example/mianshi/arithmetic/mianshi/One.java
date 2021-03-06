package com.test.example.mianshi.arithmetic.mianshi;

/**
 * 五只猴子分桃。半夜，第一只猴子先起来，它把桃分成了相等的五堆，多出一只。于是，它吃掉了一个，拿走了一堆；
 * 第二只猴子起来一看，只有四堆桃。于是把四堆合在一起，分成相等的五堆，又多出一个。于是，它也吃掉了一个， 拿走了一堆；.....其他几只猴子也都是
 * 这样分的。问：这堆桃至少有多少个？（朋友说，这是小学奥数题）。
 * 
 * @author Administrator
 *
 */
public class One {

	/**
	 * 参考答案：先给这堆桃子加上4个,设此时共有X个桃子,最后剩下a个桃子.这样: 
	 * 第一只猴子分完后还剩:(1-1/5)X=(4/5)X;
	 * 第二只猴子分完后还剩:(1-1/5)2X; 
	 * 第三只猴子分完后还剩:(1-1/5)3X; 
	 * 第四只猴子分完后还剩:(1-1/5)4X;
	 * 第五只猴子分完后还剩:(1-1/5)5X=(1024/3125)X; 
	 * 得:a=(1024/3125)X; 
	 * 要使a为整数,X最小取3125.
	 * 减去加上的4个,所以,这堆桃子最少有3121个。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 假设最后一堆有a个
		int a = 0;
		// 倒数第二堆有b个
		int b = (5 * a + 1) / 4;
		// 倒数第三堆有c个
		int c = (5 * (5 * a + 1) / 4 + 1) / 4;
		// 倒数第四堆有d个
		int d = (5 * (5 * (5 * a + 1) / 4 + 1) / 4 + 1) / 4;
		// 桃子总数为e个
		int e = 5 * (5 * (5 * (5 * a + 1) / 4 + 1) / 4 + 1) / 4 + 1;
		e = (((625 * a + 125) / 4 + 25) / 4 + 5) / 4 + 1;
        System.out.println(a + b + c + d + e);
	}

}
