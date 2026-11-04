package worldassets;

import Entity.Entity;
import tileengine.TEEntityTileRenderer;
import tileengine.TETileRenderer;
import world.WorldMap;

import java.io.Serializable;

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

    private final WorldMap map;

    private Entity entity = null;
    public Entity getEntity() {
        return entity;
    }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    public void removeEntity() {
        entity = null;
    }
    public boolean hasEntity() {
        return entity != null;
    }
    public void drawEntity() {
        ((TEEntityTileRenderer) entity.getSkin()).draw(getPos().getX(), getPos().getY(), entity.getHealthPercentage());
    }

    public Tile(TETileRenderer tileType, Area area, Position pos, WorldMap map) {
        this.tileType = tileType;
        this.area = area;
        tilePos = pos;
        this.map = map;
    }

    public Tile(TETileRenderer tileType, Position pos, WorldMap map) {
        this.tileType = tileType;
        this.area = area;
        tilePos = pos;
        this.map = map;
    }

    public Tile getLeftTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() - 1, thisPos.getY());
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getRightTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() + 1, thisPos.getY());
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getTopTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX(), thisPos.getY() + 1);
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getBottomTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX(), thisPos.getY() - 1);
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getBottomLeftTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() - 1, thisPos.getY() - 1);
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getBottomRightTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() + 1, thisPos.getY() - 1);
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getTopLeftTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() - 1, thisPos.getY() + 1);
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }
    public Tile getTopRightTile() {
        Position thisPos = getPos();
        Position newPos = new Position((int) thisPos.getX() + 1, thisPos.getY() + 1);
        if (Position.validPos(newPos, map)) {
            return map.getTile(newPos);
        }
        return null;
    }

}
