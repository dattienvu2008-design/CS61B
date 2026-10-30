package core;

import tileengine.TERenderer;
import world.World;

public class Main {
    public static final int WIDTH = 80, HEIGHT = 45;
    public static final int AVG_ROOM_WIDTH = 15, AVG_ROOM_HEIGHT = 12;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World world = new World(5, WIDTH, HEIGHT, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, 2);
        ter.renderFrame(world.map);
    }
}
