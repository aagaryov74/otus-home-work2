package ru.otus.agaryov.dz2.service;

import java.io.IOException;

public interface IOService {

    void printToConsole(String propertyParam);
    void printFToConsole(String propertyParam, Object ... args);
    String readFromConsole() throws IOException;
    String checkAndSwitchLocale(String inputString);
    String getLocaleLang();

}
