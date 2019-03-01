package ru.otus.agaryov.dz2.exam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.agaryov.dz2.csvfilereader.CsvFileReader;
import ru.otus.agaryov.dz2.results.ResultChecker;
import ru.otus.agaryov.dz2.utils.AsciiChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static java.lang.System.out;
@Service
public class ExamExecutor {
    private CsvFileReader csvFile;
    private ResultChecker checker;
    private AsciiChecker asciiChecker;
    private MessageSource ms;


    @Autowired
    public ExamExecutor(CsvFileReader csvFileReader,
                        ResultChecker resultChecker,
                        AsciiChecker asciiChecker,
                        MessageSource ms) {
        this.csvFile = csvFileReader;
        this.checker = resultChecker;
        this.asciiChecker = asciiChecker;
        this.ms = ms;
    }

    public void doExam() throws IOException {

        Locale currentLocale = Locale.getDefault();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        out.println(ms.getMessage("enterFio",null,currentLocale));
        String studentFIO = br.readLine();
        if (asciiChecker.isASCII(studentFIO)) {
            // fio is in ASCII but if locale is in ru_RU we need to change language to english
            if (currentLocale.getLanguage().contentEquals("ru")) {
                currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
                checker.reloadMap(csvFile.readAnotherFile(ms.getMessage("config.csvfile",null,currentLocale)));
            }
        } else {
            // fio is not in ASCII so if locale is in en_EN we need to change language to russian
            if (currentLocale.getLanguage().contentEquals("en")) {
                currentLocale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
                checker.reloadMap(csvFile.readAnotherFile(ms.getMessage("config.csvfile",null,currentLocale)));
            }
        }
        out.printf(ms.getMessage("welcome",null,currentLocale),
                studentFIO,csvFile.getReadedStrsCount());
        for (int i = 0; i < checker.getQuestions().length; i++) {
            String question = checker.getQuestions()[i].toString();
            out.printf(ms.getMessage("question",null,currentLocale),i+1,question);
            String input = br.readLine();
            checker.checkAnswer(question,input);
        }
        out.printf(ms.getMessage("results",null,currentLocale),checker.getResult());
    }
}
