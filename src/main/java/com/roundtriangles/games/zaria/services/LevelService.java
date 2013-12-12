package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.OrderedMap;
import com.roundtriangles.games.zaria.AbstractLevel;
import com.roundtriangles.games.zaria.services.utils.Levels;
import com.roundtriangles.games.zaria.services.utils.Levels.LevelsAssetLoader;

@SuppressWarnings("rawtypes")
public abstract class LevelService<T extends AbstractLevel> implements Disposable, LoadedCallback {

    protected OrderedMap<String, T> levelMap;
    protected final String levelDefinitionFile;
    protected final Class<T> levelClass;
    protected Levels levels;
    protected AssetManager assetManager;

    public abstract AsynchronousAssetLoader<T, AssetLoaderParameters<T>> getLevelLoader();

    /**
     * Creates the level manager.
     */
    public LevelService(Class<T> levelClass, String levelDefinitionFile) {
        this.levelClass = levelClass;
        this.levelDefinitionFile = levelDefinitionFile;
    }

    public void load() {
        AssetLoaderParameters<Levels> parameter = new AssetLoaderParameters<Levels>();
        parameter.loadedCallback = this;
        assetManager.load(levelDefinitionFile, Levels.class, parameter);
    }

    @Override
    public void finishedLoading (AssetManager assetManager, String fileName, Class type) {
        levels = assetManager.get(levelDefinitionFile, Levels.class);

        for (String levelFile : levels.levels) {
            assetManager.load(levelFile, levelClass);
        }
    }

    @SuppressWarnings("unchecked")
    public OrderedMap<String, T> getLevels() {
        if (levelMap == null) {
            levelMap = new OrderedMap<String, T>();
            T previousLevel = null;
            for (String levelFile : levels.levels) {
                T level = assetManager.get(levelFile, levelClass);
                if (previousLevel == null) {
                    previousLevel = level;
                } else {
                    previousLevel.nextLevel = level;
                }
                levelMap.put(level.name, level);
            }
        }
        return levelMap;
    }

    public T getLevel(String name) {
        return getLevels().get(name);
    }

    @Override
    public void dispose() {
        for (T level : levelMap.values()) {
            level.dispose();
        }
        levelMap.clear();
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        if (assetManager != null) {
            this.assetManager.setLoader(levelClass, getLevelLoader());
            this.assetManager.setLoader(Levels.class, new LevelsAssetLoader());
        }
    }
}
