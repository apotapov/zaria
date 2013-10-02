package com.zaria.framework.services;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.zaria.framework.services.PreferenceService.PreferenceChangeListener;

/**
 * A service that manages the sound effects.
 */
public class SoundService implements Disposable, PreferenceChangeListener {

    private static class NamedMusic {
        Music music;
        String name;
    }

    private static final String LOG_TAG = SoundService.class.getSimpleName();

    private boolean soundEnabled = true;
    private boolean musicEnabled = true;

    private AssetManager assetManager;
    private List<String> soundEffects;
    private List<String> backgroundMusic;

    private NamedMusic currentMusic;

    public SoundService(AssetManager assetManager) {
        this.assetManager = assetManager;
        soundEffects = new ArrayList<String>();
        backgroundMusic = new ArrayList<String>();
        currentMusic = new NamedMusic();
    }

    public SoundService() {
        this(null);
    }

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

    /**
     * Plays the specified sound.
     */
    public void playSound(String name) {
        if (soundEnabled) {
            if (assetManager.isLoaded(name)) {
                Sound soundToPlay = assetManager.get(name, Sound.class);
                soundToPlay.play();
            } else {
                Gdx.app.log(LOG_TAG, "Could not find specified sound: " + name);
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
            } else {
                Gdx.app.log(LOG_TAG, "Could not find specified music: " + name);
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
        Gdx.app.log(LOG_TAG, "Disposing sound manager");
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
    }

    public void onFinishLoading() {
    }
}
