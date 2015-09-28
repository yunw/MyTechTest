package com.test.example.mianshi.arithmetic.mianshi.four;

import java.util.List;

/**
 * 字典树
 * 
 * 又称单词查找树，Trie树，是一种树形结构，是一种哈希树的变种。
 * 典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。
 * 它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希表高。
 *
 * 性质 它有3个基本性质： 根节点不包含字符，除根节点外每一个节点都只包含一个字符； 从根节点到某一节点，路径上经过的字符连接起来， 为该节点对应的字符串；
 * 每个节点的所有子节点包含的字符都不相同。
 * 
 * 实现方法 搜索字典项目的方法为： (1) 从根结点开始一次搜索； (2)取得要查找关键词的第一个字母，并根据该字母选择对应的子树并转到该子树继续进行检索；
 * (3)在相应的子树上，取得要查找关键词的第二个字母,并进一步选择对应的子树进行检索。 (4) 迭代过程……
 * (5)在某个结点处，关键词的所有字母已被取出，则读取附在该结点上的信息，即完成查找。
 * 
 * 其他操作类似处理
 * 
 * 应用
 * 
 * 串的快速检索 给出N个单词组成的熟词表，以及一篇全用小写英文书写的文章，请你按最早出现的顺序写出所有不在熟词表中的生词。
 * 在这道题中，我们可以用数组枚举，用哈希，用字典树，先把熟词建一棵树，然后读入文章进行比较，这种方法效率是比较高的。
 * 
 * “串”排序 给定N个互不相同的仅由一个单词构成的英文名，让你将他们按字典序从小到大输出
 * 用字典树进行排序，采用数组的方式创建字典树，这棵树的每个结点的所有儿子很显然地按照其字母大小排序。 对这棵树进行先序遍历即可。
 * 
 * 最长公共前缀 对所有串建立字典树，对于两个串的最长公共前缀的长度即他们所在的结点的公共祖先个数，于是，问题就转化为当时公共祖先问题。
 */
public class Trie {

	private final static int SIZE = 10;

	/**
	 * 根节点
	 */
	private TrieNode root;

	public Trie() {
		// 初始化字典树
		root = new TrieNode();
	}

	/**
	 * 字典树节点
	 * 
	 * @author Administrator
	 *
	 */
	private class TrieNode {

		/**
		 * 所有的子节点
		 */
		private TrieNode[] son;

		/**
		 * 是否为最后一个节点
		 */
		private boolean isEnd;
		
		private long endNum;

		@SuppressWarnings("unused")
		/**
		 *  节点的值
		 */
		private char val;

		TrieNode() {
			son = new TrieNode[SIZE];
			isEnd = false;
			endNum = 0;
		}

	}

	/**
	 * 在字典树中插入一个单词
	 * 
	 * @param str
	 */
	public void insert(String str) {
		if (str == null || str.length() == 0) {
			return;
		}
		TrieNode node = root;
		char[] letters = str.toCharArray();
		for (int i = 0, len = str.length(); i < len; i++) {
			int pos = letters[i] - '0';
			if (node.son[pos] == null) {
				node.son[pos] = new TrieNode();
				node.son[pos].val = letters[i];
			}
			node = node.son[pos];
		}
		node.isEnd = true;
		node.endNum++;
	}

	/**
	 * 在字典树中查找一个完全匹配的单词.
	 * 
	 * @param str
	 *            目标单词
	 * @return 是否存在
	 */
	public boolean has(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		TrieNode node = root;
		char[] letters = str.toCharArray();
		for (int i = 0, len = str.length(); i < len; i++) {
			int pos = letters[i] - '0';
			if (node.son[pos] != null) {
				node = node.son[pos];
			} else {
				return false;
			}
		}
		return node.isEnd;
	}

	/**
	 *  前序遍历字典树.
	 * @param startNode
	 * @param nodeList
	 */
	public void preTraverse(TrieNode startNode, List<TrieNode> nodeList) {
		if (startNode != null) {
			if (startNode.isEnd) {
				nodeList.add(startNode);
			}
			for (TrieNode child : startNode.son) {
				preTraverse(child, nodeList);
			}
		}
	}
	
	/**
	 *  前序遍历字典树，获取重复次数为1的节点.
	 * @param startNode
	 * @param nodeList
	 */
	public void preTraverse2(TrieNode startNode, List<TrieNode> nodeList) {
		if (startNode != null) {
			if (startNode.isEnd && startNode.endNum == 1) {
				nodeList.add(startNode);
			}
			for (TrieNode child : startNode.son) {
				preTraverse(child, nodeList);
			}
		}
	}

	public TrieNode getRoot() {
		return this.root;
	}

}
