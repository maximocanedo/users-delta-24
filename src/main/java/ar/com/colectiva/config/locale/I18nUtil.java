package ar.com.colectiva.config.locale;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18nUtil {

    private final MessageSource messageSource;

    @Autowired
    public I18nUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Resource(name = "localeHolder")
    LocaleHolder localeHolder;

    public String getMessage(String code, String... args){
        return messageSource.getMessage(code, args, localeHolder.getCurrentLocale());
    }

}
