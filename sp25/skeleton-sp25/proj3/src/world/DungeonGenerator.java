package world;

import datastructure.WeightedQuickUnion;
import renderengine.Tileset;
import utils.RandomUtils;
import worldassets.Position;
import worldassets.tile.Tile;
import worldassets.area.Area;
import worldassets.area.Room;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class DungeonGenerator{
    // Private Helper:
    public static WorldChunk generateRandomChunk(Position chunkPos, int gameSeed,
                                                 int width, int height, int avgRoomWidth, int avgRoomHeight) {
        WorldChunk chunk = new WorldChunk(chunkPos, width, height, gameSeed);
        int numRooms = 9 + chunk.rand.nextInt(14);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                chunk.setTile(x, y, new Tile(Tileset.NOTHING, Area.VOID, new Position(x, y), chunk));
            }
        }

        Position pos;
        int randWidth, randHeight;
        int numTry = 0;
        createRoomLoop:
        for (int currentRoomIndex = 0; currentRoomIndex < numRooms; currentRoomIndex++) {
            if (numTry > 1000) {
                break createRoomLoop;
            }
            pos = (currentRoomIndex == 0)? getRandomPos(chunk.rand, width, height) : getRandomRoomCenterByDistance(chunk);
            randWidth = (int) RandomUtils.gaussian(chunk.rand, avgRoomWidth, 1.7);
            randHeight = (int) RandomUtils.gaussian(chunk.rand, avgRoomHeight, 1.7);
            pos = Area.centerToTopLeftPos(pos, randWidth, randHeight, chunk);

            for (int roomIndex = 0; roomIndex < chunk.getNumRooms(); roomIndex++) {
                if (chunk.getRoom(roomIndex).isAdjacentOrOverlap(new Room(pos, randWidth, randHeight, chunk, chunk.rand))) {
                    currentRoomIndex--;
                    numTry++;
                    continue createRoomLoop;
                }
            }
            createRoom(randWidth, randHeight, pos, chunk);
            numTry = 0;
        }

        if (numRooms > 1) {
            for (Integer[] roomsLst : DungeonGenerator.findMST(chunk)) {
                createHallway(chunk.getRoom(roomsLst[0]), chunk.getRoom(roomsLst[1]), chunk);
            }
        }

        int numWarps = 1 + chunk.rand.nextInt(3);
        for (int i = 0; i < numWarps; i++) {
            for (Room room : chunk.getRoomsLst()) {

            }
        }

        return chunk;
    }

    public static void generateRandomMapForChunk(WorldChunk chunk, int avgRoomWidth, int avgRoomHeight) {
        int numRooms = 9 + chunk.rand.nextInt(14);

        for (int x = 0; x < chunk.width; x++) {
            for (int y = 0; y < chunk.height; y++) {
                chunk.setTile(x, y, new Tile(Tileset.NOTHING, Area.VOID, new Position(x, y), chunk));
            }
        }

        Position pos;
        int randWidth, randHeight;
        int numTry = 0;
        createRoomLoop:
        for (int currentRoomIndex = 0; currentRoomIndex < numRooms; currentRoomIndex++) {
            if (numTry > 1000) {
                break createRoomLoop;
            }
            pos = (currentRoomIndex == 0)? getRandomPos(chunk.rand, chunk.width, chunk.height) : getRandomRoomCenterByDistance(chunk);
            randWidth = (int) RandomUtils.gaussian(chunk.rand, avgRoomWidth, 1.7);
            randHeight = (int) RandomUtils.gaussian(chunk.rand, avgRoomHeight, 1.7);
            pos = Area.centerToTopLeftPos(pos, randWidth, randHeight, chunk);

            for (int roomIndex = 0; roomIndex < chunk.getNumRooms(); roomIndex++) {
                if (chunk.getRoom(roomIndex).isAdjacentOrOverlap(new Room(pos, randWidth, randHeight, chunk, chunk.rand))) {
                    currentRoomIndex--;
                    numTry++;
                    continue createRoomLoop;
                }
            }
            createRoom(randWidth, randHeight, pos, chunk);
            numTry = 0;
        }

        if (numRooms > 1) {
            for (Integer[] roomsLst : DungeonGenerator.findMST(chunk)) {
                createHallway(chunk.getRoom(roomsLst[0]), chunk.getRoom(roomsLst[1]), chunk);
            }
        }
    }

    private static ArrayList<Integer[]> findMST(WorldChunk map) {
        // Need more optimization! (For now just a runable method - still messy)
        int numRooms = map.getNumRooms(), index = 0;
        int currentDist;
//        int numDists = (numRooms*(numRooms - 1)) / 2;
        ArrayList<Integer[]> dists = new ArrayList<>();
        for (int i = 0; i < numRooms - 1; i++) {
            for (int j = i + 1; j < numRooms; j++) {
                currentDist = map.getRoom(i).distantFromCenterTo(map.getRoom(j));
                dists.add(new Integer[]{i, j, currentDist});
            }
        }
        WeightedQuickUnion roomsSet = new WeightedQuickUnion(numRooms);
        int room1, room2;
        PriorityQueue<Integer[]> distsQueue = new PriorityQueue<>(dists.size(), Comparator.comparingInt(a -> a[2]));
        //dist.size() = 0 -> invalid
        distsQueue.addAll(dists);
        ArrayList<Integer[]> minimumSTree = new ArrayList<>();
        while (true) {
            Integer[] currentPath = distsQueue.poll();
            if (currentPath == null) {
                break;
            } else {
                room1 = currentPath[0];
                room2 = currentPath[1];
                if (roomsSet.isConnected(room1, room2)) {
                    continue;
                } else {
                    roomsSet.connect(room1, room2);
                    minimumSTree.add(currentPath);
                }
            }
        }
        return minimumSTree;
    }

    private static Position getRandomPos(Random rand, int width, int height) {
        int x = RandomUtils.uniform(rand, 0, width);
        int y = RandomUtils.uniform(rand, 0, height);
        return new Position(x, y);
    } // Worked!

    private static Position getRandomRoomCenterByDistance(WorldChunk chunk) {
        int numCandidates = 300; // use here for future changes
        int totalDist = 0;
        Position[] candidates = new Position[numCandidates];
        Position currentPos; int currentDist;
        Integer[] candidatesTotalDists = new Integer[numCandidates];
        for (int i = 0; i < numCandidates; i++) {
            currentPos = getRandomPos(chunk.rand, chunk.width, chunk.height);
            candidates[i] = currentPos;
            currentDist = currentPos.calculatePositionScore(chunk.getRoomsLst());
            candidatesTotalDists[i] = (i == 0)? (currentDist) : (currentDist + candidatesTotalDists[i-1]);
            totalDist += currentDist;
        }
        int roulette = RandomUtils.uniform(chunk.rand, 1, totalDist + 1);
        for (int i = 0; i < numCandidates; i++) {
            if (roulette <= candidatesTotalDists[i]) {
                return candidates[i];
            }
        }
        return candidates[numCandidates - 1];
    } // Worked!

    private static int[] findValidRectangleRoom(int width, int height, Position pos, WorldChunk chunk) {
        // Assume that validPos(xPos, yPos) == true
        // Find "valid" room (room that its wall does not go out of the map)
        // May add feature about canceling overlap between 2 room in the future
        int xPos = pos.getX(), yPos = (int) pos.getY();
        assert Position.validPos(xPos, yPos, chunk);

        int[] validSize = {width, height}; //validSize[0] and validSize[1] are upper validSize of x and y respectively
        for (int x = xPos; x < xPos + width; x++) {
            if (!Position.validPos(x, yPos, chunk)) {
                validSize[0] = x - xPos; //Math.abs
                break;
            }
        }
        for (int y = yPos; y > yPos - height; y--) {
            if (!Position.validPos(xPos, y, chunk)) {
                validSize[1] = yPos - y;
                break;
            }
        }
        return validSize;
    } // Worked!

    private static void createHallway(Room roomA, Room roomB, WorldChunk chunk) {
        Position centerA = roomA.getCenter();
        Position centerB = roomB.getCenter();
        int currentX = (int) centerA.getX();
        int currentY = (int) centerA.getY();
        Tile currentTile;
        if (currentY < centerB.getY()) {
            while (currentY <= centerB.getY()) {
                currentTile = chunk.getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR1);
                    createHallwayAddWallHelper(currentTile);
                }
                currentY++;
            }
        } else {
            while (currentY >= centerB.getY()) {
                currentTile = chunk.getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR1);
                    createHallwayAddWallHelper(currentTile);
                }
                currentY--;
            }
        }
        if (currentX < centerB.getX()) {
            while (currentX <= centerB.getX()) {
                currentTile = chunk.getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR1);
                    createHallwayAddWallHelper(currentTile);
                }
                currentX++;
            }
        } else {
            while (currentX >= centerB.getX()) {
                currentTile = chunk.getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR1);
                    createHallwayAddWallHelper(currentTile);
                }
                currentX--;
            }
        }
    }

    private static void createHallwayAddWallHelper(Tile tile) {
        Tile[] adjacentTile = new Tile[8];
        adjacentTile[0] = tile.getBottomTile();
        adjacentTile[1] = tile.getTopTile();
        adjacentTile[2] = tile.getLeftTile();
        adjacentTile[3] = tile.getRightTile();
        adjacentTile[4] = tile.getTopLeftTile();
        adjacentTile[5] = tile.getTopRightTile();
        adjacentTile[6] = tile.getBottomLeftTile();
        adjacentTile[7] = tile.getBottomRightTile();
        for (Tile currentTile : adjacentTile) {
            if (currentTile != null && currentTile.getTileType() == Tileset.NOTHING) {
                currentTile.setTileType(Tileset.WALL);
            }
        }
    }

    /*
     * Create room of size roomWidth*roomHeight. (xPos;yPos) is the room's top left corner
     * */
    private static void createRoom(int roomWidth, int roomHeight, Position pos, WorldChunk chunk) {
        int xPos = (int) pos.getX(), yPos = (int) pos.getY();
        {
            int[] validSize = findValidRectangleRoom(roomWidth, roomHeight, pos, chunk);
            roomWidth = validSize[0];
            roomHeight = validSize[1];
        }

        Room room = new Room(pos, roomWidth, roomHeight, chunk, chunk.rand);
        chunk.addRoom(room);

        int randFloorTile;
        Random randTile = new Random(0);
        for (int x = xPos; x < xPos + roomWidth; x++) {
            if (!Position.validPos(pos, chunk)) {
                continue;
            }
            for (int y = yPos; y > yPos - roomHeight; y--) {
                if (!Position.validPos(pos, chunk)) {
                    continue;
                }
                // Create room here?
                if (x == xPos || y == yPos || x == xPos + roomWidth - 1 || y == yPos - roomHeight + 1) {
                    chunk.setTile(x, y, new Tile(Tileset.WALL, room, new Position(x, y), chunk));
                } else {
                    randFloorTile = randTile.nextInt(2);
                    switch (randFloorTile) {
                        case 0:
                            chunk.setTile(x, y, new Tile(Tileset.FLOOR1, room, new Position(x, y), chunk));
                            break;
                        case 1:
                            chunk.setTile(x, y, new Tile(Tileset.FLOOR2, room, new Position(x, y), chunk));
                            break;
                    }
                }
            }
        }
    }

    private static void createHallwayToOtherChunk() {

    }
}
