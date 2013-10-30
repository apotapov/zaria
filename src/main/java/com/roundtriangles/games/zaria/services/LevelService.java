package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.roundtriangles.games.zaria.AbstractLevel;

@SuppressWarnings("rawtypes")
public abstract class LevelService<T extends AbstractLevel> implements Disposable {

    protected final Array<T> levels;
    protected final Class<T> levelClass;
    protected AssetManager assetManager;

    public abstract void load();
    public abstract AsynchronousAssetLoader<T, AssetLoaderParameters<T>> getLevelLoader();

    /**
     * Creates the level manager.
     */
    public LevelService(Class<T> levelClass) {
        levels = new Array<T>();
        this.levelClass = levelClass;
    }

    /**
     * Retrieve all the available levels.
     */
    public T getLevel(String name) {
        if (assetManager.isLoaded(name)) {
            return assetManager.get(name, levelClass);
        } else {
            throw new GdxRuntimeException("Could not find specified level: " + name);
        }
    }

    @Override
    public void dispose() {
        for (int i = 0; i < levels.size; i++) {
            levels.get(i).dispose();
        }
        levels.clear();
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        if (assetManager != null) {
            this.assetManager.setLoader(levelClass, getLevelLoader());
        }
    }
}
