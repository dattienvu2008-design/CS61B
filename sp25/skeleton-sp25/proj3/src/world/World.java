package world;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.Tileset;
import utils.RandomUtils;
import worldassets.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
* Use for constructing map and storing information about state of that map (Specs may change in the future)
* */
public class World {
    // Attributes:
    public final WorldMap map;
    private /*final*/ HashMap<AreaType, Area[]> areas;
    private final int width, height, avgRoomWidth, avgRoomHeight;
    private final Random rand;
    private int currentRoomIndex;
    private final ArrayList<Room> rooms;

    // For Debugging only!!!
    // private TERenderer ter;

    static void main(String[] args) {
        final int WIDTH = 80, HEIGHT = 45;
        final int AVG_ROOM_WIDTH = 16, AVG_ROOM_HEIGHT = 13;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        for (int i = 0; i < 100; i++) {
            World world1 = new World(6, WIDTH, HEIGHT, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, i);
            ter.renderFrame(world1.map);
            StdDraw.pause(800);
        }
    }

    public World(int numRooms, int width, int height, int avgRoomWidth, int avgRoomHeight, int seed /* And some more parameters! */) {
        // For Debugging only!!!
//        ter = new TERenderer();
//        ter.initialize(width, height);

        rand = new Random(seed);
        map = new WorldMap(width, height);
        this.width = width;
        this.height = height;
        this.avgRoomWidth = avgRoomWidth;
        this.avgRoomHeight = avgRoomHeight;
        rooms = new ArrayList<>();
        currentRoomIndex = 0;
        generateRandomMap(numRooms);
    }

    // Getter:
    /**
    * Get a safe copy of rooms, with no null element inside
    * */

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Setter:

    // Private Helper Method:
    private void generateRandomMap(int numRooms) {
        //Generating map with respect to params
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map.setTile(x, y, new Tile(Tileset.NOTHING));
            }
        }
        Position pos;
        int randWidth, randHeight;
        int lowerBound = Math.min(avgRoomHeight, avgRoomWidth), upperBound = Math.max(avgRoomHeight, avgRoomWidth);

        createRoomLoop:
        for (int i = 0; i < numRooms; i++) {
            pos = (i == 0)? getRandomPos(rand) : getRandomRoomCenterByDistance(rand);
            randWidth = (int) RandomUtils.gaussian(rand, avgRoomWidth, 1.5);
            randHeight = (int) RandomUtils.gaussian(rand, avgRoomHeight, 1.5);
            pos = Area.centerToTopLeftPos(pos, randWidth, randHeight, this);

            for (int room = 0; room < map.getNumRooms(); room++) {
                if (map.getRoom(room).isAdjacentOrOverlap(new Room(pos, randWidth, randHeight))) {
                    i--;
                    continue createRoomLoop;
                }
            }

            map.createRoom(pos, randWidth, randHeight);

//            ter.renderFrame(this.map); //JUST FOR DEBUGGING!!!
        }
    }

    private Position getRandomPos(Random rand) {
        int x = RandomUtils.uniform(rand, 0, width);
        int y = RandomUtils.uniform(rand, 0, height);
        return new Position(x, y);
    } // Worked!

    private Position getRandomRoomCenterByDistance(Random rand) {
        int numCandidates = 300; // use here for future changes
        int totalDist = 0;
        Position[] candidates = new Position[numCandidates];
        Position currentPos; int currentTotalDist;
        Integer[] candidatesTotalDists = new Integer[numCandidates];
        for (int i = 0; i < numCandidates; i++) {
            currentPos = getRandomPos(rand);
            candidates[i] = currentPos;
            currentTotalDist = currentPos.calculatePositionScore(map.getRoomsLst());
            candidatesTotalDists[i] = (i == 0)? (currentTotalDist) : (currentTotalDist + candidatesTotalDists[i-1]);
            totalDist += currentTotalDist;
        }
        int roulette = RandomUtils.uniform(rand, 1, totalDist + 1);
        for (int i = 0; i < numCandidates; i++) {
            if (roulette <= candidatesTotalDists[i]) {
                return candidates[i];
            }
        }
        return candidates[numCandidates - 1];
    } // Worked!

    private boolean validPos(int xPos, int yPos) {
        // Need to bring to Position
        return xPos >= 0 && yPos >= 0 && xPos < width && yPos < height;
    } // Worked!

    // Public Method:

}
