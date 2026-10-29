package core;

import tileengine.TERenderer;

public class Main {
    public static final int WIDTH = 25, HEIGHT = 25;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World world = new World(1, WIDTH, HEIGHT);
        world.createRoom(5, 20, 10, 15);
        ter.renderFrame(world.map);
    }
}
