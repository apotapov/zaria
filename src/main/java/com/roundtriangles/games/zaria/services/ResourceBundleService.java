package com.roundtriangles.games.zaria.services;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.roundtriangles.games.zaria.services.i18n.BundleLoader;
import com.roundtriangles.games.zaria.services.i18n.PropertyResourceBundleWithParent;

public abstract class ResourceBundleService {

    protected static final String FILE_EXTENSION = ".properties";

    protected AssetManager assetManager;

    public abstract String get(Object key);
    public abstract String get(Object key, Object...parameters);
    public abstract void onFinishLoading();

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        if (assetManager != null) {
            this.assetManager.setLoader(PropertyResourceBundleWithParent.class,
                    new BundleLoader(new InternalFileHandleResolver()));
        }
    }

    protected void load(String propertyFile) {
        this.assetManager.load(propertyFile, PropertyResourceBundleWithParent.class);
    }

    protected String get(String propertyFile, Object key) {
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

    protected String get(String propertyFile, Object key, Object...parameters) {
        String localizedString = get(propertyFile, key);
        if (localizedString != null) {
            return MessageFormat.format(localizedString, parameters);
        }
        return null;
    }
}
