import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int suml = 0;
        for (int i : L) {
            suml += i;
        }
        return suml;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> even_list = new ArrayList<>();
        for (Integer i : L) {
            if (i % 2 == 0){
                even_list.add(i);
            }
        }
        return even_list;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> common_lst = new ArrayList<>();
        for (Integer i : L1) {
            if (L2.contains(i)){
                common_lst.add(i);
            }
        }
        return common_lst;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int occurs = 0;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == c){
                    occurs++;
                }
            }
        }
        return occurs;
    }
}
