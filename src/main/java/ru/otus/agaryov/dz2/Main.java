package ru.otus.agaryov.dz2;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.agaryov.dz2.csvfilereader.CsvFileReader;
import ru.otus.agaryov.dz2.exam.ExamExecutor;
import ru.otus.agaryov.dz2.results.ImplResultChecker;
import ru.otus.agaryov.dz2.results.ResultChecker;
import ru.otus.agaryov.dz2.utils.AsciiChecker;

import java.io.IOException;
import java.util.ResourceBundle;


import static java.lang.System.*;
@Configuration
@PropertySource("application.properties")
@ComponentScan
public class Main {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("application");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        CsvFileReader csvFile = context.getBean(CsvFileReader.class);
        ResultChecker checker = context.getBean(ImplResultChecker.class);
        ExamExecutor executor = context.getBean(ExamExecutor.class);
        AsciiChecker asciiChecker = context.getBean(AsciiChecker.class);
        if (csvFile.getReadedStrsCount() > 0) {
            try {
                executor.doExam();
            } catch (IOException e) {
                err.println("IO error:");
                e.printStackTrace();
            }
        } else {
            out.println("Sorry but you cannot continue due errors with config file");
        }


    }
}
