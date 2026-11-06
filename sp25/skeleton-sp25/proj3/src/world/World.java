package world;

import Entity.Entity;
import Entity.Character.Player;
import worldassets.*;

import java.io.Serializable;
import java.util.*;

/*
* Use for constructing map and storing information about state of that map (Specs may change in the future)
* */
public class World implements Serializable {
    // Attributes:
    public final HashMap<Position, WorldChunk> chunks = new HashMap<>();
    public void addChunk(Position pos, WorldChunk chunk) {
        chunks.put(pos, chunk);
    }
    public WorldChunk getChunk(Position pos) {
        return chunks.get(pos);
    }
    public WorldChunk getChunk(int x, int y) {
        return chunks.get(new Position(x, y));
    }
//    public Position getPosForNewChunk(WorldChunk chunk) {
//        List<Integer> allPossibleX = List.of(-1, 0, 1);
//        Collections.shuffle(allPossibleX, chunk.rand);
//        List<Integer> allPossibleY = List.of(-1, 0, 1);
//        int x = chunk.chunkPos.getX(), y = chunk.chunkPos.getY();
//        for (Integer X : allPossibleX) {
//            for (Integer Y : allPossibleY) {
//                if (!isPosHaveChunk(new Position(x + X * WIDTH, y + Y * HEIGHT))) {
//                    return new Position(x + X * WIDTH, y + Y * HEIGHT);
//                }
//            }
//        }
//        return null;
//    }
//    private boolean isPosHaveChunk(Position position) {
//        return getChunk(position) != null;
//    }


    public final int WIDTH, HEIGHT, avgRoomWidth, avgRoomHeight;
    private final Random rand;

    private final ArrayList<Player> players = new ArrayList<>();
    public void addPlayer(Player player) {
        players.add(player);
    }
    public Player getPlayer(int index) {
        return players.get(index);
    }
    public int getNumPlayers() {
        return players.size();
    }

    private final HashSet<Entity> entities = new HashSet<>();
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public World(int width, int height, int avgRoomWidth, int avgRoomHeight, int seed) {
        rand = new Random(seed);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.avgRoomWidth = avgRoomWidth;
        this.avgRoomHeight = avgRoomHeight;
        WorldChunk initChunk = DungeonGenerator.generateRandomChunk(new Position(0, 0), seed,
                width, height, avgRoomWidth, avgRoomHeight);
        addChunk(initChunk.chunkPos, initChunk);
    }

    // Getter:
    /**
    * Get a safe copy of rooms, with no null element inside
    * */
}
