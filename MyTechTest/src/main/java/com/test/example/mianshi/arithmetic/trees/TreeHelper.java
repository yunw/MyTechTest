package com.test.example.mianshi.arithmetic.trees;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

public class TreeHelper {

	private TreeNode root;

	private List<TreeNode> tempNodeList;

	private boolean isValidTree = true;

	public TreeHelper() {
	}

	/**
	 * 创建一棵树
	 * 
	 * @param treeNodeList
	 */
	public TreeHelper(List<TreeNode> treeNodeList) {
		tempNodeList = treeNodeList;
		generateTree();
	}

	public static TreeNode getTreeNodeById(TreeNode tree, int id) {
		if (tree == null)
			return null;
		TreeNode treeNode = tree.findTreeNodeById(id);
		return treeNode;
	}

	/**
	 * generate a tree from the given treeNode or entity list
	 */
	public void generateTree() {
		HashMap<Integer, TreeNode> nodeMap = putNodesIntoMap();
		putChildIntoParent(nodeMap);
	}

	/**
	 * put all the treeNodes into a hash table by its id as the key
	 * 
	 * @return hashmap that contains the treenodes
	 */
	protected HashMap<Integer, TreeNode> putNodesIntoMap() {
		HashMap<Integer, TreeNode> nodeMap = new HashMap<Integer, TreeNode>();
		Iterator<TreeNode> it = tempNodeList.iterator();
		while (it.hasNext()) {
			TreeNode treeNode = it.next();
			int id = treeNode.getNodeInfo().getId();
			if (treeNode.getParent() == null) {
				this.root = treeNode;
			}
			nodeMap.put(id, treeNode);
			System.out.println("keyId: " + id);
		}
		return nodeMap;
	}

	/**
	 * set the parent nodes point to the child nodes
	 * 
	 * @param nodeMap
	 *            a hashmap that contains all the treenodes by its id as the key
	 */
	protected void putChildIntoParent(HashMap<Integer, TreeNode> nodeMap) {
		Iterator<TreeNode> it = nodeMap.values().iterator();
		while (it.hasNext()) {
			TreeNode treeNode = it.next();
			if (treeNode.getParent() == null) {
				System.out.println("childId: " + treeNode.getNodeInfo().getId()
				        + " parentId: null, This is a root node.");
				continue;
			}
			int parentId = treeNode.getParent().getNodeInfo().getId();
			if (nodeMap.containsKey(parentId)) {
				TreeNode parentNode = (TreeNode) nodeMap.get(parentId);
				if (parentNode == null) {
					this.isValidTree = false;
					return;
				} else {
					parentNode.addChild(treeNode);
					System.out.println("childId: " + treeNode.getNodeInfo().getId() + " parentId: "
					        + parentNode.getNodeInfo().getId());
				}
			}
		}
	}

	/** initialize the tempNodeList property */
	protected void initTempNodeList() {
		if (this.tempNodeList == null) {
			this.tempNodeList = new ArrayList<TreeNode>();
		}
	}

	/** add a tree node to the tempNodeList */
	public void addTreeNode(TreeNode treeNode) {
		initTempNodeList();
		this.tempNodeList.add(treeNode);
	}

	/**
	 * insert a tree node to the tree generated already
	 * 
	 * @return show the insert operation is ok or not
	 */
	public boolean insertTreeNode(TreeNode treeNode) {
		boolean insertFlag = root.insertJuniorNode(treeNode);
		return insertFlag;
	}

	/**
	 * adapt the entities to the corresponding treeNode
	 * 
	 * @param entityList
	 *            list that contains the entities
	 * @return the list containg the corresponding treeNodes of the entities
	 */
	public static List<TreeNode> changeEnititiesToTreeNodes(List<NodeInfo> entityList) {
		List<TreeNode> tempNodeList = new ArrayList<TreeNode>();
		TreeNode treeNode;

		Iterator<NodeInfo> it = entityList.iterator();
		while (it.hasNext()) {
			NodeInfo nodeInfo = it.next();
			treeNode = new TreeNode();
			treeNode.setNodeInfo(nodeInfo);
			tempNodeList.add(treeNode);
		}
		return tempNodeList;
	}

	public boolean isValidTree() {
		return this.isValidTree;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<TreeNode> getTempNodeList() {
		return tempNodeList;
	}

	public void setTempNodeList(List<TreeNode> tempNodeList) {
		this.tempNodeList = tempNodeList;
	}

	public static void main(String[] args) {
		createNodes();
	}

	public static void createNodes() {
		List<TreeNode> nodeList = new ArrayList<TreeNode>();

		TreeNode node1 = new TreeNode();
		NodeInfo oe1 = new NodeInfo();
		oe1.setId(1);
		oe1.setName("the first node.");
		node1.setNodeInfo(oe1);
		node1.setParent(null);
		nodeList.add(node1);

		TreeNode node2 = new TreeNode();
		NodeInfo oe2 = new NodeInfo();
		oe2.setId(2);
		oe2.setName("the second node.");
		node2.setNodeInfo(oe2);
		node2.setParent(node1);
		nodeList.add(node2);

		TreeNode node3 = new TreeNode();
		NodeInfo oe3 = new NodeInfo();
		oe3.setId(3);
		oe3.setName("the third node.");
		node3.setNodeInfo(oe3);
		node3.setParent(node2);
		nodeList.add(node3);

		TreeHelper helper = new TreeHelper(nodeList);
		helper.generateTree();
		HashMap<Integer, TreeNode> nodeMap = new HashMap<Integer, TreeNode>();
		nodeMap.put(1, node1);
		nodeMap.put(2, node1);
		nodeMap.put(3, node1);

		helper.putChildIntoParent(nodeMap);
	}

}
