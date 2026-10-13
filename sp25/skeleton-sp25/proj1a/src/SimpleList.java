
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class SimpleList<T> implements Deque61B<T>, Comparable<SimpleList<T>> {
    public static void main(String[] args) {
        Deque61B<Integer> lst1 = new SimpleList<>();
        lst1.addFirst(3);
        lst1.addFirst(4);
        lst1.addLast(2);
        System.out.println(lst1.toList());
    }

    private class Node {
        T item;
        Node next;

        Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node first;
    private Node last;
    private int size;

    @Override
    public int compareTo(SimpleList<T> o) {
        return Integer.compare(this.sizeOf(), o.sizeOf());
    }

    public int sizeOf(){
        return this.size;
    }

    public SimpleList() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, first);
        first = newNode;

        if (last == null) {
            last = newNode;
        }

        size++;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, null);

        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }

        size++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node current = first;

        while (current != null) {
            result.add(current.item);
            current = current.next;
        }

        return result;
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
        if (first == null) {
            return null;
        }

        T removedItem = first.item;
        first = first.next;
        size--;

        if (first == null) {
            last = null;
        }

        return removedItem;
    }

    @Override
    public T removeLast() {
        if (last == null) {
            return null;
        }

        T removedItem = last.item;

        if (first == last) {
            first = null;
            last = null;
        } else {
            Node current = first;

            while (current.next != last) {
                current = current.next;
            }

            current.next = null;
            last = current;
        }

        size--;
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = first;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return getRecursiveHelper(first, index);
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }

        return getRecursiveHelper(node.next, index - 1);
    }
}
