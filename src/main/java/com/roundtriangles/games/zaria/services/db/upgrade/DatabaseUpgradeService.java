package com.roundtriangles.games.zaria.services.db.upgrade;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.roundtriangles.games.zaria.services.IAssetBasedService;
import com.roundtriangles.games.zaria.services.db.DatabaseServiceConfig;

public class DatabaseUpgradeService implements IAssetBasedService {

    AssetManager assetManager;
    DatabaseServiceConfig config;

    public DatabaseUpgradeService(DatabaseServiceConfig config) {
        this.config = config;
    }

    public void loadUpgradeData() {
        try {
            //            for (Class<? extends ITable> table : config.tables) {
            //                TableUtils.createTableIfNotExists(config.connectionSource, table);
            //            }
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public void upgradeDb(int oldVersion, int newVersion) {
        try {
            for (int i = oldVersion + 1; i <= newVersion; i++) {
                DatabaseUpgrade updgrade = config.upgradeHistory.history.get(i);
                if (updgrade != null) {
                    runUpgrade(updgrade);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void runUpgrade(DatabaseUpgrade updgrade) {
        // here we try inserting data in the on-create as a test
        //        Dao dao = updgrade.daoWrapper.getDao();
        //
        //        dao.updateRaw(statement, arguments)
        //
        //
        //        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        //        String line;
        //        while ((line = reader.readLine()) != null) {
        //
        //            dao.updateRaw(line);
        //        }
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        if (assetManager != null) {
            this.assetManager.setLoader(DatabaseUpgrade.class,
                    new DatabaseUpgradeLoader(new InternalFileHandleResolver()));
        }
    }

    @Override
    public void onFinishLoading() {
        // TODO Auto-generated method stub

    }

}
