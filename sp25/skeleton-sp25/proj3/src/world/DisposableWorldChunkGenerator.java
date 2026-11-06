package world;

import worldassets.Position;

public class DisposableWorldChunkGenerator {
    int gameSeed, width, height, avgRoomWidth, avgRoomHeight;
    Position position;
    World world;

    public DisposableWorldChunkGenerator(int gameSeed, Position position,
                                         int width, int height, int avgRoomWidth, int avgRoomHeight, World world) {
        this.gameSeed = gameSeed;
        this.position = position;
        this.width = width;
        this.height = height;
        this.avgRoomHeight = avgRoomHeight;
        this.avgRoomWidth = avgRoomWidth;
        this.world = world;
    }

    public WorldChunk create() {
        WorldChunk addedChunk = DungeonGenerator.generateRandomChunk(position, gameSeed, width, height, avgRoomWidth, avgRoomHeight);
        world.addChunk(addedChunk.chunkPos, addedChunk);
        return addedChunk;
    }
}
