package deque;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    //Attributes: (back to private after debug)
    public T[] lst;
    public int size;
    public int first, last; //first: index that addFirst will add at
                             //last: index that addLast will add at

    public ArrayDeque61B(){
        lst = (T[]) new Object[8];
        size = 0;
        first = last = 0;
    }

    public ArrayDeque61B(T[] arr){
        int length = 1;
        while (length < arr.length) length *= 2;
        if (length > 8) lst = (T[]) new Object[length];
        else lst = (T[]) new Object[8];
        for (int i = 0; i < arr.length; i++) {
            lst[i] = arr[i];
        }
        size = arr.length;
        first = index_shift(0, -1);
        last = index_shift(arr.length - 1, 1);
    }

    /*
    * Shift index by dist point
    * */
    private int index_shift(int index, int dist){
        int temp = index + dist;
        while(temp < 0) temp += lst.length;
        while(temp > lst.length - 1) temp -= lst.length;
        return temp;
    }

    public void upsize(){
        T[] new_lst = (T[]) new Object[lst.length*2];
        int index = index_shift(first, 1);
        for (int i = 0; i < lst.length; i++) {
            new_lst[i] = lst[index];
            index = index_shift(index, 1);
        }
        lst = new_lst;
        first = index_shift(0, -1);
        last = index_shift(size - 1, 1);
    }

    public void downsize(){
        if (lst.length/4 >= 8) {
            T[] new_lst = (T[]) new Object[lst.length/4];
            for (int i = 0; i < size; i++) new_lst[i] = lst[i];
            lst = new_lst;
        } else if (lst.length > 8) {
            T[] new_lst = (T[]) new Object[8];
            for (int i = 0; i < size; i++) new_lst[i] = lst[i];
            lst = new_lst;
        }
    }

    @Override
    public void addFirst(T x) {
        if (size() == lst.length) upsize();
        lst[first] = x;
        if (first == last) {
            first = index_shift(first, -1);
            last = index_shift(last, 1);
        } else first = index_shift(first, -1);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size() == lst.length) upsize();
        lst[last] = x;
        if (first == last) {
            first = index_shift(first, -1);
            last = index_shift(last, 1);
        } else last = index_shift(last, 1);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> new_lst = new ArrayList<>();
        int index = index_shift(first, 1);
        for (int i = 0; i < size; i++) {
            new_lst.add(lst[index]);
            index = index_shift(index, 1);
        }
        return new_lst;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T removed = get(first);
        lst[first] = null;
        first = index_shift(first, 1);
        size--;
        if (size <= lst.length/4) downsize();
        return removed;
    }

    @Override
    public T removeLast() {
        T removed = get(last);
        lst[last] = null;
        last = index_shift(last, -1);
        size--;
        if (size <= lst.length/4) downsize();
        return removed;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) return null;
        return lst[index_shift(first, index+1)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
