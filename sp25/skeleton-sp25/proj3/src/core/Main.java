package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.Tileset;
import utils.RandomUtils;
import world.World;
import worldassets.Avatar;
import worldassets.Direction;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final int WIDTH = 90, HEIGHT = 48;
    public static final int AVG_ROOM_WIDTH = 14, AVG_ROOM_HEIGHT = 11;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World blankWorld = new World(0, 0, 0, 0, 0, 0);
        Random gameDefaultRandom = new Random();
        char c;
        boolean isGameStarted = false;
        int currentSeed = 0, initNumRooms = 9;
        ter.renderFrame(blankWorld.map);
        Game:
        while (true) {
            StdDraw.clear(new Color(0, 0, 0));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
            StdDraw.text((double) WIDTH/2.0, (double) (HEIGHT * 6) /7, "CS61B");
            StdDraw.text((double) WIDTH /2.0, (double) (HEIGHT * 4.6) /7, "(N) New Game");
            StdDraw.text((double) WIDTH /2.0, (double) (HEIGHT * 2.8) /7, "(L) Load Game");
            StdDraw.text((double) WIDTH /2.0, (double) (HEIGHT * 1.0) /7, "(Q) Quit Game");
            StdDraw.show();
            MainMenu:
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toLowerCase(c);
                switch (c) {
                    case 'n':
                        isGameStarted = true;
                        currentSeed = gameDefaultRandom.nextInt();
                        break MainMenu;
                    case 'l':
                        isGameStarted = true;
                        currentSeed = loadGame(); // Modified later for custom seed input
                        break MainMenu;
                    case 'q':
                        break Game;
                }
            }
            if (isGameStarted) {
                gameRunner(currentSeed, ter);
                isGameStarted = false;
            }
        }
        System.exit('0');
    }

    private static void mainMenu() {

    }

    private static int loadGame() {
        char c;
        int seed = 0;
        SeedInput:
        while (true) {
            StdDraw.clear(new Color(0, 0, 0));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
            StdDraw.text((double) WIDTH/2.0, (double) (HEIGHT * 6) /7, "CS61B");
            StdDraw.text((double) WIDTH/2.0, (double) (HEIGHT * 4.5) /7, "Enter seed (Press Enter to start game):");
            StdDraw.text((double) WIDTH/2.0, (double) (HEIGHT * 3.8) /7, "" + seed);

            StdDraw.show();
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (Character.isDigit(c) && seed < 1000000000 && seed > -1000000000) {
                    if (seed >= 0) {
                        seed *= 10;
                        seed += Integer.parseInt(Character.toString(c));
                    } else {
                        seed *= 10;
                        seed -= Integer.parseInt(Character.toString(c));
                    }
                } else if (c == '-') {
                    seed = -seed;
                } else if (seed >= 1000000000) {
                    // print screen "limit reached"
                } else if (c == '\n') {
                   return seed;
                }
            }
        }
    }

    private static void gameRunner(int seed, TERenderer ter) {
        ter.resetFont();
        Random rand = new Random(seed);
        int initNumRooms = 9 + rand.nextInt(14);
        char c;
        World world = new World(initNumRooms, WIDTH, HEIGHT - 6, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, seed);
        Avatar player = new Avatar(
                10, 1, Tileset.PLAYER, world.map.getTile(world.map.getRoom(0).getPosition()).getBottomRightTile());
        Game:
        while (true) {
            ter.renderFrame(world.map);
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (Character.isAlphabetic(c)) {
                    c = Character.toLowerCase(c);
                }
                switch (c) {
                    case 'w':
                        player.move(Direction.UP);
                        break ;
                    case 's':
                        player.move(Direction.DOWN);
                        break ;
                    case 'a':
                        player.move(Direction.LEFT);
                        break ;
                    case 'd':
                        player.move(Direction.RIGHT);
                        break;
                    case 'q':
                        break Game;
                }
            }
        }
    }
}
