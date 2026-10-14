import deque.Deque61B;
import deque.Maximizer61B;
import deque.ArrayDeque61B;

import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MyTest {
    @Test
    public void test_init(){
        Deque61B<Integer> empty_lst = new ArrayDeque61B<>();
        Deque61B<Integer> full_lst = new ArrayDeque61B<>(new Integer[]{1,2,3,4});
        Deque61B<Integer> lst = new ArrayDeque61B<>(new Integer[]{1,2,3});
        assertThat(empty_lst.toList()).hasSize(0);
        assertThat(full_lst.toList()).containsExactly(1,2,3,4);
        assertThat(lst.toList()).containsExactly(1,2,3);
    }

    @Test
    public void test_add(){
        Deque61B<Integer> empty_lst = new ArrayDeque61B<>();
        Deque61B<Integer> full_lst = new ArrayDeque61B<>(new Integer[]{1,2,3,4});
        empty_lst.addLast(1); empty_lst.addFirst(0); empty_lst.addLast(2);
        assertThat(empty_lst.toList()).containsExactly(0,1,2);
        full_lst.addLast(5); full_lst.addLast(6); full_lst.addFirst(0); full_lst.addLast(7);
        full_lst.addFirst(-1); full_lst.addFirst(-2);
        assertThat(full_lst.toList()).containsExactly(-2,-1,0,1,2,3,4,5,6,7);
    }

    @Test
    public void test_get(){
        Deque61B<Integer> empty_lst = new ArrayDeque61B<>();
        Deque61B<Integer> full_lst = new ArrayDeque61B<>(new Integer[]{1,2,3,4,5,6});
        assertThat(full_lst.get(2)).isEqualTo(3);
        assertThat(full_lst.get(0)).isEqualTo(1);
        assertThat(full_lst.get(5)).isEqualTo(6);
        assertThat(full_lst.get(-1)).isNull();
        assertThat(full_lst.get(6)).isNull();
    }

    @Test
    public void test_remove(){
        ArrayDeque61B<Integer> long_lst = new ArrayDeque61B<>();
        for (int i = 0; i < 513; i++) long_lst.addFirst(i);
        for (int i = 0; i < 385; i++) long_lst.removeFirst();
    }
}
