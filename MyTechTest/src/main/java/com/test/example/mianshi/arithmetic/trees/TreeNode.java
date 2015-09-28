package com.test.example.mianshi.arithmetic.trees;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings("serial")
public class TreeNode implements Serializable {

	private TreeNode parent;

	private NodeInfo nodeInfo;

	private List<TreeNode> children;

	public TreeNode() {
		initChildren();
	}

	public TreeNode(TreeNode parentNode) {
		getParent();
		initChildren();
	}

	/**
	 * 是否为叶子结点
	 * 
	 * @return true：是；false：否
	 */
	public boolean isLeaf() {
		if (children == null || children.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 插入一个child节点到当前节点中
	 */
	public void addChild(TreeNode treeNode) {
		initChildren();
		children.add(treeNode);
	}

	public void initChildren() {
		if (children == null)
			children = new ArrayList<TreeNode>();
	}

	public boolean isValidTree() {
		return true;
	}

	/**
	 * 返回当前节点的父辈节点集合
	 */
	public List<TreeNode> getElders() {
		List<TreeNode> elderList = new ArrayList<TreeNode>();
		if (parent == null) {
			return elderList;
		} else {
			elderList.add(parent);
			elderList.addAll(parent.getElders());
			return elderList;
		}
	}

	/** 
	 * 返回当前节点的晚辈集合 
	 */
	public List<TreeNode> getJuniors() {
		List<TreeNode> juniorList = new ArrayList<TreeNode>();
		List<TreeNode> childList = getChildren();
		if (childList == null) {
			return juniorList;
		} else {
			int childNumber = childList.size();
			for (int i = 0; i < childNumber; i++) {
				TreeNode junior = childList.get(i);
				juniorList.add(junior);
				juniorList.addAll(junior.getJuniors());
			}
			return juniorList;
		}
	}

	/**
	 *  返回当前节点的孩子集合 
	 */
	public List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * 删除节点和它下面的晚辈
	 */
	public void deleteNode() {
		if (parent != null) {
			parent.deleteChildren(nodeInfo.getId());
		}
	}

	/**
	 * 删除当前节点的某个子节点
	 */
	public void deleteChildren(int childId) {
		List<TreeNode> childList = getChildren();
		int childNumber = childList.size();
		for (int i = 0; i < childNumber; i++) {
			TreeNode child = childList.get(i);
			if (child.nodeInfo.getId() == childId) {
				childList.remove(i);
				return;
			}
		}
	}

	/**
	 * 动态的插入一个新的节点到当前树中
	 */
	public boolean insertJuniorNode(TreeNode treeNode) {
		int juniorParentId = treeNode.parent.nodeInfo.getId();
		if (nodeInfo.getId() == juniorParentId) {
			addChild(treeNode);
			return true;
		} else {
			List<TreeNode> childList = this.getChildren();
			int childNumber = childList.size();
			boolean insertFlag;

			for (int i = 0; i < childNumber; i++) {
				TreeNode childNode = childList.get(i);
				insertFlag = childNode.insertJuniorNode(treeNode);
				if (insertFlag == true)
					return true;
			}
			return false;
		}
	}

	/**
	 * 找到一颗树中某个节点
	 */
	public TreeNode findTreeNodeById(int id) {
		if (this.nodeInfo.getId() == id) {
			return this;
		}
		if (this.isLeaf()) {
			return null;
		} else {
			int childNumber = children.size();
			for (int i = 0; i < childNumber; i++) {
				TreeNode child = children.get(i);
				TreeNode resultNode = child.findTreeNodeById(id);
				if (resultNode != null) {
					return resultNode;
				}
			}
			return null;
		}
	}

	/**
	 * 遍历一棵树，层次遍历
	 */
	public void traverse() {
		if (this.nodeInfo.getId() < 0)
			return;
		System.out.println(this.nodeInfo.getId());
		if (isLeaf())
			return;
		int childNumber = children.size();
		for (int i = 0; i < childNumber; i++) {
			TreeNode child = children.get(i);
			child.traverse();
		}
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

}
