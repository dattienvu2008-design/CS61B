package worldassets;

import tileengine.TETileRenderer;

public class Tile {
    private TETileRenderer tileType;
    private Area area;

    public Tile(TETileRenderer tileType) {
        this.tileType = tileType;
    }

    public TETileRenderer getTileType() {
        return tileType;
    }
}
