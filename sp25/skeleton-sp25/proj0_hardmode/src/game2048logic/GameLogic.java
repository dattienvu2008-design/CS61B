package game2048logic;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import game2048rendering.Side;

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
        int size = size_of(board);
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

    public static int size_of(int[][] board)/*Size of board*/{
        int size = 0;
        try {
            int random, count = 0;
            while(true){
                random = board[count][0];
                size++;
                count++;
            }
        }
        catch (ArrayIndexOutOfBoundsException a){
            return size;
        }
    }

    public static void tilt_side(int[][] board, Side side, int size){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                apply_merge(board, row, col, side, size);
            }
        }
    }

    public static void apply_merge(int[][] board, int row, int col, Side side, int size){
        int[] merged_column = merge(merged_column_of_n(board, row, col, side, size), board[row][col]);
        for (int i = 0; i < merged_column.length; i++) {
            board[row+i][col] = merged_column[i];
        }
    }

    public static int[] merged_column_of_n(int[][] board, int row, int col, Side side, int size){
        //int[] col_lst = new int[row + 1];
        List<Integer> col_lst = new ArrayList<>();
        col_lst.add(0);
        int count = 0;
        try {
            int rX, rY;
            while (true){
                count++;
                rX = side.x(col, row - count, size);
                rY = side.y(col, row - count, size);
                col_lst.add(board[rX][rY]);
            }
        }
        catch (ArrayIndexOutOfBoundsException a){
            int[] col_arr = new int[count];
            for (int i = 0; i < count; i++) {
                col_arr[i] = col_lst.get(i);
            }
            return col_arr;
        }
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
