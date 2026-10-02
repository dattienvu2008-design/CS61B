package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // fill this in
        if (side == Side.NORTH) {
            // Don't you dare try to write all of your
            // code in this method. You will want to write
            // helper methods. And those helper methods should
            // have helper methods.
            return;
        } else if (side == Side.EAST) {
            return;
        } else if (side == Side.WEST) {
            return;
        } else { // SOUTH
            return;
        }
    }
    public static void tilt_to_north(int[][] board){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                apply_merge(board, row, col);
            }
        }
    }

    private static void apply_merge(int[][] board, int row, int col){
        int[] merged_column = merge(merged_column_of_n(board, row, col), board[row][col]);
        for (int i = 0; i < merged_column.length; i++) {
            board[row+i][col] = merged_column[i];
        }
    }

    public static int[] merged_column_of_n(int[][] board, int row, int col){
        int[] col_arr = new int[row + 1];
        col_arr[0] = 0;
        for (int i = 1; i <= row; i++) {
            col_arr[i] = board[row-i][col];
        }
        return col_arr;
    }

    public static int[] merge(int[] col, int need_to_merged){
        /*
        * col: reverse column from the board, cut from the highest element to the element right before need_to_merged
        *      element and then add 1 more element 0 to the tail
        *      Example: [0,0,4,2] (the first element 0 is the formal need_to_merged position, but set to 0)
        * need_to_merged: nah
        * */
        for (int i = 0; i < col.length; i++) {
            if (need_to_merged == col[i]){
                col[i] *= 2;
                break;
            } else if (col[i] == 0 && i != col.length - 1) {
                continue;
            } else if (col[i] != 0) {
                col[i-1] = need_to_merged;
                break;
            } else if (i == col.length - 1 && col[i] == 0) {
                col[i] = need_to_merged;
                break;
            }
        }
        return col;
    }
}
