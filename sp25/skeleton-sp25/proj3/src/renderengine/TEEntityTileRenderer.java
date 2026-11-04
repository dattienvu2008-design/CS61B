package renderengine;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class TEEntityTileRenderer extends TETileRenderer{

    public TEEntityTileRenderer(char character, Color textColor, Color backgroundColor, String description, String filepath, int id, boolean blockable) {
        super(character, textColor, backgroundColor, description, filepath, id, blockable);
    }

    public void draw(double x, double y, double healthPercentage) {
        if (getFilepath() != null) {
            try {
                StdDraw.picture(x + 0.5, y + 0.5, getFilepath());
                drawHealthHelper(x, y, healthPercentage);
                return;
            } catch (IllegalArgumentException e) {
                // Exception happens because the file can't be found. In this case, fail silently
                // and just use the character and background color for the tile.
            }
        }

        drawHealthHelper(x, y, healthPercentage);
        StdDraw.setPenColor(getBackgroundColor());
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
        StdDraw.setPenColor(getTextColor());
        StdDraw.text(x + 0.5, y + 0.5, Character.toString(character()));
    }

    private void drawHealthHelper(double x, double y, double healthPercentage) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(x + 0.5, y + 1, 0.5, 0.06);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledRectangle(x + 0.5, y + 1, 0.5 * healthPercentage, 0.06);
    }
}
