package com.roundtriangles.games.zaria;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.roundtriangles.games.zaria.screen.AbstractScreen;
import com.roundtriangles.games.zaria.services.SoundService;

public abstract class AbstractGame<T extends AbstractGame<?>> extends Game {

    public FPSLogger fpsLogger;
    public SoundService soundService;

    public abstract AbstractScreen<T> getFirstScreen();
    public abstract FPSLogger createFPSLogger();
    public abstract SoundService createSoundService();


    public abstract void initialize();

    @Override
    public void create() {
        fpsLogger = createFPSLogger();
        soundService = createSoundService();

        initialize();

        setScreen(getFirstScreen());
    }

    @Override
    public void render() {
        super.render();
        if (fpsLogger != null) {
            fpsLogger.log();
        }
    }
}
