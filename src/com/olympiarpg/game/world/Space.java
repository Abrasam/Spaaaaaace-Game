package com.olympiarpg.game.world;

import java.util.HashMap;

import com.olympiarpg.game.main.Main;
import com.olympiarpg.game.util.Coordinate;

public class Space {
	public static HashMap<Coordinate, SpaceSector> sectors = new HashMap<Coordinate, SpaceSector>();

	public static int currentSectorX = 0;
	public static int currentSectorY = 0;

	public static void initNew() {
		sectors.put(new Coordinate(0, 0), new SpaceSector());
	}

	public static SpaceSector getCurrentSector() {
		return sectors.get(new Coordinate(currentSectorX, currentSectorY));
	}

	public static void addRelative(int dX, int dY) {
		Coordinate c = new Coordinate(currentSectorX + dX, currentSectorY + dY);
		if (!sectors.containsKey(c)) {
			sectors.put(c, new SpaceSector());
		}
		currentSectorX += dX;
		currentSectorY += dY;
		if (dX < 0) {
			Main.pX = 9;
		} else if (dX > 0) {
			Main.pX = 0;
		} else if (dY < 0) {
			Main.pY = 5;
		} else if (dY > 0) {
			Main.pY = 0;
		}
	}
}
