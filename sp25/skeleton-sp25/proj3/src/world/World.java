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
    // private /*final*/ HashMap<AreaType, Area[]> areas;
    private final int width, height, avgRoomWidth, avgRoomHeight;
    private final Random rand;
    // For Debugging only!!!
    // private TERenderer ter;

    static void main(String[] args) {
        final int WIDTH = 80, HEIGHT = 45;
        final int AVG_ROOM_WIDTH = 16, AVG_ROOM_HEIGHT = 13;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World world1;
        for (int i = 234; i < 23407; i = i + 2) {
            world1 = new World(6, WIDTH, HEIGHT, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, i);
            ter.renderFrame(world1.map);
            StdDraw.pause(2000);
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
                map.setTile(x, y, new Tile(Tileset.NOTHING, Area.VOID, new Position(x, y), map));
            }
        }
        Position pos;
        int randWidth, randHeight;
        int lowerBound = Math.min(avgRoomHeight, avgRoomWidth), upperBound = Math.max(avgRoomHeight, avgRoomWidth);
        int numTry = 0;

        createRoomLoop:
        for (int i = 0; i < numRooms; i++) {
            if (numTry > 1000) {
                break createRoomLoop;
            }
            pos = (i == 0)? getRandomPos(rand) : getRandomRoomCenterByDistance(rand);
            randWidth = (int) RandomUtils.gaussian(rand, avgRoomWidth, 1.7);
            randHeight = (int) RandomUtils.gaussian(rand, avgRoomHeight, 1.7);
            pos = Area.centerToTopLeftPos(pos, randWidth, randHeight, this);

            for (int roomIndex = 0; roomIndex < map.getNumRooms(); roomIndex++) {
                if (map.getRoom(roomIndex).isAdjacentOrOverlap(new Room(pos, randWidth, randHeight, map))) {
                    i--;
                    numTry++;
                    continue createRoomLoop;
                }
            }
            map.createRoom(pos, randWidth, randHeight);
            numTry = 0;
        }

        if (numRooms > 1) {
            for (Integer[] roomsLst : map.findMST()) {
                map.createHallway(map.getRoom(roomsLst[0]), map.getRoom(roomsLst[1]));
            }
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
