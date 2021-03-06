package com.fredrikpedersen.s306631mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

//Code taken from https://androidwave.com/android-multi-language-support-best-practices/, with slight alterations

public class LocaleManager {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ NORWEGIAN, GERMAN })
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = { NORWEGIAN, GERMAN };
    }

    public static final String NORWEGIAN = "nb";
    public static final String GERMAN = "de";
    private static final String LANGUAGE_KEY = "language_key";

    //Set current pref locale
    public static Context setLocale(Context context) {
        return updateResources(context, getLanguagePref(context));
    }

    //Set new Locale with context
    public static void setNewLocale(Context context, @LocaleDef String language) {
        setLanguagePref(context, language);
        updateResources(context, language);
    }

    //Get saved Locale from SharedPreferences. Returns Norwegian locale by default.
    private static String getLanguagePref(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LANGUAGE_KEY, NORWEGIAN);
    }


    //Set preference key
    private static void setLanguagePref(Context context, String localeKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(LANGUAGE_KEY, localeKey).apply();
    }

    //Update resource
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }

    //Get current Locale
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}
