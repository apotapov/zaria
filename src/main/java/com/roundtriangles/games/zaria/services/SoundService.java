package com.roundtriangles.games.zaria.services;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.roundtriangles.games.zaria.services.PreferenceService.PreferenceChangeListener;

/**
 * A service that manages the sound effects.
 */
public class SoundService implements IAssetBasedService, PreferenceChangeListener {

    static class NamedMusic {
        Music music;
        String name;
    }

    boolean soundEnabled = true;
    boolean musicEnabled = true;
    boolean vibrateEnabled = true;

    AssetManager assetManager;
    List<String> soundEffects;
    List<String> backgroundMusic;

    NamedMusic currentMusic;

    public SoundService() {
        soundEffects = new ArrayList<String>();
        backgroundMusic = new ArrayList<String>();
        currentMusic = new NamedMusic();
    }

    @Override
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadSound(String...soundList) {
        int len = soundList.length;
        for (int i = 0; i < len; i++) {
            String sound = soundList[i];
            assetManager.load(sound, Sound.class);
            soundEffects.add(sound);
        }
    }

    public void loadMusic(String...soundList) {
        int len = soundList.length;
        for (int i = 0; i < len; i++) {
            String music = soundList[i];
            assetManager.load(music, Music.class);
            backgroundMusic.add(music);
        }
    }

    public void vibrate(int milliseconds) {
        if (vibrateEnabled) {
            Gdx.input.vibrate(milliseconds);
        }
    }

    /**
     * Plays the specified sound.
     */
    public void playSound(String name) {
        if (soundEnabled) {
            if (assetManager.isLoaded(name)) {
                assetManager.get(name, Sound.class).play();
            }
        }
    }

    public void playMusic(String name) {
        if (currentMusic.music == null || !currentMusic.name.equals(name)) {
            if (currentMusic.music != null) {
                currentMusic.music.stop();
            }
            if (assetManager.isLoaded(name)) {
                Music music = assetManager.get(name, Music.class);
                music.setLooping(true);
                if (musicEnabled) {
                    music.play();
                }
                currentMusic.music = music;
                currentMusic.name = name;
            }
        }
    }

    public void play() {
        if (musicEnabled && currentMusic.music != null) {
            currentMusic.music.play();
        }
    }

    public void pauseAll() {
        if (currentMusic.music != null) {
            currentMusic.music.pause();
        }
        int len = soundEffects.size();
        for (int i = 0; i < len; i++) {
            String sound = soundEffects.get(i);
            if (assetManager.isLoaded(sound)) {
                assetManager.get(sound, Sound.class).stop();
            }
        }
    }

    /**
     * Disposes the sound manager.
     */
    @Override
    public void dispose() {
        int len = soundEffects.size();
        for (int i = 0; i < len; i++) {
            String sound = soundEffects.get(i);
            if (assetManager.isLoaded(sound)) {
                assetManager.unload(sound);
            }
        }
        soundEffects.clear();
        len = backgroundMusic.size();
        for (int i = 0; i < len; i++) {
            String music = backgroundMusic.get(i);
            if (assetManager.isLoaded(music)) {
                assetManager.unload(music);
            }
        }
        backgroundMusic.clear();
    }

    @Override
    public void onPreferenceChange(String name, boolean value) {
        if (name.equals(PreferenceService.PREF_SOUND_ENABLED)) {
            soundEnabled = value;
        }
        if (name.equals(PreferenceService.PREF_MUSIC_ENABLED)) {
            musicEnabled = value;
            if (currentMusic.music != null) {
                if (musicEnabled) {
                    currentMusic.music.play();
                } else {
                    currentMusic.music.stop();
                }
            }
        }
        if (name.equals(PreferenceService.PREF_VIBRATE_ENABLED)) {
            vibrateEnabled = value;
        }
    }

    @Override
    public void onPreferenceChange(String name, int value) {
    }

    @Override
    public void onPreferenceChange(String name, String value) {
    }

    @Override
    public void onFinishLoading() {
    }
}
