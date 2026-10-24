package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryTextHandler(NGramMap ngmParam) {
        super();
        ngm = ngmParam;
    }

    @Override
    public String handle(NgordnetQuery q) {
        String response = "";
        int startYear = q.startYear(), endYear = q.endYear();
        for (String word : q.words()) {
            response += word + ": " + ngm.weightHistory(word, startYear, endYear) + "\n";
        }
        return response;
    }
}
