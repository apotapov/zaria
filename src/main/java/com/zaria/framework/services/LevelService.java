package com.zaria.framework.services;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.zaria.framework.AbstractLevel;

@SuppressWarnings("rawtypes")
public class LevelService<T extends AbstractLevel> implements Disposable {

    private final Array<T> levels;

    /**
     * Creates the level manager.
     */
    public LevelService() {
        levels = new Array<T>();
    }

    public void addLevel(T level) {
        levels.add(level);
    }

    /**
     * Retrieve all the available levels.
     */
    public Array<T> getLevels() {
        return levels;
    }

    /**
     * Retrieve the level with the given id, or <code>null</code> if no such
     * level exist.
     */
    public T findLevelById(int id) {
        if (id < 0 || id >= levels.size) {
            return null;
        }
        return levels.get(id);
    }

    @Override
    public void dispose() {
        for (int i = 0; i < levels.size; i++) {
            levels.get(i).dispose();
        }
        levels.clear();
    }
}
