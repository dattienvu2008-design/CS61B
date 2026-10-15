import deque.ArrayDeque61B;

import deque.Deque61B;
import deque.LinkedListDeque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    public void test_init(){
    List<Integer> long_lst = new ArrayList<>();
    for (int i = 0; i < 500; i++) long_lst.add(i);
    Deque61B<Integer> empty_lst = new ArrayDeque61B<>();
    Deque61B<Integer> full_lst = new ArrayDeque61B<>(new Integer[]{1,2,3,4});
    Deque61B<Integer> long_61blst = new ArrayDeque61B<>(long_lst.toArray(new Integer[0]));
    assertThat(empty_lst.toList()).hasSize(0);
    assertThat(full_lst.toList()).containsExactly(1,2,3,4);
    assertThat(long_61blst.toList()).isEqualTo(long_lst);
}

    @Test
    public void test_add_get(){
        Deque61B<Integer> empty_lst = new ArrayDeque61B<>();
        Deque61B<Integer> full_lst = new ArrayDeque61B<>(new Integer[]{1,2,3,4});
        Deque61B<Integer> long_lst = new ArrayDeque61B<>();

        //Basic test
        empty_lst.addLast(1); empty_lst.addFirst(0); empty_lst.addLast(2);
        assertThat(empty_lst.toList()).containsExactly(0,1,2);
        full_lst.addLast(5); full_lst.addLast(6); full_lst.addFirst(0); full_lst.addLast(7);
        full_lst.addFirst(-1); full_lst.addFirst(-2);
        assertThat(full_lst.toList()).containsExactly(-2,-1,0,1,2,3,4,5,6,7);

        //Edge test
        for (int i = 0; i < 129; i++) long_lst.addLast(i);
        for (int i = 0; i < 129; i++) assertThat(long_lst.get(i)).isEqualTo(i);
        long_lst = new ArrayDeque61B<>(new Integer[]{0,1,2,3,4,5,6});
        for (int i = 7; i < 130; i++) long_lst.addLast(i);
        for (int i = 0; i < 130; i++) assertThat(long_lst.get(i)).isEqualTo(i);
        long_lst = new ArrayDeque61B<>(new Integer[]{0,1,2,3,4,5,6,7});
        for (int i = 8; i < 130; i++) long_lst.addLast(i);
        for (int i = 0; i < 130; i++) assertThat(long_lst.get(i)).isEqualTo(i);
    }

    @Test
    public void test_iterable() {
        ArrayDeque61B<Integer> lst = new ArrayDeque61B<>();
        int count = 0;
        for (int i = 0; i < 50; i++) {
            lst.addLast(i);
        }
        System.out.println(lst.toList());
        for (Integer i : lst) {
            assertThat(i).isEqualTo(count);
            count++;
        }
    }

    @Test
    public void test_equals() {
        //Compare LinkedListDeque61B and ArrayDeque61B
        Deque61B<Integer> arr_lst = new ArrayDeque61B<>(new Integer[]{0,1,2,3});
        Deque61B<Integer> linked_lst = new LinkedListDeque61B<>();
        for (int i = 0; i < 4; i++) {
            linked_lst.addLast(i);
        }
        assertThat(arr_lst.equals(linked_lst)).isTrue();
        assertThat(arr_lst.equals(new ArrayList<Integer>())).isFalse();
        linked_lst.addFirst(-1);
        assertThat(linked_lst.equals(arr_lst)).isFalse();
    }
}
