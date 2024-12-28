package ar.com.colectiva.config.locale;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("localeHolder")
public class LocaleHolder {
    private Locale currentLocale;

    public LocaleHolder() {
    }

    public LocaleHolder(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    @Override
    public String toString() {
        return "LocaleHolder{" +
                "currentLocale=" + currentLocale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocaleHolder that = (LocaleHolder) o;

        return currentLocale != null ? currentLocale.equals(that.currentLocale) : that.currentLocale == null;
    }

    @Override
    public int hashCode() {
        return currentLocale != null ? currentLocale.hashCode() : 0;
    }
}
