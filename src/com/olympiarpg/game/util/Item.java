package com.olympiarpg.game.util;

public class Item {
	
	private ItemType type;
	private int count;

	public Item(ItemType type, int count) {
		this.type = type;
		this.count = count;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
