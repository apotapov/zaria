package com.roundtriangles.games.zaria.services;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.roundtriangles.games.zaria.services.i18n.PropertyResourceBundleWithParent;

public class LocaleService extends ResourceBundleService {

    private static final String PREFIX = "i18n/messages_";

    Map<Locale, String> localeMap;
    Locale rootLocale;

    public LocaleService() {
        localeMap = new HashMap<Locale, String>();
    }

    public void load(Locale rootLocale, Locale...additionalLocales) {
        this.rootLocale = rootLocale;
        load(rootLocale);

        int len = additionalLocales.length;
        for (int i = 0; i < len; i++) {
            load(additionalLocales[i]);
        }
    }

    protected void load(Locale locale) {
        String propertyFile = PREFIX + rootLocale.getLanguage() + FILE_EXTENSION;
        load(propertyFile);
        localeMap.put(rootLocale, propertyFile);
    }

    @Override
    public String get(Object key) {
        return get(localeMap.get(Locale.getDefault()), key);
    }

    @Override
    public String get(Object key, Object...parameters) {
        return get(localeMap.get(Locale.getDefault()), key, parameters);
    }

    @Override
    public void onFinishLoading() {
        String rootPropertyFile = localeMap.get(rootLocale);
        if (rootPropertyFile != null) {
            PropertyResourceBundleWithParent rootResourceBundle =
                    assetManager.get(rootPropertyFile, PropertyResourceBundleWithParent.class);
            for (Map.Entry<Locale, String> entry : localeMap.entrySet()) {
                Locale locale = entry.getKey();
                if (!locale.equals(rootLocale)) {
                    String file = entry.getValue();
                    PropertyResourceBundleWithParent resourceBundle =
                            assetManager.get(file, PropertyResourceBundleWithParent.class);
                    resourceBundle.setParent(rootResourceBundle);
                }
            }
        }
    }

}
