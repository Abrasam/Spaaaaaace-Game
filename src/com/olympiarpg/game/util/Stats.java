package com.olympiarpg.game.util;

import java.util.ArrayList;

public class Stats {
	public static int fuel;
	public static int money;
	public static ArrayList<Item> inventory;
	
	public static void initNew() {
		fuel = 100;
		money = 1000;
		inventory = new ArrayList<Item>();
	}
}
