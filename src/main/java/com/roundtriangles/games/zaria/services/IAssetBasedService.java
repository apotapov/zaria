package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public interface IAssetBasedService extends Disposable {

    public void setAssetManager(AssetManager assetManager);
    public void onFinishLoading();
}
