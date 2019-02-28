package ru.otus.agaryov.dz2.results;

import ru.otus.agaryov.dz2.csvfilereader.CsvFileReader;

import java.util.LinkedHashMap;

public class ImplResultChecker implements ResultChecker {
    private Integer result;
    private LinkedHashMap<String, String> qaMap;

    public ImplResultChecker(CsvFileReader csvFileReader){
        this.result = 0;
        if (csvFileReader != null) this.qaMap = csvFileReader.readCsvIntoMap();
    }

    @Override
    public Object[] getQuestions() {
        if (this.qaMap != null) return this.qaMap.keySet().toArray();
        return null;
    }


    @Override
    public void checkAnswer(String question, String answer) {
        if (qaMap != null && qaMap.get(question) != null) {
            if (qaMap.get(question).contentEquals(answer.trim())) this.result++;
        }
    }

    @Override
    public Integer getResult() {
        return this.result;
    }
}
