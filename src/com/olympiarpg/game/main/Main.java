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
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.olympiarpg.game.util.Item;
import com.olympiarpg.game.util.ItemType;
import com.olympiarpg.game.util.Mode;
import com.olympiarpg.game.util.Render;
import com.olympiarpg.game.util.SoundUtil;
import com.olympiarpg.game.util.Stats;
import com.olympiarpg.game.world.Planet;
import com.olympiarpg.game.world.Space;

public class Main {
	
	//Font used for text.
	public static TrueTypeFont font;
	public static TrueTypeFont deadFont;
	
	//Location of player.
	public static int pX = 5;
	public static int pY = 4;
	
	//Texture map.
	public static HashMap<Integer, Texture> textures = new HashMap<Integer, Texture>();
	
	//Mode, space or planet, defaults to space.
	public static Mode gameMode = Mode.SPACE;
	public static Mode pMode;
	
	//Current planet, defaults to null as you start in space.
	public static Planet currentPlanet = null;
	
	//Movement variables.
	public static int counter = -1;
	public static int xF = 5;
	public static int yF = 4;
	public static int direction = 0; //0=up, 1=right, 2=down, 3=left
	
	//Stuff and things.
	public static boolean isDead = false;
	
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
			textures.put(8, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space8.png")), GL_NEAREST));
			textures.put(9, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space9.png")), GL_NEAREST));
			textures.put(10, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space10.png")), GL_NEAREST));
			textures.put(11, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/space11.png")), GL_NEAREST));
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
			textures.put(70, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/sat1.png")), GL_NEAREST));
			textures.put(100, TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/oretest.png")), GL_NEAREST));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Load font. TODO: Add better fonts.
		Font f = new Font("Arial", Font.BOLD, 24);
		font = new TrueTypeFont(f, true);
		f = new Font("Arial", Font.BOLD, 48);
		deadFont = new TrueTypeFont(f, true);
		
		//Initialise sound.
		try {
			SoundUtil.playSound("main");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Stuff and things.
		Keyboard.enableRepeatEvents(false);
		
		//Initialise game.
		Space.initNew();
		Stats.initNew();
		
		//Render loop.
		while (!Display.isCloseRequested()) {
			render();
			amIDead();
		}
		Display.destroy();
	}
	
	private static void amIDead() {
		if (Stats.fuel <= 0) {
			isDead = true;
		}
		
	}

	public static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		if (gameMode == Mode.SPACE) {
		
			Space.getCurrentSector().draw(textures);
			
			//drawGrid();
			if (!isDead) {
				Render.drawShip(pX, pY);
			} else {
				Render.die("You're out of fuel...");
			}
			 
			Render.drawHUD(false);
			Render.drawShop();
		} else if (gameMode == Mode.INVENTORY) {
			Render.drawInventory();
			Render.drawHUD(true);
		} else {
			System.out.println("ERROR!");
		}
				
		Display.update();
		Display.sync(60);
	}

	public static void action() {
		if (Space.getCurrentSector().getTiles()[pX][pY] == 11) {
			Space.getCurrentSector().getTiles()[pX][pY] = 3;
			Stats.inventory.add(new Item("Test Ore", "Some delicious test ore.", 22, ItemType.TEST, 100));
		}
	}
	
	public static void moveEvent() {
		Stats.fuel--;
	}
}