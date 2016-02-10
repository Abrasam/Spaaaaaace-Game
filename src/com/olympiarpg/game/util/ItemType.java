package com.olympiarpg.game.util;

public enum ItemType {
IRON_ORE("Iron Ore", "Mined by laser from the heart of an asteroid.", 50, 100),
PLATINUM_ORE("Platinum Ore", "Found only in the finest asteroids, with applications in space travel. Worth a lot to any space trader.", 150, 102),
SCRAP("Scrap", "Scrap metal found floating in space - worthless.", 4, 101);

	public String name;
	public String desc;
	public int value;
	public int tex;
	
	private ItemType(String name, String desc, int value, int tex) {
		this.name = name;
		this.desc = desc;
		this.value = value;
		this.tex = tex;
	}
}
