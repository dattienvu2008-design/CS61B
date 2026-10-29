package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task2 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    public static final int WIDTH = 30, HEIGHT = 20;
    public static final int RANDOM_SEED = 123456;

    private static void fillWithTrees(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.TREE;
            }
        }
        return;
    }

    private static boolean validPos (int x, int y, TETile[][] world) {
        return x < world.length && x >= 0 && y < world[0].length && y >= 0;
    }

    private static void addSquare(TETile[][] world, int startX, int startY, int size, TETile tile) {
        for (int x = startX; x < startX + size; x++) {
            if (!validPos(x, 0, world)) continue;
            for (int y = startY; y > startY - size; y--) {
                if (!validPos(x, y, world)) continue;
                world[x][y] = tile;
            }
        }
    }

    private static void addRandomSquare(TETile[][] world, TETile tile, int size, java.util.Random rand) {
        int randX = RandomUtils.uniform(rand, 0, world.length);
        int randY = RandomUtils.uniform(rand, 0, world[0].length);
        addSquare(world, randX, randY, size, tile);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][15];
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        fillWithTrees(world);
        addRandomSquare(world, Tileset.FLOWER, 5, new Random(RANDOM_SEED + 100));
        ter.renderFrame(world);
    }
}



