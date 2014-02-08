package com.roundtriangles.games.zaria.services.db;

import com.badlogic.gdx.utils.Array;
import com.roundtriangles.games.zaria.services.db.upgrade.UpgradeHistory;

public class DatabaseServiceConfig {

    //    public ConnectionSource connectionSource;

    public UpgradeHistory upgradeHistory = new UpgradeHistory();
    public Array<Class<? extends ITable>> tables = new Array<Class<? extends ITable>>();
}
