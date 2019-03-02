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
public class IOService {
    private MessageSource ms;
    private Locale locale;
    private BufferedReader br;
    private AsciiCheckerService asciiCheckerService;

    public IOService(@Qualifier("messageSource") MessageSource ms,
                     AsciiCheckerService asciiCheckerService) {
        this.ms = ms;
        this.asciiCheckerService = asciiCheckerService;
        br = new BufferedReader(new InputStreamReader(System.in));
        locale = Locale.getDefault();
    }

    public void outCons(String propParam) {
        out.println(ms.getMessage(propParam,null,locale));

    }

    public void outFCons(String propParam, Object ... args) {
        out.printf(ms.getMessage(propParam,null,locale),args);
    }

    public String rCons() throws IOException {
        return br.readLine();
    }

    public String getMess(String propParam) {
        return ms.getMessage(propParam,null,locale);
    }

    public Boolean checkAndSwitchLocale(String inpStr) {
        Boolean isLocaleChanged = false;
        if (asciiCheckerService.isASCII(inpStr)) {
            // inpStr is in ASCII but if locale is in ru_RU we need to change language to english
            if (locale.getLanguage().contentEquals("ru")) {
                locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
                isLocaleChanged = true;
            }
        } else {
            // inpStr is not in ASCII so if locale is in en_EN we need to change language to russian
            if (locale.getLanguage().contentEquals("en")) {
                locale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
                isLocaleChanged = true;
            }
        }
        return isLocaleChanged;
    }
}
