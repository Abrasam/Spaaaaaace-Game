package com.olympiarpg.game.util;

import org.lwjgl.opengl.GL11;

public class Shop {
	private String name;
	private float mod;
	private String ownerName;
	
	public Shop(String name, float mod, String ownerName) {
		this.setName(name);
		this.mod = mod;
		this.ownerName = ownerName;
	}

	public float getMod() {
		return mod;
	}

	public void setMod(float mod) {
		this.mod = mod;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void draw() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(127f/255, 127f/255, 127f/255);
		GL11.glRecti(100, 100, 1080, 668);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glRecti(110, 110, 1070, 658);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
