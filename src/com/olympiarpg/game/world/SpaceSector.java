package com.olympiarpg.game.world;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;

public class SpaceSector {

	private int[][] tiles = new int[10][6];

	public SpaceSector() {
		weights.put(1, 6);
		weights.put(2, 2);
		weights.put(3, 59);
		weights.put(4, 2);
		weights.put(5, 3);
		weights.put(6, 12);
		weights.put(7, 2);
		weights.put(8, 59);
		weights.put(9, 2);
		weights.put(10, 6);
		weights.put(11, 3);
		generate();
	}

	public int[][] getTiles() {
		return tiles;
	}

	Random r = new Random();
	HashMap<Integer, Integer> weights = new HashMap<Integer, Integer>();

	public void generate() {
		//Basic randomiser. To be replaced with a good clever one that adds quests and planets and shit.
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 6; y++) {
				tiles[x][y] = 0;
			}
		}
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 6; y++) {
				tiles[x][y] = getRandomTexture() + 1;
			}
		}

		if (r.nextInt(3) == 1) {
			int x = r.nextInt(9);
			int y = r.nextInt(5);
			int rand = r.nextInt(3);
			if (rand == 1) {
				tiles[x][y] = 20;
				tiles[x+1][y] = 21;
				tiles[x+1][y+1] = 22;
				tiles[x][y+1] = 23;
			} else if (rand == 0) {
				tiles[x][y] = 50;
				tiles[x+1][y] = 51;
				tiles[x+1][y+1] = 52;
				tiles[x][y+1] = 53;
			}
		}

		if (r.nextInt(3) == 1) {
			int x = r.nextInt(9);
			int y = r.nextInt(5);
			if (tiles[x][y] != 20 && tiles[x][y] != 21 && tiles[x][y] != 22 && tiles[x][y] != 23 && tiles[x][y] != 50 && tiles[x][y] != 51 && tiles[x][y] != 52 && tiles[x][y] != 53) {
				tiles[x][y] = 70;
			}
		}
	}

	private int getRandomTexture() {
		int randomIndex = -1;
		double random = Math.random() * 154;
		for (int i = 0; i < 11; ++i)
		{
			random -= weights.get(i+1);
			if (random <= 0.0d)
			{
				randomIndex = i;
				break;
			}
		}
		return randomIndex;
	}

	public void draw(HashMap<Integer, Texture> textures) {
		glEnable(GL_TEXTURE_2D);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 6; y++) {
				textures.get(tiles[x][y]).bind();
				glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2i(128*x, 128*y);
				glTexCoord2f(1, 0);
				glVertex2i(128*x+128, 128*y);
				glTexCoord2f(1, 1);
				glVertex2i(128*x+128, 128*y+128);
				glTexCoord2f(0, 1);
				glVertex2i(128*x, 128*y+128);
				glEnd();
			}
		}
	}
}
