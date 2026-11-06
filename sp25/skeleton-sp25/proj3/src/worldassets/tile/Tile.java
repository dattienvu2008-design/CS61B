package worldassets.tile;

import Entity.Entity;
import renderengine.TETileRenderer;
import world.WorldChunk;
import worldassets.Position;
import worldassets.area.Area;

import java.io.Serializable;
import java.util.Random;

public class Tile implements Serializable {
    private TETileRenderer tileType;
    public TETileRenderer getTileType() {
        return tileType;
    }
    public void setTileType(TETileRenderer tileType) {
        this.tileType = tileType;
    }

    private Area area = Area.VOID;
    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }

    private final Position tilePos;
    public Position getPos() {
        return tilePos;
    }

    public final WorldChunk chunk;

    private Entity entity = null;
    public Entity getEntity() {
        return entity;
    }
    protected void setEntity(Entity entity) {
        this.entity = entity;
    }
    public void moveEntityToThis(Entity entity) {
        setEntity(entity);
    }
    public void removeEntity() {
        entity = null;
    }
    public boolean hasEntity() {
        return entity != null;
    }

    public Tile(TETileRenderer tileType, Area area, Position pos, WorldChunk chunk) {
        this.tileType = tileType;
        this.area = area;
        tilePos = pos;
        this.chunk = chunk;
    }

    public Tile(TETileRenderer tileType, Position pos, WorldChunk chunk) {
        this.tileType = tileType;
        this.area = null;
        tilePos = pos;
        this.chunk = chunk;
    }

    public boolean isTileWalkable() {
        return !this.getTileType().blockable && ((this.hasEntity() && !this.getEntity().getSkin().blockable) || !this.hasEntity());
    }

    public Tile getValidRandomAdjacentTile(Random rand) {
        Tile[] adjacentTile = new Tile[4];
        adjacentTile[0] = getLeftTile();
        adjacentTile[1] = getRightTile();
        adjacentTile[2] = getTopTile();
        adjacentTile[3] = getBottomTile();
        for (Tile tile : adjacentTile) {
            if (tile.isTileWalkable()) {
                return tile;
            }
        }
        return null;
    }
    public Tile getLeftTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() - 1, thisPos.getY());
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getRightTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() + 1, thisPos.getY());
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getTopTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX(), thisPos.getY() + 1);
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getBottomTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX(), thisPos.getY() - 1);
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getBottomLeftTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() - 1, thisPos.getY() - 1);
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getBottomRightTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() + 1, thisPos.getY() - 1);
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getTopLeftTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() - 1, thisPos.getY() + 1);
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }
    public Tile getTopRightTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() + 1, thisPos.getY() + 1);
        if (Position.validPos(newPos, chunk)) {
            return chunk.getTile(newPos);
        }
        return null;
    }

}
