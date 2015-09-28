package com.test.example.mianshi.arithmetic.sort;

/**
 * 每次处理就是将无序数列的第一个元素与有序数列的元素从后往前逐个进行比较，找出插入位置，将该元素插入到有序数列的合适位置中。
 * 假设在一个无序的数组中，要将该数组中的数按插入排序的方法从小到大排序
 * 。假设啊a[]={3,5,2,1,4};插入排序的思想就是比大小，满足条件交换位置，一开始会像冒泡排序一样
 * ，但会比冒泡多一步就是交换后（a[i]=a[i+1
 * ]后）原位置（a[i]）会继续和前面的数比较满足条件交换，直到a[i+1]前面的数组是有序的。比如在第二次比较后数组变成a[]={2,3,5,1,4};
 */
public class DirectInsertSort {

	public static void main(String[] args) {
		int[] arr = { 5, 4, 1, 2, 6, 9, 8, 7 };
		arr = insertSort(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}

	private static int[] insertSort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			for (int j = i; j > 0; j--) {
				if (arr[j] < arr[j - 1]) {
					int temp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = temp;
				} else {
					break;
				}
			}
		}
		return arr;
	}

}
