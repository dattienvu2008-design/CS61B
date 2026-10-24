package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class DirectedGraph {
    public static class Node {
        private String[] words;
        private String meaning;
        private int id;
        private ArrayList<Integer> edges = new ArrayList<>(); // May change data type later

        public Node(int ID, String[] wordsLst, String wordsMeaning) {
            words = wordsLst;
            meaning = wordsMeaning;
            id = ID;
        }
    }

    private final HashMap<Integer, Node> nodesID = new HashMap<>();
    private final HashMap<String, HashSet<Node>> nodesWord = new HashMap<>();
    private final HashMap<String, HashMap<Integer, Integer>> wordHistory = new HashMap<>();

    static void main() {
        // Just for fast testing, delete after use!!!
        DirectedGraph graph = new DirectedGraph(sSize82191, hSize82191, wordHistory14377);
        HashSet<Node> h277 = DirectedGraph.getHyponymsNodes(graph.getNode(277), graph);
        HashSet<Node> h44609 = DirectedGraph.getHyponymsNodes(graph.getNode(44609), graph);
        Set<String> h44609str = graph.getHyponyms("gum");
        TreeMap<Integer, HashSet<String>> h44609weight1 = graph.getWeightedHyponyms("mom", 2000,2020);
        TreeMap<Integer, HashSet<String>> h44609weight2 = graph.getWeightedHyponyms("cat", 2000,2020);
        TreeMap<Integer, HashSet<String>> h44609weight3 = graph.getWeightedHyponyms("boat", 2000,2020);
        System.out.println("hello");
    }

    private void synsetFileReaderHelper(String line) {
        String[] arr = line.split(",");
        addNode(Integer.parseInt(arr[0]), arr[1].split(" "), arr[2]);
    }

    private void hypnonymsFileReaderHelper(String line) {
        String[] arr = line.split(",");
        for (int i = 1; i < arr.length; i++) {
            addEdge(Integer.parseInt(arr[0]), Integer.parseInt(arr[i]));
        }
    }

    private void wordHistoryFileReaderHelper (String line) {
        String[] arr = line.split("\\s+");
        HashMap<Integer, Integer> thisWord = wordHistory.get(arr[0]);
        if (thisWord == null) {
            thisWord = new HashMap<Integer, Integer>();
        }
        thisWord.put(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
        wordHistory.put(arr[0], thisWord);
    }

    public DirectedGraph(String synsetFileName, String hypnonymsFileName, String wordHistoryFileName) {
        try (Stream<String> synsetLines = Files.lines(Path.of(synsetFileName));
             Stream<String> hypnonymsLines = Files.lines(Path.of(hypnonymsFileName));
             Stream<String> wordHistoryLines = Files.lines(Path.of(wordHistoryFileName))) {
            synsetLines.forEach(line -> synsetFileReaderHelper(line));
            hypnonymsLines.forEach(line -> hypnonymsFileReaderHelper(line));
            wordHistoryLines.forEach(line -> wordHistoryFileReaderHelper(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNode(int ID, String[] wordLst, String wordsMeaning) {
        nodesID.put(ID, new Node(ID, wordLst, wordsMeaning));
        for (String word : wordLst) {
            if (nodesWord.get(word) == null) {
                nodesWord.put(word, new HashSet<>());
            }
            nodesWord.get(word).add(nodesID.get(ID));
        }
    }

    public void addEdge(int idSrc, int idDest) {
        nodesID.get(idSrc).edges.add(idDest);
    }

    public Node getNode(int id) {
        return nodesID.get(id);
    }

    public HashSet<Node> getNode(String word) {
        return nodesWord.get(word);
    }

    public static HashSet<Node> getHyponymsNodes(Node node, DirectedGraph graph) {
        return getHyponymsNodesHelper(node, graph, new HashSet<>());
    }

    public static HashSet<Node> getHyponymsNodesHelper(Node node, DirectedGraph graph, HashSet<Integer> visited) {
        if (node.edges.isEmpty()) {
            return new HashSet<>(Set.of(node));
        }
        HashSet<Node>[] hyponymsOfAdjNodes = new HashSet[node.edges.size()];
        HashSet<Node> formalSet = new HashSet<>(Set.of(node));
        int cacheEdge;
        for (int i = 0; i < node.edges.size(); i++) {
            cacheEdge = node.edges.get(i);
            if (visited.contains(cacheEdge)) hyponymsOfAdjNodes[i] = new HashSet<>();
            else {
                visited.add(cacheEdge);
                hyponymsOfAdjNodes[i] = getHyponymsNodesHelper(graph.getNode(cacheEdge), graph, visited);
            }
        }
        for (HashSet<Node> set : hyponymsOfAdjNodes) {
            formalSet.addAll(set);
        }
        return formalSet;
    }

    public HashSet<String> getHyponyms(String word) {
        HashSet<Node> wordNodes = getNode(word);
        if (wordNodes == null) {
            return new HashSet<>(Set.of(""));
        }
        HashSet<String> hyponymsLst = new HashSet<>();
        for (Node wordNode : wordNodes) {
            for (Node node : DirectedGraph.getHyponymsNodes(wordNode, this)) {
                hyponymsLst.addAll(Arrays.asList(node.words));
            }
        }
        return hyponymsLst;
    }


    /*
    * Return a map mapped each hyponym to its "weight"
    * (in fact, map hyponym to a number k, in which k is the number of times that hyponym appeared between startYear and endYear)
    * return null if the word does not exist (in data set)
    * */
    public TreeMap<Integer, HashSet<String>> getWeightedHyponyms(String word, int startYear, int endYear) {
        //Just for debugging=)) (I created this one for 4D but now... unused .-.)
        HashSet<Node> wordNodes = getNode(word);
        if (wordNodes == null) {
            return null;
        }
        TreeMap<Integer, HashSet<String>> hyponymsLst = new TreeMap<>();
        HashSet<String> visited = new HashSet<>();
        int currentWordWeight;
        for (Node wordNode : wordNodes) {
            for (Node hyponymsNode : DirectedGraph.getHyponymsNodes(wordNode, this)) {
                for (String hyponymsNodeWord : hyponymsNode.words) {
                    if (!visited.contains(hyponymsNodeWord)) {
                        currentWordWeight = countWordOccurences(hyponymsNodeWord, startYear, endYear);
                        if (hyponymsLst.containsKey(currentWordWeight)) {
                            hyponymsLst.get(currentWordWeight).add(hyponymsNodeWord);
                        }
                        else {
                            HashSet<String> words = new HashSet<>(Set.of(hyponymsNodeWord));
                            hyponymsLst.put(currentWordWeight, words);
                        }
                        visited.add(hyponymsNodeWord);
                    }
                }
            }
        }
        return hyponymsLst;
    }

    public int countWordOccurences (String word, int startYear, int endYear) {
        int wordOccurences;
        wordOccurences = 0;
        if (wordHistory.get(word) != null) {
            for (int year = startYear; year <= endYear; year++) {
                if (wordHistory.get(word).get(year) == null) continue;
                wordOccurences += wordHistory.get(word).get(year);
            }
        }
        return wordOccurences;
    }

    // CONSTANT:
    public static final String hEecs = "data/hyponyms_eecs.txt";
    public static final String hSize10 = "data/hyponyms_size10.txt";
    public static final String hSize11 = "data/hyponyms_size11.txt";
    public static final String hSize14 = "data/hyponyms_size14.txt";
    public static final String hSize16 = "data/hyponyms_size16.txt";
    public static final String hSize25 = "data/hyponyms_size25.txt";
    public static final String hSize1000 = "data/hyponyms_size1000.txt";
    public static final String hSize82191 = "data/hyponyms_size82191.txt";
    public static final String sEecs = "data/synsets_eecs.txt";
    public static final String sSize10 = "data/synsets_size10.txt";
    public static final String sSize11 = "data/synsets_size11.txt";
    public static final String sSize14 = "data/synsets_size14.txt";
    public static final String sSize16 = "data/synsets_size16.txt";
    public static final String sSize25 = "data/synsets_size25.txt";
    public static final String sSize1000 = "data/synsets_size1000.txt";
    public static final String sSize82191 = "data/synsets_size82191.txt";
    public static final String wordHistory14377 = "data/word_history_size14377.csv";
}

/*
* Some personal implementation mistake
* 1. Slightly violate encapsulation principle (Access node attribute through its instance variable instead of getter method
*    -> Hard to debug in the future
* */
