package Lab9;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task3 {
    public static final int WIDTH = 80, HEIGHT = 45;
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

    private static void addRandomSquare(TETile[][] world, java.util.Random rand) {
        TETile tile = Tileset.NOTHING;
        int randomTile = RandomUtils.uniform(rand, 1, 4);
        switch (randomTile) {
            case 1:
                tile = Tileset.WALL;
                break;
            case 2:
                tile = Tileset.WATER;
                break;
            case 3:
                tile = Tileset.FLOWER;
                break;
        }
        int size = RandomUtils.uniform(rand, 3, 7);
        int randX = RandomUtils.uniform(rand, 0, world.length);
        int randY = RandomUtils.uniform(rand, 0, world[0].length);
        addSquare(world, randX, randY, size, tile);
    }

    private static void copyWorld(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[0].length; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][(HEIGHT*80)/100];
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        fillWithTrees(world);

        ter.renderFrame(world);
        TETile[][] savedWorld = new TETile[WIDTH][HEIGHT*80/100];
        copyWorld(world, savedWorld);

        char c;
        int countSquares = 0;
        Random randEngine = new Random(RANDOM_SEED);
        game:
        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);

                switch (c) {
                    case 'n':
                        addRandomSquare(world, randEngine);
                        countSquares++;
                        break;
                    case 's':
                        copyWorld(world, savedWorld);
                        break;
                    case 'l':
                        copyWorld(savedWorld, world);
                        randEngine = new Random();
                        break;
                    case 'q':
                        System.exit(0);
                        break game;
                    default:
                        break;
                }
            }
            ter.renderFrame(world);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(10, (HEIGHT * 90)/100, "Number of squares: " + countSquares);
            StdDraw.show();
            StdDraw.pause(3);
        }
    }
}



