package com.roundtriangles.games.zaria.services.db;

import com.badlogic.gdx.assets.AssetManager;
import com.roundtriangles.games.zaria.services.IAssetBasedService;
import com.roundtriangles.games.zaria.services.db.upgrade.DatabaseUpgradeService;


public abstract class DatabaseService implements IAssetBasedService {

    protected DatabaseUpgradeService upgradeService;
    protected DatabaseServiceConfig config;

    protected abstract void createTables();
    protected abstract void loadData();

    public void load() {
        createTables();
        upgradeService.loadUpgradeData();
        loadData();
    }

    @Override
    public void setAssetManager(AssetManager assetManager) {
        upgradeService.setAssetManager(assetManager);
    }

    @Override
    public void onFinishLoading() {
        upgradeService.onFinishLoading();
    }

    @Override
    public void dispose() {
        upgradeService.dispose();
    }
}
