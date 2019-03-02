package ru.otus.agaryov.dz2.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static java.lang.System.out;

@Service
public class ImplIOService implements  IOService {
    private MessageSource messageSource;
    private Locale locale;
    private BufferedReader bufferedReader;
    private AsciiCheckerService asciiCheckerService;

    public ImplIOService(@Qualifier("messageSource") MessageSource messageSource,
                         AsciiCheckerService asciiCheckerService) {
        this.messageSource = messageSource;
        this.asciiCheckerService = asciiCheckerService;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        locale = Locale.getDefault();
    }

    public void printToConsole(String propertyParam) {
        out.println(messageSource.getMessage(propertyParam,null,locale));

    }

    public void printFToConsole(String propertyParam, Object ... args) {
        out.printf(messageSource.getMessage(propertyParam,null,locale),args);
    }

    public String readFromConsole() throws IOException {
        return bufferedReader.readLine();
    }

    public String getMessage(String propertyParam) {
        return messageSource.getMessage(propertyParam,null,locale);
    }

    public String getLocaleLang() {
        return locale.getLanguage();
    }

    public String checkAndSwitchLocale(String inputString) {
        String localeLanguage = locale.getLanguage();
        if (asciiCheckerService.isASCII(inputString)) {
            // inpStr is in ASCII but if locale is in ru_RU we need to change language to english
            if (locale.getLanguage().contentEquals("ru")) {
                locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
                return locale.getLanguage();
            }
        } else {
            // inpStr is not in ASCII so if locale is in en_EN we need to change language to russian
            if (locale.getLanguage().contentEquals("en")) {
                locale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
                return locale.getLanguage();
            }
        }
        return localeLanguage;
    }
}
