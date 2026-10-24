package demo;

import main.NGramMap;
import main.TimeSeries;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import static main.Main.*;
import java.util.ArrayList;
import java.util.List;

public class PlotDemo {
    public static final int YEAR_1900 = 1900;
    public static final int YEAR_1950 = 1950;

    public static void main(String[] args) {

        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE);
        ArrayList<String> words = new ArrayList<>();
        words.add("cat");
        words.add("dog");

        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String word : words) {
            lts.add(ngm.weightHistory(word, YEAR_1900, YEAR_1950));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String s = Plotter.encodeChartAsString(chart);

        // you can also do this to display locally:
        // Plotter.displayChart(chart);

//        List<String> wordsLst = new ArrayList<>(List.of("dog", "cat"));
//        int startYear = 1900, endYear = 1950;
//        List<TimeSeries> lts2 = new ArrayList<>();
//        TimeSeries currentTs;
//        for (int i = 0; i < wordsLst.toArray().length; i++) {
//            currentTs = ngm.weightHistory(wordsLst.get(i), startYear, endYear);
//            if (currentTs == null) {
//                wordsLst.remove(i);
//                continue;
//            }
//            lts2.add(currentTs);
//        }
//        XYChart chart2 = Plotter.generateTimeSeriesChart(wordsLst, lts2);
//        String encoded = Plotter.encodeChartAsString(chart2);
//        System.out.println(encoded);
//        Plotter.displayChart(chart2);
    }
}
