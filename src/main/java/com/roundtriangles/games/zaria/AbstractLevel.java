package com.roundtriangles.games.zaria;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json.Serializable;

public abstract class AbstractLevel<T> implements Serializable, Disposable {
    private final int id;
    private final String name;
    private boolean completed;
    private T nextLevel;

    public AbstractLevel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void load();

    /**
     * Retrieves the ID of this object.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the level's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves whether this level is completed.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the level status.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Retrieves the next level.
     */
    public T getNextLevel() {
        return nextLevel;
    }

    /**
     * Sets the next level.
     */
    public void setNextLevel(T nextLevel) {
        this.nextLevel = nextLevel;
    }

    /**
     * Retrieves whether there is a next level.
     */
    public boolean hasNextLevel() {
        return (nextLevel != null);
    }
}
