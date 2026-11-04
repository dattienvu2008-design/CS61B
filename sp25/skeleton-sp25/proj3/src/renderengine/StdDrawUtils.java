package renderengine;

import edu.princeton.cs.algs4.StdDraw;

//Cited: AI/LLM Generated
public class StdDrawUtils {
    public static void drawRectBottomLeft(double x, double y, double w, double h) {
        double cx = x + w / 2.0;
        double cy = y + h / 2.0;
        double halfWidth = w / 2.0;
        double halfHeight = h / 2.0;
        StdDraw.rectangle(cx, cy, halfWidth, halfHeight);
    }

    public static void filledRectBottomLeft(double x, double y, double w, double h) {
        double cx = x + w / 2.0;
        double cy = y + h / 2.0;
        double halfWidth = w / 2.0;
        double halfHeight = h / 2.0;

        StdDraw.filledRectangle(cx, cy, halfWidth, halfHeight);
    }
}
