package com.olympiarpg.game.util;

import java.util.HashMap;

public class Stats {
	public static int fuel;
	public static int money;
	public static HashMap<ItemType, Integer> inventory;
	
	public static void initNew() {
		fuel = 100;
		money = 1000;
		inventory = new HashMap<ItemType, Integer>();
	}
	
	public static void add(ItemType t, int n) {
		if (inventory.get(t) != null) {
			inventory.put(t, inventory.get(t) + n);
		} else {
			inventory.put(t, n);
		}
	}
	
	public static void remove(ItemType t, int n) {
		if (inventory.get(t) != null) {
			inventory.put(t,  (inventory.get(t) - n < 0 ? 0 : inventory.get(t) - n));
		}
	}
}
