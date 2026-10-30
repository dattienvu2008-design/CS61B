package worldassets;

import tileengine.TETileRenderer;

import java.awt.*;

public class InteractiveTile extends Tile {
    // Implement later!!!
    // May add something like tile status (trapped, poisonous, breakable, ...), some method to change tile status/terrain/something else
    // Not overengineering, just an insurrance for future ambition features:>
    private TETileRenderer tileType;

    public InteractiveTile(TETileRenderer tileType) {
        super(tileType);
    }
}
