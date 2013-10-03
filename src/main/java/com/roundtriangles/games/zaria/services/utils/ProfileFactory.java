package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.utils.Json.Serializable;

public interface ProfileFactory<T extends Serializable> {
    public T newProfile();
    public Class<T> getProfileClass();
}
