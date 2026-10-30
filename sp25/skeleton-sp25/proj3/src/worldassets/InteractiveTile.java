package worldassets;

import tileengine.TETile;

import java.awt.*;

public class InteractiveTile extends TETile {
    // Implement later!!!
    public InteractiveTile(char character, Color textColor, Color backgroundColor, String description, String filepath, int id) {
        super(character, textColor, backgroundColor, description, filepath, id);
    }

    public InteractiveTile(char character, Color textColor, Color backgroundColor, String description, int id) {
        super(character, textColor, backgroundColor, description, id);
    }

    public InteractiveTile(TETile t, Color textColor) {
        super(t, textColor);
    }

    public InteractiveTile(TETile t, char c) {
        super(t, c);
    }
    // May add something like tile status (trapped, poisonous, breakable, ...), some method to change tile status/terrain/something else
    // Not overengineering, just an insurrance for future ambition features:>

//    public InteractiveTile(TETile tileType) {
//        this.tileType = tileType;
//    }
}
