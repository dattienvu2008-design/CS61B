package personal_test;

import game2048logic.GameLogic;

import game2048rendering.Side;
import org.junit.jupiter.api.*;

public class personal_test {
    @Test
    public void test_merge(){
        int[] sample_col_blocked = {0,0,4,2}, sample_col_all0 = {0,0,0,0};
        int[] sample_col_merged = {0,0,32,4}, sample_col_all_blocked = {0,4,16,2};
        GameLogic.merge(sample_col_blocked, 16);
        Assertions.assertArrayEquals(new int[]{0,16,4,2}, sample_col_blocked, "Mảng sau khi merge không đúng!");
        GameLogic.merge(sample_col_all0, 16);
        Assertions.assertArrayEquals(new int[]{0,0,0,16}, sample_col_all0, "Mảng sau khi merge không đúng!");
        GameLogic.merge(sample_col_merged, 32);
        Assertions.assertArrayEquals(new int[]{0,0,64,4}, sample_col_merged, "Mảng sau khi merge không đúng!");
        GameLogic.merge(sample_col_all_blocked, 2);
        Assertions.assertArrayEquals(new int[]{2,4,16,2}, sample_col_all_blocked, "Mảng sau khi merge không đúng!");
    }

    @Test
    public void test_merged_column_of_n(){
        int[][] board = new int[][]{
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };
        int[] result1 = GameLogic.merged_column_of_n(board, 3, 2, Side.NORTH, 4);
        int[] result2 = GameLogic.merged_column_of_n(board, 0, 2, Side.SOUTH, 4);
        int[] result3 = GameLogic.merged_column_of_n(board, 1, 1, Side.EAST, 4);
        int a;
        a = 5;
    }

    @Test
    public void test_tilt_to_north(){
        int[][] board = new int[][]{
                {1,  2,  3,  4,  5},
                {6,  7,  8,  9,  10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        System.out.println(GameLogic.size_of(board));
    }

    @Test
    public void test_side(){
        int[][] board = new int[][]{
                {1,  2,  3,  4,  5},
                {6,  7,  8,  9,  10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        Side N = Side.NORTH;
        Side S = Side.SOUTH;
        Side E = Side.EAST;
        Side W = Side.WEST;
        System.out.println(board[1][1]);
        System.out.println(board[N.x(1,1, 5)][N.y(1, 1, 5)]);
        System.out.println(board[S.x(1,1, 5)][S.y(1, 1, 5)]);
        System.out.println(board[E.x(1,1, 5)][E.y(1, 1, 5)]);
        System.out.println(board[W.x(1,1, 5)][W.y(1, 1, 5)]);
    }
}
