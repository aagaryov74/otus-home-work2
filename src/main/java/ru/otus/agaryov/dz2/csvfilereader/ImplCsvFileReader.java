package ru.otus.agaryov.dz2.csvfilereader;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImplCsvFileReader implements CsvFileReader {

    // Config File with questions
    private  String csvFile;
    // Counter of strings that have been read
    private Integer readStrCounter;

    @Autowired
    public ImplCsvFileReader(@Value("${config.csvfile:QuestionsAndAnswers.csv}") String fileName) {
        this.readStrCounter = 0;
        this.csvFile = fileName;
    }

    @Override
    public LinkedHashMap<String,String> readCsvIntoMap() {
        CSVReader reader;
        this.readStrCounter=0;
        LinkedHashMap<String,String> qaMap = new LinkedHashMap<>();
        try {
            reader = new CSVReader(new FileReader(this.csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                    qaMap.put(line[0], line[1]);
                    this.readStrCounter++;
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error at reading config file  " + csvFile + ": "
                    + e.toString());

        }
        return qaMap;
    }

    @Override
    public LinkedHashMap<String,String> readAnotherFile(String fileName) {
        this.csvFile = fileName;
        return readCsvIntoMap();
    }

    @Override
    public Integer getReadedStrsCount() {
        return readStrCounter;
    }

}
