package world;

import worldassets.Position;
import worldassets.area.Room;
import worldassets.tile.Tile;

import java.io.Serializable;
import java.util.*;

public class WorldChunk implements Serializable {
    private final Tile[][] map;
    public final int width, height;
    public final Position chunkPos;
    final Random rand;

    private int currentRoomIndex;
    private final ArrayList<Room> rooms;

    public WorldChunk(Position chunkPos, int width, int height, int gameSeed) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        rooms = new ArrayList<>();
        this.rand = new Random(gameSeed + chunkPos.hashCode());
        this.chunkPos = chunkPos;
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

    void setRoom(int index, Room room) {
        rooms.set(index, room);
    }

    void addRoom(Room room) {
        rooms.add(room);
    }

    public Position getGridPosition(Position position) {
        return null;
    }
}
