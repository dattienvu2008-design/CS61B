import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public static void main(String[] args) {
        //Test area
        Deque61B llst1 = new LinkedListDeque61B();
        llst1.addFirst(1);
        llst1.addFirst(2);
        llst1.addLast(0);
        List<Integer> converted = llst1.toList();
        System.out.println(converted);
        llst1.removeFirst();
        System.out.println(llst1.toList());
    }
    public static class Node<T>/* intNode implementation */{
        public T item;
        public Node<T> next, prev;

        public Node(T i, Node<T> next_node, Node<T> prev_node){
            item = i;
            next = next_node;
            prev = prev_node;
        }
    }

    // ---------------------------------
    // Attributes:
    private int size;
    private Node<T> sentinel;
    private Node<T> first;
    // ---------------------------------

    public LinkedListDeque61B()/* Empty list */{
        //instantiating sentinel, do not modify
        sentinel = new Node<T>(null, null, null);

        first = sentinel;
        size = 0;
    }

    public LinkedListDeque61B(T x) /* List with init value */{
        //instantiating sentinel, do not modify
        sentinel = new Node<T>(null, null, null);

        first = new Node<T>(x, sentinel, sentinel);
        sentinel.next = sentinel.prev = first;
        size = 1;
    }
    @Override
    public void addFirst(T x) {
        this.first = this.first.next = new Node<T>(x, sentinel, this.first);

        sentinel.prev = this.first;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == 0){
            addFirst(x);
        } else {
            this.sentinel.next = this.sentinel.next.prev = new Node<T>(x, this.sentinel.next, this.sentinel);
            size++;
        }
    }

    @Override
    public List<T> toList() {
        Node<T> pointer = this.sentinel.prev;
        List<T> this_lst = new ArrayList<>();
        while (pointer != sentinel){
            this_lst.add(pointer.item);
            pointer = pointer.prev;
        }
        return this_lst;
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
        if (size != 0) {
            T item = this.first.item;
            this.first.prev.next = this.sentinel;
            this.sentinel.prev = this.first = this.first.prev;
            return item;
        }
        else return null;
    }

    @Override
    public T removeLast() {
        if (size != 0) {
            T item = this.sentinel.next.item;
            this.sentinel.next.next.prev = this.sentinel;
            this.sentinel.next = this.sentinel.next.next;
            return item;
        }
        else return null;
    }

    @Override
    public T get(int index) {
        int current_index = 0;
        Node<T> current_node = this.sentinel.prev;
        while (current_index < size && current_index >= 0) {
            if (current_index == index) {
                return current_node.item;
            }
            current_index++;
            current_node = current_node.prev;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
