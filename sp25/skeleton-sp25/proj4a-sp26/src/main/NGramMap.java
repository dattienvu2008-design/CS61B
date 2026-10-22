package main;

import org.checkerframework.checker.units.qual.Time;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private TimeSeries yearsHistory = new TimeSeries();
    private HashMap<String, TimeSeries> wordHistory = new HashMap<>();
    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    private void readYearHistoryFileHelper(String line) {
        String[] arr = line.split(",");
        //Each year only appear once in file -> No need to check for duplicates
        yearsHistory.put(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]));
    }

    private void readWordHistoryFileHelper(String line) {
        String[] arr = line.split("\\s+");
        TimeSeries thisWord = wordHistory.get(arr[0]);
        if (thisWord == null) {
            thisWord = new TimeSeries();
        }
        thisWord.put(Integer.parseInt(arr[1]), Double.parseDouble(arr[2]));
        wordHistory.put(arr[0], thisWord);
    }

    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        try (Stream<String> lines = Files.lines(Path.of(wordHistoryFilename))) {
            lines.forEach(line -> readWordHistoryFileHelper(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Stream<String> lines = Files.lines(Path.of(yearHistoryFilename))) {
            lines.forEach(line -> readYearHistoryFileHelper(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (wordHistory.containsKey(word)) {
            TimeSeries copy = new TimeSeries();
            TimeSeries thisWordTimeSeries = wordHistory.get(word);
            Double thisYearOccur;
            for (int i = startYear; i <= endYear; i++) {
                thisYearOccur = thisWordTimeSeries.get(i);
                if (thisYearOccur != null) {
                    copy.put(i, thisYearOccur);
                }
            }
            return copy;
        }
        return new TimeSeries();
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (wordHistory.containsKey(word)) {
            TimeSeries copy = new TimeSeries();
            TimeSeries thisWordTimeSeries = wordHistory.get(word);
            Double thisYearOccur;
            for (Integer year : thisWordTimeSeries.years()) {
                copy.put(year, thisWordTimeSeries.get(year));
            }
            return copy;
        }
        return new TimeSeries();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries copy = new TimeSeries();
        for (Integer i : yearsHistory.keySet()) {
            copy.put(i, yearsHistory.get(i));
        }
        return copy;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {

        TimeSeries boundedWordHistory = wordHistory.get(word);
        for (Integer year : boundedWordHistory.years()) {
            if (year < startYear || year > endYear) {
                boundedWordHistory.remove(year);
            }
        }
        return boundedWordHistory.dividedBy(yearsHistory);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return wordHistory.get(word).dividedBy(yearsHistory);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summed = new TimeSeries();
        for (String word : words) {
            summed = summed.plus(weightHistory(word, startYear, endYear));
        }
        return summed;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summed = new TimeSeries();
        for (String word : words) {
            summed.plus(weightHistory(word));
        }
        return summed;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
