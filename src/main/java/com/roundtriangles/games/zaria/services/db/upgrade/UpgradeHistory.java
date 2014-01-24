package com.roundtriangles.games.zaria.services.db.upgrade;

import com.badlogic.gdx.utils.IntMap;

public class UpgradeHistory {
    public IntMap<DatabaseUpgrade> history = new IntMap<DatabaseUpgrade>();
    public int currentVersion;
}
