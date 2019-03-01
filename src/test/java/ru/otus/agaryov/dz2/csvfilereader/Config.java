package ru.otus.agaryov.dz2.csvfilereader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.TestPropertySource;
import ru.otus.agaryov.dz2.utils.AsciiChecker;

@Configuration
@TestPropertySource(locations = "/test.properties")
public class Config {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("test");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
