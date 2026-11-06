package worldassets.area;

import world.WorldChunk;
import worldassets.Position;
import worldassets.tile.Tile;

import java.io.Serializable;
import java.util.Random;

public class Room extends Area implements Serializable {
    public final Random rand;
    public Room(Position pos, int width, int height, WorldChunk map, Random rand) {
        super(pos, width, height, map);
        this.rand = rand;
    }

    public Tile getRandomEmptyTile(WorldChunk map) {
        int xOff, yOff;
        Tile choosenTile;
        while (true) {
            xOff = 1 + rand.nextInt(getWidth() - 2);
            yOff = 1 + rand.nextInt(getHeight() - 2);
            choosenTile = map.getTile((int) this.getPosition().getX() + xOff, (int) this.getPosition().getY() - yOff);
            if (!choosenTile.hasEntity()) {
                return choosenTile;
            }
        }
    }
}
