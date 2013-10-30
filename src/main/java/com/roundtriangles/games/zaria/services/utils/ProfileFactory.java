package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.utils.Json.Serializer;


public interface ProfileFactory<T> extends Serializer<T>{
    public T newProfile();
    public Class<T> getProfileClass();
}
