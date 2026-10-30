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
    /**
     * World map.
     */
    public final Map map;
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
        map = new Map(width, height);
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
    public Room[] getRoomsLst() {
        Room[] currentRooms = new Room[currentRoomIndex];
        for (int i = 0; i < currentRoomIndex; i++) {
            currentRooms[i] = getRoom(i);
        }
        return currentRooms;
    }

    public Room getRoom(int index) {
        return rooms.get(index);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumRooms() {
        return rooms.size();
    }

    // Setter:
    public void setRoom(int index, Room room) {
        rooms.set(index, room);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Private Helper Method:
    private void generateRandomMap(int numRooms) {
        //Generating map with respect to params
        InteractiveTile tile = new InteractiveTile(Tileset.NOTHING);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map.setTile(x, y, tile);
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

            for (int room = 0; room < getNumRooms(); room++) {
                if (getRoom(room).isOverlapped(new Room(pos, randWidth, randHeight))) {
                    i--;
                    continue createRoomLoop;
                }
            }

            createRoom(pos, randWidth, randHeight);

//            ter.renderFrame(this.map); //JUST FOR DEBUGGING!!!
        }
    }

    private Position getRandomPos(Random rand) {
        int x = RandomUtils.uniform(rand, 0, width);
        int y = RandomUtils.uniform(rand, 0, height);
        return new Position(x, y);
    }

    private Position getRandomRoomCenterByDistance(Random rand) {
        int numCandidates = 300; // use here for future changes
        int totalDist = 0;
        Position[] candidates = new Position[numCandidates];
        Position currentPos; int currentTotalDist;
        Integer[] candidatesTotalDists = new Integer[numCandidates];
        for (int i = 0; i < numCandidates; i++) {
            currentPos = getRandomPos(rand);
            candidates[i] = currentPos;
            currentTotalDist = currentPos.calculatePositionScore(getRoomsLst());
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
    }

    private int[] findValidRectangleRoom(Position pos, int width, int height) {
        // Assume that validPos(xPos, yPos) == true
        // Find "valid" room (room that its wall does not go out of the map)
        // May add feature about canceling overlap between 2 room in the future
        int xPos = (int) pos.getX(), yPos = (int) pos.getY();
        assert validPos(xPos, yPos);

        int[] validSize = {width, height}; //validSize[0] and validSize[1] are upper validSize of x and y respectively
        for (int x = xPos; x < xPos + width; x++) {
            if (!validPos(x, yPos)) {
                validSize[0] = x - xPos; //Math.abs
                break;
            }
        }
        for (int y = yPos; y > yPos - height; y--) {
            if (!validPos(xPos, y)) {
                validSize[1] = yPos - y;
                break;
            }
        }
        return validSize;
    } // Worked!

    private boolean validPos(int xPos, int yPos) {
        // Need to bring to Position
        return xPos >= 0 && yPos >= 0 && xPos < width && yPos < height;
    } // Worked!

    // Public Method:
    /*
    * Create room of size roomWidth*roomHeight. (xPos;yPos) is the room's top left corner
    * */
    public void createRoom(Position pos, int roomWidth, int roomHeight) {
        int xPos = (int) pos.getX(), yPos = (int) pos.getY();
        {
            int[] validSize = findValidRectangleRoom(pos, roomWidth, roomHeight);
            roomWidth = validSize[0];
            roomHeight = validSize[1];
        }

        addRoom(new Room(pos, roomWidth, roomHeight)); //Need to modify!!

        for (int x = xPos; x < xPos + roomWidth; x++) {
            if (!Position.validPos(pos, this)) {
                continue;
            }
            for (int y = yPos; y > yPos - roomHeight; y--) {
                if (!Position.validPos(pos, this)) {
                    continue;
                }
                // Create room here?
                if (x == xPos || y == yPos || x == xPos + roomWidth - 1 || y == yPos - roomHeight + 1) {
                    map.setTile(x, y, new InteractiveTile(Tileset.WALL));
                } else {
                    map.setTile(x, y, new InteractiveTile(Tileset.WALL));
                }
            }
        }
        currentRoomIndex++;
    }
}
