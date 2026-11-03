package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.Tileset;
import world.World;
import world.WorldMap;
import Entity.Entity;
import Entity.Player;
import worldassets.Direction;
import worldassets.Room;
import worldassets.Tile;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameRunner implements Serializable {
    public final int WIDTH, HEIGHT;
    public final int AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT;

    private World world;
    private void setWorld(World world) {
        this.world = world;
    }

    public GameRunner(int width, int height, int avgRoomWidth, int avgRoomHeight) {
        WIDTH = width;
        HEIGHT = height;
        AVG_ROOM_HEIGHT = avgRoomHeight;
        AVG_ROOM_WIDTH = avgRoomWidth;
    }

    public void startGame() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World blankWorld = new World(0, 0, 0, 0, 0, 0);
        ter.renderFrame(blankWorld.map);
        mainMenu(ter);
    }

    private void mainMenu(TERenderer ter) {
        boolean isGameStarted = false;
        Random gameDefaultRandom = new Random();
        int currentSeed = 0;
        char c; // For reading user character input

        Game:
        while (true) {
            StdDraw.clear(new Color(0, 0, 0));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
            StdDraw.text((double) WIDTH/2.0, (double) (HEIGHT * 6) /7, "CS61B");
            StdDraw.text((double) WIDTH /2.0, (double) (HEIGHT * 4.6) /7, "(N) New Game");
            StdDraw.text((double) WIDTH /2.0, (double) (HEIGHT * 2.8) /7, "(S) New Game (Custom seed)");
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
                    case 's':
                        isGameStarted = true;
                        currentSeed = seedInput();
                        break MainMenu;
                    case 'q':
                        break Game;
                }
            }
            if (isGameStarted) {
                gameRunnerSinglePlayer(currentSeed, ter);
                isGameStarted = false;
            }
        }
        System.exit('0');
    }

    private int seedInput() {
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

    private void gameRunnerSinglePlayer(int seed, TERenderer ter) {
        ter.resetFont();
        Random rand = new Random(seed);
        int initNumRooms = 9 + rand.nextInt(14);
        char c;
        setWorld(new World(initNumRooms, WIDTH, HEIGHT - 6, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, seed));
        world.addPlayer(new Player(10, 1, Tileset.PLAYER, chooseRandomStartingPos(rand)));
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
                        world.getPlayer(0).move(Direction.UP);
                        break ;
                    case 's':
                        world.getPlayer(0).move(Direction.DOWN);
                        break ;
                    case 'a':
                        world.getPlayer(0).move(Direction.LEFT);
                        break ;
                    case 'd':
                        world.getPlayer(0).move(Direction.RIGHT);
                        break;
                    case '\u001B':

                    case 'q':
                        break Game;
                }
            }
        }
    }

    private Tile chooseRandomStartingPos(Random rand) {
        int numRooms = world.map.getNumRooms();
        Room choosenRoom = world.map.getRoom(rand.nextInt(numRooms));
        Tile choosenTile = choosenRoom.getRandomEmptyTile(world.map);
        while (choosenTile.hasEntity()) {
            choosenRoom = world.map.getRoom(rand.nextInt(numRooms));
            choosenTile = choosenRoom.getRandomEmptyTile(world.map);
        }
        return choosenTile;
    }
}
