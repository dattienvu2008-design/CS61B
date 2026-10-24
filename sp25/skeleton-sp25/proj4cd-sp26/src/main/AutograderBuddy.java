package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {
        DirectedGraph graph = new DirectedGraph(synsetFile, hyponymFile, wordHistoryFile);
        return new HyponymsHandler(graph);
    }
}
