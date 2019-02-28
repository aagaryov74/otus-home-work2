package ru.otus.agaryov.dz2;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.agaryov.dz2.csvfilereader.CsvFileReader;
import ru.otus.agaryov.dz2.exam.ExamExecutor;
import ru.otus.agaryov.dz2.results.ImplResultChecker;
import ru.otus.agaryov.dz2.results.ResultChecker;

import java.io.IOException;


import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        CsvFileReader csvFile = context.getBean(CsvFileReader.class);
        ResultChecker checker = context.getBean(ImplResultChecker.class);
        ExamExecutor executor = context.getBean(ExamExecutor.class);
        if (csvFile.getReadedStrsCount() > 0) {
            try {
                executor.doExam();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            out.println("Sorry but you cannot continue due errors with config file");
        }


    }
}
