package com.roundtriangles.games.zaria.screen;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.roundtriangles.games.zaria.AbstractGame;
import com.roundtriangles.games.zaria.services.utils.GameAssetLoader;

public class LoadingScreen<T extends AbstractGame<?>> extends AbstractScreen<T> {

    protected GameAssetLoader assetLoader;
    protected AbstractScreen<T> nextScreen;
    protected float displayTime;

    public LoadingScreen(final T game,
            GameAssetLoader assetLoader,
            AbstractScreen<T> nextScreen,
            Image backgroundImage,
            float displayTime,
            float fadeTime) {
        super(game, backgroundImage, true, fadeTime);

        this.assetLoader = assetLoader;
        this.nextScreen = nextScreen;
        this.displayTime = displayTime;
    }

    @Override
    public void initialize() {
        RunnableAction loadingAction = new RunnableAction();
        loadingAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                if (assetLoader != null) {
                    assetLoader.load();
                }
            }
        });

        Action switchScreenAction = new Action() {
            @Override
            public boolean act(float delta) {
                switchScreen(nextScreen);
                return true;
            }
        };

        // configure the fade-in/out effect on the splash image
        SequenceAction actions = Actions.sequence(
                loadingAction,
                Actions.delay(displayTime),
                switchScreenAction);

        backgroundImage.addAction(actions);
    }
}
