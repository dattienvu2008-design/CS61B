package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.checkerframework.checker.units.qual.Time;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryHandler(NGramMap ngmParam) {
        super();
        ngm = ngmParam;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear(), endYear = q.endYear();
        List<TimeSeries> lts = new ArrayList<>();
        TimeSeries currentTs;
        int wordsLength = words.size();
        for (int i = 0; i < wordsLength; i++) {
            currentTs = ngm.weightHistory(words.get(i), startYear, endYear);
            if (currentTs == null) {
                words.remove(i);
                continue;
            }
            lts.add(currentTs);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(q.words(), lts);
        String encoded = Plotter.encodeChartAsString(chart);
        return encoded;
    }
}
