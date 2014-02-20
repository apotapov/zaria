package com.roundtriangles.games.zaria;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.roundtriangles.games.zaria.screen.AbstractScreen;
import com.roundtriangles.games.zaria.screen.LoadingScreen;
import com.roundtriangles.games.zaria.services.SoundService;

public abstract class AbstractGame<T extends AbstractGame<?>> extends Game {

    protected final String LOG_TAG = getClass().getSimpleName();

    public FPSLogger fpsLogger;
    public SoundService soundService;

    public abstract LoadingScreen<T> getLoadingScreen();
    public abstract AbstractScreen<T> getMainMenuScreen();
    public abstract FPSLogger createFPSLogger();
    public abstract SoundService createSoundService();


    public abstract void initialize();

    @Override
    public void create() {
        fpsLogger = createFPSLogger();
        soundService = createSoundService();

        initialize();

        AbstractScreen<T> loadingScreen = getLoadingScreen();
        if (loadingScreen != null) {
            setScreen(loadingScreen);
        } else {
            setScreen(getMainMenuScreen());
        }
    }

    @Override
    public void render() {
        super.render();
        if (fpsLogger != null) {
            fpsLogger.log();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
