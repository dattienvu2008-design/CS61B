package core;

public class Main {
    public static final int WIDTH = 90, HEIGHT = 48;
    public static final int AVG_ROOM_WIDTH = 14, AVG_ROOM_HEIGHT = 11;
    public static final int STANDARD_HEALTH = 5;

    static void main(String[] args) {
        GameRunner gameRunner = new GameRunner(WIDTH, HEIGHT, AVG_ROOM_WIDTH, AVG_ROOM_HEIGHT, STANDARD_HEALTH);
        gameRunner.startGame();
    }
}
