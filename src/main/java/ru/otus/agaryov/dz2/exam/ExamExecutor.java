package ru.otus.agaryov.dz2.exam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.agaryov.dz2.csvfilereader.CsvFileReader;
import ru.otus.agaryov.dz2.results.ResultChecker;
import ru.otus.agaryov.dz2.service.AsciiCheckerService;
import ru.otus.agaryov.dz2.service.IOService;

import java.io.IOException;

@Service
public class ExamExecutor {
    private CsvFileReader csvFile;
    private ResultChecker checker;
    private AsciiCheckerService asciiCheckerService;
    private IOService ioService;


    @Autowired
    public ExamExecutor(@Qualifier("implCsvFileReader") CsvFileReader csvFileReader,
                        ResultChecker resultChecker,
                        IOService ioService) {
        this.csvFile = csvFileReader;
        this.checker = resultChecker;
        this.ioService = ioService;
    }

    public void doExam() {


        try {
            ioService.printToConsole("enterFio");
            String studentFIO = ioService.readFromConsole();
            if (ioService.checkAndSwitchLocale(studentFIO))
                checker.setMap(csvFile.setCsvFile(ioService.getApplicatonMessages("config.csvfile")));
            ioService.printFToConsole("welcome",
                    studentFIO, csvFile.getReadedStrsCount());
            for (int i = 0; i < checker.getQuestions().length; i++) {
                String question = checker.getQuestions()[i].toString();
                ioService.printFToConsole("question",
                        i + 1, question);
                String input = ioService.readFromConsole();
                checker.checkAnswer(question, input);
            }
            ioService.printFToConsole("results", checker.getResult());
        } catch (IOException e) {
            ioService.printToConsole("iowarning");
        }
    }
}
