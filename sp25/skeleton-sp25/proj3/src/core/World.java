package core;

import tileengine.TETile;
import tileengine.Tileset;

/*
* Use for constructing map and storing information about state of that map (Specs may change in the future)
* */
public class World {
    public Tile[][] map;

    static void main(String[] args) {
        World a = new World(3, 10, 10);
        a.createRoom(5, 5, 3, 3);
    }

    public World(int numRooms, int width, int height /* And some more parameters! */) {
        map = new Tile[width][height];
        generateMap();
    }

    private void generateMap(/* Params */) {
        //Generating map with respect to params
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                map[x][y] = new Tile(Tileset.NOTHING, Area.NOTHING);
            }
        }
    }

    /*
    * Create room of size width*height. (xPos;yPos) is the room's top left corner
    * */
    public void createRoom(int xPos, int yPos, int width, int height) {
        {
            int[] validSize = findValidRectangleRoom(xPos, yPos, width, height);
            width = validSize[0];
            height = validSize[1];
        }
        for (int x = xPos; x < xPos + width; x++) {
            if (!validPos(xPos, yPos)) {
                // Need more modified to create wall at the border
                continue;
            }
            for (int y = yPos; y > yPos - height; y--) {
                if (!validPos(xPos, yPos)) {
                    continue;
                }
                // Create room here?
                if (x == xPos || y == yPos || x == xPos + width - 1 || y == yPos - height + 1) {
                    map[x][y].setTileType(Tileset.WALL);
                } else {
                    map[x][y].setTileType(Tileset.FLOOR);
                }
            }
        }
    }

    private int[] findValidRectangleRoom(int xPos, int yPos, int width, int height) {
        // Assume that validPos(xPos, yPos) == true
        // Find "valid" room (room that its wall does not go out of the map)
        // May add feature about canceling overlap between 2 room in the future
        assert validPos(xPos, yPos);

        int[] validSize = {width, height}; //validSize[0] and validSize[1] are upper validSize of x and y respectively
        for (int x = xPos; x < xPos + width; x++) {
            if (!validPos(x, yPos)) {
                validSize[0] = x - xPos;
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
    }

    private boolean validPos(int xPos, int yPos) {
        return xPos >= 0 && yPos >= 0 && xPos < map.length && yPos < map[0].length;
    }
}
