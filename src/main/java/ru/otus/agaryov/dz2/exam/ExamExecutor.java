package ru.otus.agaryov.dz2.exam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.agaryov.dz2.csvfilereader.CsvFileReader;
import ru.otus.agaryov.dz2.results.ResultChecker;
import ru.otus.agaryov.dz2.service.AsciiCheckerService;
import ru.otus.agaryov.dz2.service.IOService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static java.lang.System.out;
@Service
public class ExamExecutor {
    private CsvFileReader csvFile;
    private ResultChecker checker;
    private AsciiCheckerService asciiCheckerService;
    private IOService ioServ;


    @Autowired
    public ExamExecutor(@Qualifier("implCsvFileReader") CsvFileReader csvFileReader,
                        ResultChecker resultChecker,
                        IOService ioServ) {
        this.csvFile = csvFileReader;
        this.checker = resultChecker;
        this.ioServ = ioServ;
    }

    public void doExam() {


        try {
            ioServ.outCons("enterFio");
            String studentFIO = ioServ.rCons();
            if (ioServ.checkAndSwitchLocale(studentFIO))
                checker.setMap(csvFile.setCsvFile(ioServ.getAppMess("config.csvfile")));
            ioServ.outFCons("welcome",
                    studentFIO, csvFile.getReadedStrsCount());
            for (int i = 0; i < checker.getQuestions().length; i++) {
                String question = checker.getQuestions()[i].toString();
                ioServ.outFCons("question",
                        i + 1, question);
                String input = ioServ.rCons();
                checker.checkAnswer(question, input);
            }
            ioServ.outFCons("results", checker.getResult());
        } catch (IOException e) {
            ioServ.outCons("iowarning");
        }
    }
}
