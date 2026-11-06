package worldassets.tile;

import Entity.Entity;
import renderengine.TETileRenderer;
import world.DisposableWorldChunkGenerator;
import world.World;
import world.WorldChunk;
import worldassets.Direction;
import worldassets.Position;
import worldassets.area.Area;

import java.io.Serializable;

/**
 * Teleport Entity to the corresponding Warp. Created one if the corresponding Warp does not exist.
 */
public class WarpTile extends Tile implements Serializable {
    // Implement later!!!
    // May add something like tile status (trapped, poisonous, breakable, ...), some method to change tile status/terrain/something else
    // Not overengineering, just an insurrance for future ambition features:>
    private WarpTile destWarp;
    public Tile entitiesReceiveTile;
    private DisposableWorldChunkGenerator chunkGenerator;

    public WarpTile(TETileRenderer tileType, Area area, Position pos, WorldChunk map,
                    Tile entitiesReceiveTile, DisposableWorldChunkGenerator worldChunkGenerator) {
        super(tileType, area, pos, map);
        this.entitiesReceiveTile = entitiesReceiveTile;
        chunkGenerator = worldChunkGenerator;
    }

    @Override
    public void moveEntityToThis(Entity entity) {
        if (destWarp == null) {
            // TODO:
//            destWarp = chunkGenerator.create().getWarp; //getWarp, or smt else, must be Warp type
            entity.teleport(destWarp.entitiesReceiveTile);
        } else {
            entity.teleport(destWarp.entitiesReceiveTile);
        }
    }
}
