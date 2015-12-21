package com.olympiarpg.game.util;

public class Item {
	private String name;
	private int value;
	private String description;
	private ItemType type;
	public int texId;
	
	public Item(String n, String d, int v, ItemType type, int texId) {
		setName(n);
		setDescription(d);
		setValue(v);
		this.type = type;
		this.texId = texId;
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

	public ItemType getType() {
		return type;
	}
}
