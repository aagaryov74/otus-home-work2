package ru.otus.agaryov.dz2.service;

import java.io.IOException;

public interface IOService {
    void printToConsole(String propertyParam);
    void printFToConsole(String propertyParam, Object ... args);
    String readFromConsole() throws IOException;
    String getApplicatonMessages(String propertyParam);
    Boolean checkAndSwitchLocale(String inputString);

}
