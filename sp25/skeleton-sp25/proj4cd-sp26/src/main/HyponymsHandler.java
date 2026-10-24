package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;
import org.apache.commons.text.similarity.HammingDistance;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private DirectedGraph graph;
    public HyponymsHandler (DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        System.out.println("k is " + q.k());
        if (q.k() == 0) {
            System.out.println("Currently running k = 0"); //DEBUG
            if (words.size() == 1) {
                HashSet<String> hyponyms = graph.getHyponyms(words.get(0));
                return hyponyms.stream().sorted().toList().toString();
            } else {
                HashSet<String> sharedHyponyms = graph.getHyponyms(words.get(0));
                for (int i = 1; i < words.size(); i++) {
                    sharedHyponyms.retainAll(graph.getHyponyms(words.get(i))); //dedupe
                }
                return sharedHyponyms.stream().sorted().toList().toString();
            }
        }
        else {
            HashSet<String> intersect = graph.getHyponyms(words.getFirst());
            for (int i = 1; i < words.size(); i++) {
                intersect.retainAll(graph.getHyponyms(words.get(i)));
            }
            if (q.k() > intersect.size()) {
                return intersect.stream().sorted().toList().toString();
            }
            else {
                Comparator<String> compareByOccurs = (word1, word2) ->
                {return Integer.compare(graph.countWordOccurences(word1, q.startYear(), q.endYear()),
                                        graph.countWordOccurences(word2, q.startYear(), q.endYear()));};
                System.out.println(intersect.stream().sorted(compareByOccurs).skip(intersect.size() - q.k()).toList());
                return intersect.stream().sorted(compareByOccurs).skip(intersect.size() - q.k()).toList().toString();
            }
        }
//        else {
//            // For Debug and Visualization only
//            System.out.println("Currently running k = " + q.k());
//
//            HashSet<String> mostOccursWord = new HashSet<>();
//            TreeMap<Integer, HashSet<String>>[] wordTrees = new TreeMap[words.size()];
//            HashSet<String> intersect = graph.getHyponyms(words.getFirst());
//            for (int i = 0; i < words.size(); i++) {
//                intersect.retainAll(graph.getHyponyms(words.get(i)));
//                wordTrees[i] = graph.getWeightedHyponyms(words.get(i), q.startYear(), q.endYear());
//            }
//            int[] cacheWords = new int[words.size()];
//            int cacheWord = -1, cacheTreeIndex = -1;
//            try {
//                while (mostOccursWord.size() < q.k()) {
//                    cacheWord = -1;
//                    for (int i = 0; i < wordTrees.length; i++) {
//                        cacheWords[i] = wordTrees[i].lastKey();
//                    }
//                    for (int i = 0; i < cacheWords.length; i++) {
//                        if (cacheWords[i] > cacheWord) {
//                            cacheWord = cacheWords[i];
//                            cacheTreeIndex = i;
//                        }
//                    }
//                    mostOccursWord.addAll(wordTrees[cacheTreeIndex].get(cacheWord));
//                    if (mostOccursWord.size() < q.k()) {
//                        wordTrees[cacheTreeIndex].remove(cacheWord);
//                    }
//                }
//            } catch (NoSuchElementException e) {
//                return mostOccursWord.toString();
//            }
//            Iterator<String> iter = wordTrees[cacheTreeIndex].get(cacheWord).iterator();
//            while (mostOccursWord.size() > q.k()) {
//                mostOccursWord.remove(iter.next());
//            }
//            System.out.println(mostOccursWord.toString());
//            return mostOccursWord.toString();
//        }
    }
}
