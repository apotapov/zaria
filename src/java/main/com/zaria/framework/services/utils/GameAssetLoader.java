package com.zaria.framework.services.utils;

import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.zaria.framework.services.GraphicsService;
import com.zaria.framework.services.LocaleService;
import com.zaria.framework.services.SoundService;

public abstract class GameAssetLoader implements Disposable {

    protected AssetManager assetManager;
    protected SoundService soundService;
    protected LocaleService localeService;
    protected GraphicsService graphicsService;

    public GameAssetLoader(SoundService soundManager,
            GraphicsService graphicsManager,
            LocaleService localeManager) {
        this.assetManager = new AssetManager();

        this.soundService = soundManager;
        soundManager.setAssetManager(assetManager);

        this.graphicsService = graphicsManager;
        graphicsManager.setAssetManager(assetManager);
        graphicsService.setAnimationDuration(getAnimationDurations());

        this.localeService = localeManager;
        localeManager.setAssetManager(assetManager);
    }

    protected abstract void loadAssets();
    protected abstract Map<String, Float> getAnimationDurations();

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
