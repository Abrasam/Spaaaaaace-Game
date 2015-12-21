package com.olympiarpg.game.util;

import static com.olympiarpg.game.main.Main.counter;
import static com.olympiarpg.game.main.Main.direction;
import static com.olympiarpg.game.main.Main.pX;
import static com.olympiarpg.game.main.Main.pY;
import static com.olympiarpg.game.main.Main.textures;
import static com.olympiarpg.game.main.Main.xF;
import static com.olympiarpg.game.main.Main.yF;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRecti;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.olympiarpg.game.main.Main;
import com.olympiarpg.game.world.Space;

public class Render {
	public static void drawHUD(boolean inv) {
		glDisable(GL_TEXTURE_2D);
		int sX = Math.round(Mouse.getX()/128);
		int sY = Math.round((768+50-Mouse.getY()-1)/128);
		glColor4f(0, 0, 0, 0.35f);
		glRecti(sX*128, sY*128, sX*128+128, sY*128+128);
		glColor4f(0, 191, 255, 1);
		glRecti(0, 769, 1280, 768+50);
		glEnable(GL_TEXTURE_2D);
		Main.font.drawString(64, 780, "Money: " + Stats.money, Color.blue);
		Main.font.drawString(256, 780, "Fuel: " + Stats.fuel, Color.blue);
		Main.font.drawString(256+128, 780, "Press " + (!inv ? "<E> to enter " : "<ESC> to leave ") + "inventory.", Color.blue);
		Color.white.bind();
	}

	public static void drawInventory() {
		glDisable(GL_TEXTURE_2D);
		glColor4f(80f/255, 80f/255, 80f/255, 1);
		glBegin(GL_QUADS);
			glVertex2f(0, 0);
			glVertex2f(1280, 0);
			glVertex2f(1280, 768);
			glVertex2f(0, 768);
		glEnd();
		glColor4f(127f/255, 127f/255, 127f/255, 1);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 6; y++) {
				glRecti(x*128+10, y*128+10, x*128+118, y*128+118);
			}
		}
		int noItems = 0;
		glEnable(GL_TEXTURE_2D);
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 10; x++) {
				if (noItems < Stats.inventory.size()) {
					textures.get(Stats.inventory.get(noItems).texId).bind();
					glBegin(GL_QUADS);
						glTexCoord2f(0, 0);
						glVertex2f(128*x+10, 128*y+10);
						glTexCoord2f(1, 0);
						glVertex2f(128*x+128-10, 128*y+10);
						glTexCoord2f(1, 1);
						glVertex2f(128*x+128-10, 128*y+128-10);
						glTexCoord2f(0, 1);
						glVertex2f(128*x+10, 128*y+128-10);
					glEnd();
					noItems++;
				}
			}
		}
		glEnable(GL_TEXTURE_2D);
		Input.keyboardInv();
	}
	
	public static void die(String msg) {
		Main.deadFont.drawString(100, 100, msg, Color.red);
		Color.white.bind();
		Main.isDead = true;
	}

	public static boolean hasMoved() {
		if (pX < 0) {
			Space.addRelative(-1, 0);
			return true;
		} else if (pX > 9) {
			Space.addRelative(1, 0);
			return true;
		} else if (pY < 0) {
			Space.addRelative(0, -1);
			return true;
		} else if (pY > 5) {
			Space.addRelative(0, 1);
			return true;
		}
		return false;
	}

	public static void drawGrid() {
		glDisable(GL_TEXTURE_2D);
		glColor4f(0, 191, 255, 0.25f);
		for (int x = 1; x <= 9; x++) {
			glLineWidth(1f);
			glBegin(GL_LINES);
				glVertex2i(x*128, 0);
				glVertex2i(x*128, 768);
			glEnd();
			glBegin(GL_LINES);
				glVertex2i(x*128+1, 0);
				glVertex2i(x*128+1, 768);
			glEnd();
		}
		for (int y = 1; y <= 5; y++) {
			glLineWidth(1f);
			glBegin(GL_LINES);
				glVertex2i(0, y*128);
				glVertex2i(1280, y*128);
			glEnd();
			glBegin(GL_LINES);
				glVertex2i(0, y*128+1);
				glVertex2i(1280, y*128+1);
			glEnd();
		}
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
	}


	public static void drawShip(int xS, int yS) {
		if (counter < 0) {
			textures.get(42).bind();
			pX = xF;
			pY = yF;
			if (hasMoved()) {
				xF = pX;
				yF = pY;
			}
			glMatrixMode(GL_TEXTURE);
			glLoadIdentity();
			glRotatef(-direction * 90, 0, 0, 1);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2i(128*pX, 128*pY);
				glTexCoord2f(1, 0);
				glVertex2i(128*pX+128, 128*pY);
				glTexCoord2f(1, 1);
				glVertex2i(128*pX+128, 128*pY+128);
				glTexCoord2f(0, 1);
				glVertex2i(128*pX, 128*pY+128);
			glEnd();
			glLoadIdentity();
			glMatrixMode(GL_MODELVIEW);
			Input.keyboardMovement();
		} else if (counter > 29) {
			textures.get(43).bind();
			glMatrixMode(GL_TEXTURE);
			glLoadIdentity();
			glRotatef(-direction * 90, 0, 0, 1);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2i(128*xS, 128*yS);
				glTexCoord2f(1, 0);
				glVertex2i(128*xS+128, 128*yS);
				glTexCoord2f(1, 1);
				glVertex2i(128*xS+128, 128*yS+128);
				glTexCoord2f(0, 1);
				glVertex2i(128*xS, 128*yS+128);
			glEnd();
			glLoadIdentity();
			glMatrixMode(GL_MODELVIEW);
			counter--;
		} else if (counter < 30) {
			textures.get(43).bind();
			glMatrixMode(GL_TEXTURE);
			glLoadIdentity();
			glRotatef(-direction * 90, 0, 0, 1);
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(128*xS + (xS-xF > 0 ? -64 : (xS-xF == 0 ? 0 : 64)), 128*yS + (yS-yF > 0 ? -64 : (yS-yF == 0 ? 0 : 64)));
				glTexCoord2f(1, 0);
				glVertex2f(128*xS+128 + (xS-xF > 0 ? -64 : (xS-xF == 0 ? 0 : 64)), 128*yS+ (yS-yF > 0 ? -64 : (yS-yF == 0 ? 0 : 64)));
				glTexCoord2f(1, 1);
				glVertex2f(128*xS+128 + (xS-xF > 0 ? -64 : (xS-xF == 0 ? 0 : 64)), 128*yS+128 + (yS-yF > 0 ? -64 : (yS-yF == 0 ? 0 : 64)));
				glTexCoord2f(0, 1);
				glVertex2f(128*xS+ (xS-xF > 0 ? -64 : (xS-xF == 0 ? 0 : 64)), 128*yS+128+ (yS-yF > 0 ? -64 : (yS-yF == 0 ? 0 : 64)));
			glEnd();
			glLoadIdentity();
			glMatrixMode(GL_MODELVIEW);
			counter--;
		}
	}
}
