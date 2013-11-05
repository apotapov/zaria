package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.OrderedMap;
import com.roundtriangles.games.zaria.AbstractLevel;

@SuppressWarnings("rawtypes")
public abstract class LevelService<T extends AbstractLevel> implements Disposable {

    protected OrderedMap<String, T> levels;
    protected final String[] levelFiles;
    protected final Class<T> levelClass;
    protected AssetManager assetManager;

    public abstract AsynchronousAssetLoader<T, AssetLoaderParameters<T>> getLevelLoader();

    /**
     * Creates the level manager.
     */
    public LevelService(Class<T> levelClass, String[] levelFiles) {
        this.levelClass = levelClass;
        this.levelFiles = levelFiles;
    }

    public void load() {
        for (String levelFile : levelFiles) {
            assetManager.load(levelFile, levelClass);
        }
    }

    @SuppressWarnings("unchecked")
    public OrderedMap<String, T> getLevels() {
        if (levels == null) {
            levels = new OrderedMap<String, T>();
            T previousLevel = null;
            for (String levelFile : levelFiles) {
                T level = assetManager.get(levelFile, levelClass);
                if (previousLevel == null) {
                    previousLevel = level;
                } else {
                    previousLevel.nextLevel = level;
                }
                levels.put(level.name, level);
            }
        }
        return levels;
    }

    public T getLevel(String name) {
        return getLevels().get(name);
    }

    @Override
    public void dispose() {
        for (T level : levels.values()) {
            level.dispose();
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
