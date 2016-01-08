package com.olympiarpg.game.util;

import org.lwjgl.input.Keyboard;

import com.olympiarpg.game.world.Space;

import static com.olympiarpg.game.main.Main.*;

public class Input {
	public static boolean shop;

	public static void keyboardInv() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			gameMode = pMode;
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
				moveEvent();
				counter = 60;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { 
				direction += (direction == 0 ? 3 : -1);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				direction -= (direction == 3 ? 3 : -1);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				Space.getCurrentSector().generate();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				pMode = (gameMode == Mode.SPACE || gameMode == Mode.PLANET ? gameMode : Mode.SPACE);
				gameMode = Mode.INVENTORY;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				action();
			} else if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
				Stats.inventory.add(new Item("Test Ore", "Test ore", 22, ItemType.TEST, 100));
			} else if (Keyboard.getEventKey() == Keyboard.KEY_L && Keyboard.getEventKeyState()) {
				shop = !shop;
			}
		}
	}
}
