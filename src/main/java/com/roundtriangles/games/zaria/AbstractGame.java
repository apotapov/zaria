package com.roundtriangles.games.zaria;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.roundtriangles.games.zaria.services.GraphicsService;
import com.roundtriangles.games.zaria.services.LocaleService;
import com.roundtriangles.games.zaria.services.PreferenceService;
import com.roundtriangles.games.zaria.services.SoundService;

@SuppressWarnings("rawtypes")
public abstract class AbstractGame<T extends AbstractGame> extends Game {

    protected final String LOG_TAG = getClass().getSimpleName();

    protected FPSLogger fpsLogger;
    protected PreferenceService preferenceService;
    protected SoundService soundService;
    protected GraphicsService graphicsService;
    protected LocaleService localeService;

    public abstract LoadingScreen<T> getLoadingScreen();
    public abstract AbstractScreen<T> getMainMenuScreen();
    public abstract void initialize();

    @Override
    public void create() {
        fpsLogger = new FPSLogger();
        soundService = new SoundService();
        graphicsService = new GraphicsService();
        preferenceService = new PreferenceService(getClass().getSimpleName(), soundService);
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
        Gdx.app.log(LOG_TAG, "Resizing to: " + width + "x" + height);
    }

    @Override
    public void pause() {
        Gdx.app.log(LOG_TAG, "Pause");
        soundService.pauseAll();
    }

    @Override
    public void resume() {
        Gdx.app.log(LOG_TAG, "Resume");
        soundService.play();
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "Dispose");
        preferenceService.dispose();
        soundService.dispose();
        graphicsService.dispose();
    }

    public PreferenceService getPreferenceService() {
        return preferenceService;
    }
    public SoundService getSoundService() {
        return soundService;
    }
    public GraphicsService getGraphicsService() {
        return graphicsService;
    }
    public LocaleService getLocaleService() {
        return localeService;
    }
}
