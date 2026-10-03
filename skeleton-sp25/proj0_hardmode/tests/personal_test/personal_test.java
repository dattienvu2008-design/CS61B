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
                {2, 2, 4, 8},
        };
        int[] result1 = GameLogic.merged_column_of_n(board, 0, 2, Side.EAST);
        int[] result2 = GameLogic.merged_column_of_n(board, 3, 0, Side.NORTH);
        int[] result3 = GameLogic.merged_column_of_n(board, 3, 3, Side.WEST);
        GameLogic.apply_merge(board, 3, 3, Side.WEST);
        System.out.println();
    }

    @Test
    public void test_tilt_to_north(){
        int[][] board = new int[][]{
                {0, 0, 4, 0},
                {2, 0, 2, 2},
                {8, 0, 2, 4},
                {2, 0, 4, 0},
        };
        GameLogic.tiltToNorth(board, Side.NORTH);
    }

    @Test
    public void test_rotate(){
        int[][] board = new int[][]{
                {0, 0, 4, 0},
                {2, 0, 2, 2},
                {8, 0, 2, 4},
                {2, 0, 4, 0},
        };
        GameLogic.tiltToNorth(board, Side.NORTH);
        GameLogic.rotate(board, Side.WEST);
        System.out.println("hello");
    }
}
