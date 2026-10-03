package game2048rendering;

public class access_side_xy_method {
    public static int side_x(Side side,int x, int y, int size){
        return side.x(x, y, size);
    }

    public static int side_y(Side side, int x, int y, int size){
        return side.y(x, y, size);
    }

    public static void main(String[] args) {
        int[][] board = new int[][]{
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };
        System.out.println(board[0][2]);
        System.out.println(board[side_y(Side.EAST.reverse(), 2, 0, 4)][side_x(Side.EAST.reverse(), 2, 0, 4)]);
    }
}
