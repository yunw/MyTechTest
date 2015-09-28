package com.test.example.mianshi.arithmetic.trees;

/**
 * 节点信息
 * 
 * @author Administrator
 *
 */
public class NodeInfo {

	private int id;

	private String name;

	public NodeInfo() {
	}

	public NodeInfo(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
