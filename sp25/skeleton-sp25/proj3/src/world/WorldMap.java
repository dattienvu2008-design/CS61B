package world;

import datastructure.WeightedQuickUnion;
import tileengine.Tileset;
import worldassets.Area;
import worldassets.Position;
import worldassets.Room;
import worldassets.Tile;

import java.util.*;

public class WorldMap {
    private final Tile[][] map;
    public final int width, height;
    private int currentRoomIndex;
    private final ArrayList<Room> rooms;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        rooms = new ArrayList<>();
    }

    // Getter:
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Get Tile at position (x,y). Throw {@link IndexOutOfBoundsException} if (x,y) invalid
     * @param x Tile's xPos
     * @param y Tile's yPos
     * @return Tile at (x,y)
     */
    public Tile getTile(int x, int y) {
        if (!Position.validPos(x, y, width, height)) {
            throw new IndexOutOfBoundsException();
        }
        return map[x][y];
    }

    /**
     * Get Tile at position (x,y)
     * Throw {@link IndexOutOfBoundsException} if pos out of bounds.
     * Throw {@link IllegalArgumentException} if pos.getX() or pos.getY() is noninteger
     * @param pos Tile's pos
     * @return Tile at (x,y)
     */
    public Tile getTile(Position pos) {
        if (!Position.validPos(pos, width, height)) {
            throw new IndexOutOfBoundsException();
        } else if (!pos.isPosInteger()) {
            throw new IllegalArgumentException();
        }
        return map[(int) pos.getX()][(int) pos.getY()];
    }

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

    public int getNumRooms() {
        return rooms.size();
    }

    // Setter:

    /**
     * Set tile at Position pos
     * @param pos Position to set tile
     * @param tile Tile that will be set at pos
     */
    void setTile(Position pos, Tile tile) {
        if (!pos.isPosInteger()) {
            throw new IllegalArgumentException();
        }
        map[(int) pos.getX()][(int) pos.getY()] = tile;
    }

    void setTile(int x, int y, Tile tile) {
        map[x][y] = tile;
    }

    public void setRoom(int index, Room room) {
        rooms.set(index, room);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Private Helper:
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

    public void createHallway(Room roomA, Room roomB) {
        Position centerA = roomA.getCenter();
        Position centerB = roomB.getCenter();
        int currentX = (int) centerA.getX();
        int currentY = (int) centerA.getY();
        Tile currentTile;
        if (currentY < centerB.getY()) {
            while (currentY <= centerB.getY()) {
                currentTile = getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR);
                    createHallwayAddWallHelper(currentTile);
                }
                currentY++;
            }
        } else {
            while (currentY >= centerB.getY()) {
                currentTile = getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR);
                    createHallwayAddWallHelper(currentTile);
                }
                currentY--;
            }
        }
        if (currentX < centerB.getX()) {
            while (currentX <= centerB.getX()) {
                currentTile = getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR);
                    createHallwayAddWallHelper(currentTile);
                }
                currentX++;
            }
        } else {
            while (currentX >= centerB.getX()) {
                currentTile = getTile(currentX, currentY);
                if (currentTile.getArea() == Area.VOID || currentTile.getTileType() == Tileset.WALL) {
                    currentTile.setTileType(Tileset.FLOOR);
                    createHallwayAddWallHelper(currentTile);
                }
                currentX--;
            }
        }
    }

    private void createHallwayAddWallHelper(Tile tile) {
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
    // Public Interface:
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

        Room room = new Room(pos, roomWidth, roomHeight, this);
        addRoom(room); //Need to modify!!

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
                    setTile(x, y, new Tile(Tileset.WALL, room, new Position(x, y), this));
                } else {
                    setTile(x, y, new Tile(Tileset.FLOOR, room, new Position(x, y), this));
                }
            }
        }
        currentRoomIndex++;
    }

    public ArrayList<Integer[]> findMST() {
        // Need more optimization! (For now just a runable method - still messy)
        int numRooms = getNumRooms(), index = 0;
        int currentDist;
//        int numDists = (numRooms*(numRooms - 1)) / 2;
        ArrayList<Integer[]> dists = new ArrayList<>();
        for (int i = 0; i < numRooms - 1; i++) {
            for (int j = i + 1; j < numRooms; j++) {
                currentDist = getRoom(i).distantFromCenterTo(getRoom(j));
                dists.add(new Integer[]{i, j, currentDist});
            }
        }
        WeightedQuickUnion roomsSet = new WeightedQuickUnion(numRooms);
        int room1, room2;
        PriorityQueue<Integer[]> distsQueue = new PriorityQueue<>(dists.size(), new FindMSTDistCompareHelper());
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

    public static class FindMSTDistCompareHelper implements Comparator<Integer[]> {
        @Override
        public int compare(Integer[] o1, Integer[] o2) {
            return o1[2] - o2[2];
        }
    }
}
