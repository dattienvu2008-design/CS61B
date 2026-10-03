package game2048logic;

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
        if (side == Side.NORTH) {
            // Don't you dare try to write all of your
            // code in this method. You will want to write
            // helper methods. And those helper methods should
            // have helper methods.
            tiltToNorth(board, side);
            return;
        } else if (side == Side.EAST) {
            tiltToNorth(board, side);
            rotate(board, side);
            return;
        } else if (side == Side.WEST) {
            tiltToNorth(board, side);
            rotate(board, side);
            return;
        } else { // SOUTH
            tiltToNorth(board, side);
            rotate(board, Side.SOUTH);
            return;
        }
    }

    public static void rotate(int[][] board, Side side){
        int[][] copy_board = new int[board.length][board[0].length];
        int temp_x, temp_y;
        for (int row = 0; row < board.length; row++) {
            //Create a deepcopy of board
            //(to prevent mutate formal board while looping)
            System.arraycopy(board[row], 0, copy_board[row], 0, board[row].length);
        }
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                //rotating
                temp_x = game2048rendering.access_side_xy_method.side_x(side, col, row, copy_board.length);
                temp_y = game2048rendering.access_side_xy_method.side_y(side, col, row, copy_board.length);
                board[temp_y][temp_x] = copy_board[row][col];
            }
        }
    }

    public static void tiltToNorth(int[][] board, Side side){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                apply_merge(board, row, col, side);
            }
        }
    }

    public static void apply_merge(int[][] board, int row, int col, Side side){
        //Later: create a method to switch between rotated_x and real x instead of calling awkwardly function from Side
        int[] merged_column = merge(merged_column_of_n(board, row, col, side), board[row][col]);
        int rotated_x = side.x(col, row, board.length), rotated_y = side.y(col, row, board.length);
        for (int i = 0; i <= rotated_y; i++) {
            board[side.reverse().y(rotated_x, rotated_y-i, board.length)-i][
                    side.reverse().x(rotated_x, rotated_y-i, board.length)] = merged_column[i] = merged_column[i];
        }
    }

    public static int[] merged_column_of_n(int[][] board, int row, int col, Side side){
        //x is col, y is row
        int rotated_x = side.x(col, row, board.length), rotated_y = side.y(col, row, board.length);
        int[] col_arr = new int[rotated_y + 1];
        col_arr[0] = 0;
        for (int i = 1; i <= rotated_y; i++) {
            col_arr[i] = board[side.reverse().y(rotated_x, rotated_y-i, board.length)][
                    side.reverse().x(rotated_x, rotated_y-i, board.length)];
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
