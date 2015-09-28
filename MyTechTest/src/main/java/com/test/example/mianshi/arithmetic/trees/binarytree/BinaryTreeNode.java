package com.test.example.mianshi.arithmetic.trees.binarytree;


public class BinaryTreeNode {

	private BinaryTreeNode left;

	private BinaryTreeNode right;

	private int data;

	public BinaryTreeNode(int iData) {
		data = iData;
	}

	public void setLeft(BinaryTreeNode left) {
		this.left = left;
	}

	public void setRight(BinaryTreeNode right) {
		this.right = right;
	}

	public BinaryTreeNode getLeft() {
		return left;
	}

	public BinaryTreeNode getRight() {
		return right;
	}

	public int getData() {
		return data;
	}

}
