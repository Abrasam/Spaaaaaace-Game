package com.olympiarpg.game.main;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.olympiarpg.game.util.Mode;
import com.olympiarpg.game.util.SoundUtil;
import com.olympiarpg.game.world.Planet;
import com.olympiarpg.game.world.Space;

/*
 * TODO: Further "wall of separation".
 */

public class Main {
	
	//Font used for text.
	private static TrueTypeFont font;
		
	//Location of player.
	public static int pX = 5;
	public static int pY = 4;
	
	//Texture map.
	public static HashMap<Integer, Texture> textures = new HashMap<Integer, Texture>();
	
	//Mode, space or planet, defaults to space.
	public static Mode gameMode = Mode.SPACE;
	
	//Current planet, defaults to null as you start in space.
	public static Planet currentPlanet = null;
	
	//Movement variables.
	private static int counter = -1;
	private static int xF = 5;
	private static int yF = 4;
	private static int direction = 0; //0=up, 1=right, 2=down, 3=left
		
	public static void main(String[] args) {
		//Initialise display.
		try {
			Display.setDisplayMode(new DisplayMode(1280, 768+50));
			Display.setTitle("Spaaaaace!");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		
		//Initialise OpenGL!
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1280, 768+50, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		//Enable OpenGL features.
		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		//Load textures. TODO: Add texture rotations.
		try {
			textures.put(1, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space1.png")), GL_NEAREST));
			textures.put(2, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space2.png")), GL_NEAREST));
			textures.put(3, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space3.png")), GL_NEAREST));
			textures.put(4, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space4.png")), GL_NEAREST));
			textures.put(5, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space5.png")), GL_NEAREST));
			textures.put(6, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space6.png")), GL_NEAREST));
			textures.put(7, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space7.png")), GL_NEAREST));
			textures.put(20, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/blackhole1.png")), GL_NEAREST));
			textures.put(21, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/blackhole2.png")), GL_NEAREST));
			textures.put(22, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/blackhole3.png")), GL_NEAREST));
			textures.put(23, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/blackhole4.png")), GL_NEAREST));
			textures.put(50, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/supernova1.png")), GL_NEAREST));
			textures.put(51, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/supernova2.png")), GL_NEAREST));
			textures.put(52, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/supernova3.png")), GL_NEAREST));
			textures.put(53, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/supernova4.png")), GL_NEAREST));
			textures.put(42, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/rocket.png")), GL_NEAREST));
			textures.put(43, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/rocketf.png")), GL_NEAREST));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Load font.
		Font f = new Font("Arial", Font.BOLD, 24);
		font = new TrueTypeFont(f, false);
		
		//Initialise sound.
		try {
			SoundUtil.playSound("main");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Initialise game.
		Space.init();
		
		//Render loop.
		while (!Display.isCloseRequested()) {
			render();
		}
		Display.destroy();
	}
	
	public static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		Space.getCurrentSector().draw(textures);
		
		//drawGrid();
		
		drawShip(pX, pY);
		
		drawHUD();
				
		Display.update();
		Display.sync(60);
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
	
	public static void drawHUD() {
		if (gameMode == Mode.SPACE) {
			glDisable(GL_TEXTURE_2D);
			int sX = Math.round(Mouse.getX()/128);
			int sY = Math.round((768+50-Mouse.getY()-1)/128);
			glColor4f(0, 0, 0, 0.35f);
			glRecti(sX*128, sY*128+1, sX*128+128, sY*128+128+1);
			glColor4f(0, 191, 255, 1);
			glRecti(0, 769, 1280, 768+50);
			glEnable(GL_TEXTURE_2D);
			font.drawString(200, 775, "HUD goes here!", Color.magenta);
			Color.white.bind();
		} else {
			//PLANET HUD HERE.
		}
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
			keyboardMovement();
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
	
	public static void keyboardMovement() {
		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				switch (direction) {
				case 0:
					yF -= 1;
					break;
				case 1:
					xF += 1;
					break;
				case 2:
					yF += 1;
					break;
				case 3:
					xF -= 1;
				}
				counter = 60;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				switch (direction) {
				case 0:
					yF -= -1;
					break;
				case 1:
					xF += -1;
					break;
				case 2:
					yF += -1;
					break;
				case 3:
					xF -= -1;
				}
				counter = 60;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { 
				direction += (direction == 0 ? 3 : -1);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				direction -= (direction == 3 ? 3 : -1);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				Space.getCurrentSector().generate();
			}
		}
	}
}