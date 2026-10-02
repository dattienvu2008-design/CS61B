package personal_test;

import game2048logic.GameLogic;
import game2048rendering.Side;
import org.junit.jupiter.api.*;

import static com.google.common.truth.Truth.assertWithMessage;
import static tester2048.TestUtils.boardToString;
import static tester2048.TestUtils.checkTilt;

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
                {4, 8, 12, 16},
                {17,18,19,20}
        };
        int[] result1 = GameLogic.merged_column_of_n(board, 4, 2);
        Assertions.assertArrayEquals(new int[]{0,12,11,10,9}, result1, "?");
        int[] result2 = GameLogic.merged_column_of_n(board, 0, 2);
        Assertions.assertArrayEquals(new int[]{0}, result2, "?");
    }
}
