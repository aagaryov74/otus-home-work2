package ru.otus.agaryov.dz2.csvfilereader;

import java.util.LinkedHashMap;

public interface CsvFileReader {
    // Read csv file into a Map
    LinkedHashMap<String, String > readCsvIntoMap();
    // How many correct strings are in config file?
    Integer getReadedStrsCount();
    // change file if we need to change locale in runtime;
    LinkedHashMap<String,String> readAnotherFile(String fileName);
}
