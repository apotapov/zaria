package com.roundtriangles.games.zaria.services;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.roundtriangles.games.zaria.services.i18n.BundleLoader;
import com.roundtriangles.games.zaria.services.i18n.PropertyResourceBundleWithParent;

public class LocaleService {

    private static final String PREFIX = "i18n/messages_";
    private static final String SUFIX = ".properties";

    public static class LocaleAndFile {
        Locale locale;
        String fileName;
    }

    AssetManager assetManager;
    Map<Locale, String> localeMap;
    Locale rootLocale;

    public LocaleService(AssetManager assetManager) {
        setAssetManager(assetManager);
        localeMap = new HashMap<Locale, String>();
    }

    public LocaleService() {
        this(null);
    }


    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        if (assetManager != null) {
            this.assetManager.setLoader(PropertyResourceBundleWithParent.class,
                    new BundleLoader(new InternalFileHandleResolver()));
        }
    }

    public void load(Locale rootLocale, Locale...additionalLocales) {
        this.rootLocale = rootLocale;
        loadAndAddLocale(rootLocale);

        int len = additionalLocales.length;
        for (int i = 0; i < len; i++) {
            loadAndAddLocale(additionalLocales[i]);
        }
    }

    protected void loadAndAddLocale(Locale locale) {
        String propertyFile = PREFIX + rootLocale.getLanguage() + SUFIX;
        this.assetManager.load(propertyFile, PropertyResourceBundleWithParent.class);
        localeMap.put(rootLocale, propertyFile);
    }

    public String _(Object key) {
        String propertyFile = localeMap.get(Locale.getDefault());
        if (propertyFile != null) {
            PropertyResourceBundleWithParent resourceBundle =
                    assetManager.get(propertyFile, PropertyResourceBundleWithParent.class);
            if (resourceBundle != null) {
                try {
                    return resourceBundle.getString(key.toString());
                } catch (MissingResourceException e) {
                    return key.toString();
                }
            }
        }
        return null;
    }

    public String _(Object key, Object...parameters) {
        String localizedString = _(key);
        if (localizedString != null) {
            return MessageFormat.format(localizedString, parameters);
        }
        return null;
    }

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
