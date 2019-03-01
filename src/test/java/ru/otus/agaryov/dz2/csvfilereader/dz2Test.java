package ru.otus.agaryov.dz2.csvfilereader;


import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.agaryov.dz2.Main;
import ru.otus.agaryov.dz2.results.ImplResultChecker;
import ru.otus.agaryov.dz2.results.ResultChecker;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration
@TestPropertySource("test.properties")
class dz2Test {

    //@Value("#{${questions}}")
    private LinkedHashMap<String,String> cMap;

    @Value("${question.one}")
    private String q1;

    @Test
    void checkAnswers() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(dz2Test.class);
        CsvFileReader reader = mock(CsvFileReader.class);

        // Не написали еще чтение из файла в мапу, но уже хотим проверить, как ответопроверятель работает
        when(reader.readCsvIntoMap()).thenReturn(cMap);
        System.out.println("q1 = " + q1);
        // Ответопроверятель
        ResultChecker resChecker = new ImplResultChecker(reader);
        // Прокладка
        ResultChecker sChecker = Mockito.spy(resChecker);

        sChecker.checkAnswer("Сколько ног у Мальвины", "2");
        sChecker.checkAnswer("Сколько месяцев в году", "12");
        sChecker.checkAnswer("Сколько месяцев в году", "1");
        sChecker.checkAnswer("Сколько колес у Машины", "1");

        int res = sChecker.getResult();
        assertEquals(2,res, "Right answers must be 2!");

        verify(sChecker, times(1)).
                checkAnswer("Сколько ног у Мальвины", "2");

        verify(sChecker, never()).
                checkAnswer("Сколько деревьев в саду", "21");

        int qCount = sChecker.getQuestions().length;

        assertEquals(4,qCount,"Questions count must be 4!");

    }
}