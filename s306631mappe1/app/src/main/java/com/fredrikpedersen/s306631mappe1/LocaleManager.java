package com.fredrikpedersen.s306631mappe1;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LocaleManager {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({NORWEGIAN, GERMAN})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = { NORWEGIAN, GERMAN};
    }

    static final String NORWEGIAN = "nb";
    static final String GERMAN = "de";
}
