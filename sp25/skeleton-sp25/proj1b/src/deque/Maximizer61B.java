package deque;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        T max_ele = null;
        int count = 0;
        for (T t : iterable) {
            if (count == 0) {
                max_ele = t;
                count++;
                continue;
            }
            if (t.compareTo(max_ele) > 0) max_ele = t;
            count++;
        }
        return max_ele;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator tso compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable, Comparator<T> comp) {
        Iterator<T> iterator = iterable.iterator();
        T max_ele = iterator.next();
        T next_ele = null;
        while (iterator.hasNext()) {
            next_ele = iterator.next();
            if (max_ele.compareTo(next_ele) < 0) max_ele = next_ele;
        }
        return max_ele;
    }

    public static void main(String[] args) {
        // The style checker will complain about this main method, feel free to delete.

         ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
         ad.addLast(5);
         ad.addLast(12);
         ad.addLast(17);
         ad.addLast(23);
         System.out.println(max(ad));
    }
}
