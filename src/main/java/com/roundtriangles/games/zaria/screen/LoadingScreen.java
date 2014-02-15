package com.roundtriangles.games.zaria.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.roundtriangles.games.zaria.AbstractGame;
import com.roundtriangles.games.zaria.services.utils.GameAssetLoader;

@SuppressWarnings("rawtypes")
public class LoadingScreen<T extends AbstractGame> extends AbstractScreen<T> {

    GameAssetLoader assetLoader;
    Texture splashTexture;
    float displayTime;
    float fadeTime;

    public LoadingScreen(final T game,
            GameAssetLoader assetLoader,
            String splashImage,
            float displayTime,
            float fadeTime) {
        super(game);

        this.assetLoader = assetLoader;
        this.splashTexture = new Texture(splashImage);
        this.displayTime = displayTime;
        this.fadeTime = fadeTime;
    }

    public LoadingScreen(final T game,
            GameAssetLoader assetLoader,
            String splashAtlas,
            String splashName,
            float displayTime,
            float fadeTime) {
        super(game);

        this.assetLoader = assetLoader;
        TextureAtlas atlas = new TextureAtlas(splashAtlas);
        this.splashTexture = atlas.findRegion(splashName).getTexture();
        this.displayTime = displayTime;
        this.fadeTime = fadeTime;
    }

    @Override
    public void resume() {
        super.resume();
        splashTexture.bind();
    }

    @Override
    public void dispose() {
        super.dispose();
        splashTexture.dispose();
    }

    @Override
    public void initialize() {
        // in the image atlas, our splash image begins at (0,0) of the
        // upper-left corner and has a dimension of 512x301
        TextureRegion splashRegion = new TextureRegion(splashTexture);

        // here we create the splash image actor and set its size
        Image splashImage = new Image(splashRegion);
        splashImage.setWidth(Gdx.graphics.getWidth());
        splashImage.setHeight(Gdx.graphics.getHeight());
        splashImage.getColor().a = 0;

        RunnableAction loadingAction = new RunnableAction();
        loadingAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                assetLoader.load();
            }
        });

        Action switchScreenAction = new Action() {
            @Override
            public boolean act(float delta) {
                game.setScreen(game.getMainMenuScreen());
                return true;
            }
        };

        // configure the fade-in/out effect on the splash image
        SequenceAction actions = Actions.sequence(
                Actions.fadeIn(fadeTime),
                loadingAction,
                Actions.delay(displayTime),
                Actions.fadeOut(fadeTime),
                switchScreenAction);

        splashImage.addAction(actions);
        stage.addActor(splashImage);
    }
}
