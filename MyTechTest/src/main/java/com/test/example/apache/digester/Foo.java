package com.test.example.apache.digester;

import java.util.ArrayList;
import java.util.List;

public class Foo {

	private String name;

	private List<Bar> barList = new ArrayList<Bar>();

	public void addBar(Bar bar) {
		barList.add(bar);
	}

	public Bar findBar(int id) {
		for (Bar bar : barList) {
			if (id == bar.getId()) {
				return bar;
			}
		}
		return null;
	}

	public List<Bar> getBars() {
		return barList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
