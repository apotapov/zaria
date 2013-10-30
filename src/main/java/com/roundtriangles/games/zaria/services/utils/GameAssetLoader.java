package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.roundtriangles.games.zaria.AbstractLevel;
import com.roundtriangles.games.zaria.services.GraphicsService;
import com.roundtriangles.games.zaria.services.LevelService;
import com.roundtriangles.games.zaria.services.LocaleService;
import com.roundtriangles.games.zaria.services.SoundService;

@SuppressWarnings("rawtypes")
public abstract class GameAssetLoader<T extends AbstractLevel> implements Disposable {

    protected AssetManager assetManager;
    protected SoundService soundService;
    protected LocaleService localeService;
    protected GraphicsService graphicsService;
    protected LevelService<T> levelService;

    public GameAssetLoader(SoundService soundService,
            GraphicsService graphicsService,
            LocaleService localeService,
            LevelService<T> levelService) {
        this.assetManager = new AssetManager();

        this.soundService = soundService;
        soundService.setAssetManager(assetManager);

        this.graphicsService = graphicsService;
        graphicsService.setAssetManager(assetManager);

        this.localeService = localeService;
        localeService.setAssetManager(assetManager);

        this.levelService = levelService;
        levelService.setAssetManager(assetManager);
    }

    protected abstract void loadAssets();

    public void load() {
        loadAssets();
        assetManager.finishLoading();
        onFinishLoading();
    }

    protected void onFinishLoading() {
        soundService.onFinishLoading();
        graphicsService.onFinishLoading();
        localeService.onFinishLoading();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
