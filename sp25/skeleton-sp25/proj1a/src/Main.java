public class Main {
    public static void main(String[] args) {
        Deque61B<Integer> slist = new SimpleList<>();
        Deque61B<Integer> dllist = new LinkedListDeque61B<>();
        slist.addFirst(0);
        slist.addFirst(1);
        slist.addLast(-1);
        dllist.addFirst(0);
        dllist.addLast(-1);
        System.out.println(slist > dllist);
    }
}
