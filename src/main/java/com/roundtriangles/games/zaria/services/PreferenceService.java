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
    }

    // constants
    public static final String PREF_MUSIC_ENABLED = "music.enabled";
    public static final String PREF_SOUND_ENABLED = "sound.enabled";

    private String preferencesName;
    private List<PreferenceChangeListener> listeners;

    public PreferenceService(String preferencesName) {
        this.preferencesName = preferencesName;
        listeners = new ArrayList<PreferenceChangeListener>();
    }

    public PreferenceService(String preferencesName, PreferenceChangeListener...listeners) {
        this.preferencesName = preferencesName;
        this.listeners = new ArrayList<PreferenceChangeListener>();
        for (PreferenceChangeListener listener : listeners) {
            registerListener(listener);
        }
        initialize();
    }

    public void initialize() {
        Preferences prefs = getPrefs();
        setSoundEnabled(prefs.getBoolean(PREF_SOUND_ENABLED, true));
        setMusicEnabled(prefs.getBoolean(PREF_MUSIC_ENABLED, true));
    }

    public void registerListener(PreferenceChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(PreferenceChangeListener listener) {
        listeners.remove(listener);
    }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(preferencesName);
    }

    public boolean isSoundEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
        updateListeners(PREF_SOUND_ENABLED, soundEffectsEnabled);
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
        updateListeners(PREF_MUSIC_ENABLED, musicEnabled);
    }

    protected void updateListeners(String name, boolean value) {
        int len = listeners.size();
        for (int i = 0; i < len; i++) {
            listeners.get(i).onPreferenceChange(name, value);
        }
    }

    @Override
    public void dispose() {
        getPrefs().flush();
    }
}
