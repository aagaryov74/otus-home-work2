package ru.otus.agaryov.dz2.csvfilereader;

import java.util.LinkedHashMap;

public interface CsvFileReader {
    // Read csv file into a Map
    LinkedHashMap<String, String > readCsvIntoMap();
    // How many correct strings are in config file?
    Integer getReadedStrsCount();
}
