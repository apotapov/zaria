package com.roundtriangles.games.zaria.services.i18n;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class BundleLoader extends AsynchronousAssetLoader<PropertyResourceBundleWithParent, AssetLoaderParameters<PropertyResourceBundleWithParent>> {

    public BundleLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName,
            FileHandle file, AssetLoaderParameters<PropertyResourceBundleWithParent> parameter) {
        // no dependencies
        return null;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName,
            FileHandle file, AssetLoaderParameters<PropertyResourceBundleWithParent> parameter) {
    }

    @Override
    public PropertyResourceBundleWithParent loadSync(AssetManager manager, String fileName,
            FileHandle file, AssetLoaderParameters<PropertyResourceBundleWithParent> parameter) {
        try {
            return new PropertyResourceBundleWithParent(file.read());
        } catch (Exception e) {
            throw new GdxRuntimeException("Error loading resource bundle: " + fileName, e);
        }
    }

}
