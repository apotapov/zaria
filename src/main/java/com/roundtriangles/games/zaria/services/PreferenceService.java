package com.roundtriangles.games.zaria.services;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Disposable;

public class PreferenceService implements Disposable {
    //private static final String LOG_TAG = PreferenceManager.class.getSimpleName();

    public interface PreferenceChangeListener {
        public void onPreferenceChange(String name, boolean value);
        public void onPreferenceChange(String name, int value);
        public void onPreferenceChange(String name, String value);
    }

    // constants
    public static final String PREF_MUSIC_ENABLED = "music.enabled";
    public static final String PREF_SOUND_ENABLED = "sound.enabled";
    public static final String PREF_VIBRATE_ENABLED = "vibrate.enabled";

    protected static final boolean DEFAULT_MUSIC_ENABLED = true;
    protected static final boolean DEFAULT_SOUND_ENABLED = true;
    protected static final boolean DEFAULT_VIBRATE_ENABLED = true;

    Preferences preferences;
    List<PreferenceChangeListener> listeners;

    public PreferenceService(String preferencesName) {
        this.preferences = Gdx.app.getPreferences(preferencesName);
        this.listeners = new ArrayList<PreferenceChangeListener>();
    }

    public PreferenceService(String preferencesName, PreferenceChangeListener...listeners) {
        this.preferences = Gdx.app.getPreferences(preferencesName);
        this.listeners = new ArrayList<PreferenceChangeListener>();
        for (PreferenceChangeListener listener : listeners) {
            registerListener(listener);
        }
        initialize();
    }

    public void initialize() {
        setSoundEnabled(getBoolean(PREF_SOUND_ENABLED, DEFAULT_SOUND_ENABLED));
        setMusicEnabled(getBoolean(PREF_MUSIC_ENABLED, DEFAULT_MUSIC_ENABLED));
        setMusicEnabled(getBoolean(PREF_VIBRATE_ENABLED, DEFAULT_VIBRATE_ENABLED));
    }

    public void registerListener(PreferenceChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(PreferenceChangeListener listener) {
        listeners.remove(listener);
    }

    public boolean isSoundEnabled() {
        return getBoolean(PREF_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
    }

    public void setSoundEnabled(boolean enabled) {
        setBoolean(PREF_SOUND_ENABLED, enabled);
    }

    public boolean isMusicEnabled() {
        return getBoolean(PREF_MUSIC_ENABLED, DEFAULT_MUSIC_ENABLED);
    }

    public void setMusicEnabled(boolean enabled) {
        setBoolean(PREF_MUSIC_ENABLED, enabled);
    }

    public boolean isVibrateEnabled() {
        return getBoolean(PREF_VIBRATE_ENABLED, DEFAULT_VIBRATE_ENABLED);
    }

    public void setVibrateEnabled(boolean enabled) {
        setBoolean(PREF_VIBRATE_ENABLED, enabled);
    }

    protected void updateListeners(String name, boolean value) {
        int len = listeners.size();
        for (int i = 0; i < len; i++) {
            listeners.get(i).onPreferenceChange(name, value);
        }
    }

    protected void updateListeners(String name, int value) {
        int len = listeners.size();
        for (int i = 0; i < len; i++) {
            listeners.get(i).onPreferenceChange(name, value);
        }
    }

    protected void updateListeners(String name, String value) {
        int len = listeners.size();
        for (int i = 0; i < len; i++) {
            listeners.get(i).onPreferenceChange(name, value);
        }
    }

    protected void setBoolean(String key, boolean value) {
        preferences.putBoolean(key, value);
        preferences.flush();
        updateListeners(key, value);
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    protected void setInteger(String key, int value) {
        preferences.putInteger(key, value);
        preferences.flush();
        updateListeners(key, value);
    }

    protected int getInteger(String key, int defaultValue) {
        return preferences.getInteger(key, defaultValue);
    }

    protected void setString(String key, String value) {
        preferences.putString(key, value);
        preferences.flush();
        updateListeners(key, value);
    }

    protected String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    @Override
    public void dispose() {
        preferences.flush();
    }
}
