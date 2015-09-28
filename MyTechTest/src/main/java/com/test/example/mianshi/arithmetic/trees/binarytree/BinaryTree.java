package com.test.example.mianshi.arithmetic.trees.binarytree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BinaryTree {

	/**
	 * 创建一颗普通二叉树
	 * 
	 * @param array
	 * @return
	 */
	public static List<BinaryTreeNode> createBinTree(int[] array) {

		List<BinaryTreeNode> nodeList = new LinkedList<BinaryTreeNode>();
		for (int i = 0, len = array.length; i < len; i++) {
			nodeList.add(new BinaryTreeNode(array[i]));
		}

		int len = array.length;
		int lastRootIndex = (len >> 1) - 1;
		for (int i = lastRootIndex; i >= 0; i--) {
			BinaryTreeNode root = nodeList.get(i);
			int leftIndex = i * 2 + 1;
			BinaryTreeNode leftNode = nodeList.get(leftIndex);
			root.setLeft(leftNode);
			// 最后的那个根节点一定是有左孩子的。右孩子则不一定
			int rightIndex = leftIndex + 1;
			if (rightIndex <= len - 1) {
				BinaryTreeNode rightNode = nodeList.get(rightIndex);
				root.setRight(rightNode);
			}
		}
		return nodeList;
	}

	/**
	 * 前序遍历递归算法
	 * 
	 * @param root
	 *            根节点
	 */
	public static void preOrderRecursion(BinaryTreeNode root) {
		if (root == null) {
			return;
		}
		System.out.print(root.getData() + " ");
		preOrderRecursion(root.getLeft());
		preOrderRecursion(root.getRight());
	}

	/**
	 * 非递归实现前序遍历
	 */
	protected static void iterativePreorder(BinaryTreeNode rootNode) {
		Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
		if (rootNode != null) {
			stack.push(rootNode);
			while (!stack.empty()) {
				rootNode = stack.pop();
				System.out.print(rootNode.getData() + " ");
				if (rootNode.getRight() != null)
					stack.push(rootNode.getRight());
				if (rootNode.getLeft() != null)
					stack.push(rootNode.getLeft());
			}
		}
	}

	/**
	 * 中序遍历递归算法
	 * 
	 * @param root
	 *            根节点
	 */
	public static void inOrderRecursion(BinaryTreeNode root) {
		if (root == null) {
			return;
		}
		inOrderRecursion(root.getLeft());
		System.out.print(root.getData() + " ");
		inOrderRecursion(root.getRight());
	}

	/**
	 * 非递归实现中序遍历
	 */
	protected static void iterativeInorder(BinaryTreeNode root) {
		Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
		while (root != null) {
			while (root != null) {
				if (root.getRight() != null)
					stack.push(root.getRight());// 当前节点右子入栈
				stack.push(root);// 当前节点入栈
				root = root.getLeft();
			}
			root = stack.pop();
			while (!stack.empty() && root.getRight() == null) {
				System.out.print(root.getData() + " ");
				root = stack.pop();
			}
			System.out.print(root.getData() + " ");
			if (!stack.empty())
				root = stack.pop();
			else
				root = null;
		}
	}

	/**
	 * 后序遍历递归算法
	 * 
	 * @param root
	 *            根节点
	 */
	public static void postOrderRecursion(BinaryTreeNode root) {
		if (root == null) {
			return;
		}
		postOrderRecursion(root.getLeft());
		postOrderRecursion(root.getRight());
		System.out.print(root.getData() + " ");
	}

	/**
	 * 非递归实现后序遍历
	 */
	protected static void iterativePostorder(BinaryTreeNode root) {
		BinaryTreeNode q = root;
		Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
		while (root != null) {
			// 左子树入栈
			for (; root.getLeft() != null; root = root.getLeft())
				stack.push(root);
			// 当前节点无右子或右子已经输出
			while (root != null && (root.getRight() == null || root.getRight() == q)) {
				System.out.print(root.getData() + " ");
				q = root;// 记录上一个已输出节点
				if (stack.empty())
					return;
				root = stack.pop();
			}
			// 处理右子
			stack.push(root);
			root = root.getRight();
		}
	}

	public static void main(String[] args) {
		int[] array = { 10, 6, 14, 4, 8, 12, 16, 1, 2, 4, 3, 9, 18 };
		List<BinaryTreeNode> nodeList = BinaryTree.createBinTree(array);
		postOrderRecursion(nodeList.get(0));
		System.out.println();
		iterativePostorder(nodeList.get(0));
	}

}
