package worldassets;

import tileengine.TETile;

public class InteractiveTile {
    private TETile tileType;
    // May add something like tile status (trapped, poisonous, breakable, ...), some method to change tile status/terrain/something else
    // Not overengineering, just an insurrance for future ambition features:>

    public InteractiveTile(TETile tileType) {
        this.tileType = tileType;
    }

    public TETile getTileType() {
        return tileType;
    }

    public void setTileType(TETile type) {
        tileType = type;
    }
}
