package com.olympiarpg.game.util;

public class Item {
	private String name;
	private int value;
	private String description;
	
	public Item(String n, String d, int v) {
		setName(n);
		setDescription(d);
		setValue(v);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
