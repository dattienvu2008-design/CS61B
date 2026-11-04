package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.Tileset;
import world.World;
import Entity.Player;
import worldassets.Direction;
import worldassets.Room;
import worldassets.Tile;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameRunner implements Serializable {
    public final int WIDTH, HEIGHT;
    public final int AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT;
    public final int STANDARD_HEALTH;

    private HashSet<World> worlds;

    private World world;
    private void setWorld(World world) {
        this.world = world;
    }

    public GameRunner(int width, int height, int avgRoomWidth, int avgRoomHeight, int standardHealth) {
        WIDTH = width;
        HEIGHT = height;
        AVG_ROOM_HEIGHT = avgRoomHeight;
        AVG_ROOM_WIDTH = avgRoomWidth;
        this.STANDARD_HEALTH = standardHealth;
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
        setWorld(new World(initNumRooms, WIDTH, HEIGHT - 6, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, seed));
        world.addPlayer(new Player(STANDARD_HEALTH, 1, Tileset.PLAYER, chooseRandomStartingPos(rand), world));
        world.addPlayer(new Player(15, 2, Tileset.PLAYER, chooseRandomStartingPos(rand), world));
        HashSet<Character> player1ActionLst = new HashSet<>(Set.of('w', 'a', 's', 'd', 'z'));

        char c;
        Game:
        while (true) {
            ter.renderFrame(world.map);
            while (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = (Character.isAlphabetic(c))? Character.toLowerCase(c) : c;

                if ((c == 'w' || c == 'a' || c == 's' || c == 'd') && world.getPlayer(0).movementCooldown.onCooldown()) continue;

                if (player1ActionLst.contains(c)) {
                    player1Action(c);
                } else if (c == 'q') {
                    break Game;
                }
            }
        }
    }

    private void player1Action(char c) {
        Player player = world.getPlayer(0);
        switch (c) {
            case 'w':
                player.move(Direction.UP);
                player.setCurrentDirection(Direction.UP);
                player.movementCooldown.startCooldown(player.getMovementCooldownMillis());
                break ;
            case 's':
                player.move(Direction.DOWN);
                player.setCurrentDirection(Direction.DOWN);
                player.movementCooldown.startCooldown(player.getMovementCooldownMillis());
                break ;
            case 'a':
                player.move(Direction.LEFT);
                player.setCurrentDirection(Direction.LEFT);
                player.movementCooldown.startCooldown(player.getMovementCooldownMillis());
                break ;
            case 'd':
                player.move(Direction.RIGHT);
                player.setCurrentDirection(Direction.RIGHT);
                player.movementCooldown.startCooldown(player.getMovementCooldownMillis());
                break;
            case 'z':
                player.useSkill(0, player.getCurrentDirection());
            case '\u001B':
                break; //esc button, use to quit game or smt else?
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
