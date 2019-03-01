package ru.otus.agaryov.dz2.results;

import java.util.LinkedHashMap;

public interface ResultChecker {
    // Check an answer to a question
    void checkAnswer(String question, String answer);
    // All questions in right order
    Object[] getQuestions();
    // get right answers counter
    Integer getResult();

    void reloadMap(LinkedHashMap<String, String> aMap);
}
