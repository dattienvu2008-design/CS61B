package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque61BIterator<T> implements Iterator<T> {
    public Deque61B<T> lst;
    public int current_index;

    public Deque61BIterator(Deque61B<T> list){
        lst = list;
        current_index = 0;
    }

    @Override
    public boolean hasNext() {
        return current_index < lst.size();
    }

    @Override
    public T next() {
        if (hasNext()) {
            current_index++;
            return lst.get(current_index - 1);
        } else throw new NoSuchElementException();
    }
}
