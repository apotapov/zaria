package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.roundtriangles.games.zaria.AbstractLevel;
import com.roundtriangles.games.zaria.services.IAssetBasedService;

@SuppressWarnings("rawtypes")
public abstract class GameAssetLoader<T extends AbstractLevel> implements Disposable {

    protected AssetManager assetManager;
    protected Array<IAssetBasedService> services;

    public GameAssetLoader(IAssetBasedService...services) {
        this.assetManager = new AssetManager();
        this.services = new Array<IAssetBasedService>();

        for (IAssetBasedService service : services) {
            service.setAssetManager(assetManager);
            this.services.add(service);
        }
    }

    protected abstract void loadAssets();

    public void load() {
        loadAssets();
        assetManager.finishLoading();
        onFinishLoading();
    }

    protected void onFinishLoading() {
        for (IAssetBasedService service : services) {
            service.onFinishLoading();
        }
    }

    @Override
    public void dispose() {
        for (IAssetBasedService service : services) {
            service.dispose();
        }
    }
}
