package renderengine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETileRenderer WALL = new TETileRenderer('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "gameassets/Tiles/tile_0014.png", 1, true);
    public static final TETileRenderer FLOOR1 = new TETileRenderer('·', new Color(128, 192, 128), Color.black, "floor", "gameassets/Tiles/tile_0048.png", 2);
    public static final TETileRenderer FLOOR2 = new TETileRenderer('·', new Color(128, 192, 128), Color.black, "floor", "gameassets/Tiles/tile_0049.png", 2);
    public static final TETileRenderer NOTHING = new TETileRenderer(' ', Color.black, new Color(17, 27, 71, 255), "nothing", 3);
    public static final TETileRenderer GRASS = new TETileRenderer('"', Color.green, Color.black, "grass", 4);
    public static final TETileRenderer WATER = new TETileRenderer('≈', Color.blue, Color.black, "water", 5);
    public static final TETileRenderer FLOWER = new TETileRenderer('❀', Color.magenta, Color.pink, "flower", 6);
    public static final TETileRenderer LOCKED_DOOR = new TETileRenderer('█', Color.orange, Color.black,
            "locked door", 7);
    public static final TETileRenderer UNLOCKED_DOOR = new TETileRenderer('▢', Color.orange, Color.black,
            "unlocked door", 8);
    public static final TETileRenderer SAND = new TETileRenderer('▒', Color.yellow, Color.black, "sand", 9);
    public static final TETileRenderer MOUNTAIN = new TETileRenderer('▲', Color.gray, Color.black, "mountain", 10);
    public static final TETileRenderer TREE = new TETileRenderer('♠', Color.green, Color.black, "tree", 11);

    public static final TETileRenderer CELL = new TETileRenderer('█', Color.white, Color.black, "cell", 12);
    public static final TETileRenderer WARP = new TETileRenderer('●', Color.blue, Color.orange, "warp",
            "gameassets/Personal/portal1.png", 13, false);

    // Entity Tile:
    public static final TEEntityTileRenderer PLAYER = new TEEntityTileRenderer(
            '@', Color.white, Color.black, "you", "gameassets/Tiles/tile_0098.png", 14, true);
}


