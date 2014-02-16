package com.roundtriangles.games.zaria;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.roundtriangles.games.zaria.screen.AbstractScreen;
import com.roundtriangles.games.zaria.screen.LoadingScreen;
import com.roundtriangles.games.zaria.services.GraphicsService;
import com.roundtriangles.games.zaria.services.SoundService;
import com.roundtriangles.games.zaria.services.resources.LocaleService;

public abstract class AbstractGame<T extends AbstractGame<?>> extends Game {

    protected final String LOG_TAG = getClass().getSimpleName();

    public FPSLogger fpsLogger;
    public SoundService soundService;
    public GraphicsService graphicsService;
    public LocaleService localeService;

    public abstract LoadingScreen<T> getLoadingScreen();
    public abstract AbstractScreen<T> getMainMenuScreen();
    public abstract void initialize();

    @Override
    public void create() {
        fpsLogger = new FPSLogger();
        soundService = new SoundService();
        graphicsService = new GraphicsService();
        localeService = new LocaleService();

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
        Gdx.app.log(LOG_TAG, "Resizing to: " + width + "x" + height);
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log(LOG_TAG, "Pause");
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.log(LOG_TAG, "Resume");
    }
}
