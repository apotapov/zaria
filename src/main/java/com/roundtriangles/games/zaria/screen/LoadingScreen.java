package com.roundtriangles.games.zaria;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.roundtriangles.games.zaria.services.utils.GameAssetLoader;

@SuppressWarnings("rawtypes")
public class LoadingScreen<T extends AbstractGame> extends AbstractScreen<T> {

    private static final float FADE_TIME = 0.75f;
    private static final float DISPLAY_TIME = 1.75f;

    private Texture splashTexture;

    public LoadingScreen(final T game, final GameAssetLoader assetLoader,
            String splashImageFile, int width, int height) {
        super(game);

        splashTexture = new Texture(splashImageFile);

        // in the image atlas, our splash image begins at (0,0) of the
        // upper-left corner and has a dimension of 512x301
        TextureRegion splashRegion =
                new TextureRegion(splashTexture, 0, 0, width, height);

        // here we create the splash image actor and set its size
        Image splashImage = new Image(splashRegion);
        splashImage.setWidth(stage.getWidth());
        splashImage.setHeight(stage.getHeight());
        splashImage.getColor().a = 0;

        // configure the fade-in/out effect on the splash image
        SequenceAction actions = Actions.sequence(
                Actions.fadeIn(FADE_TIME),
                Actions.delay(DISPLAY_TIME),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        if (assetLoader != null) {
                            //TODO implement progress
                            assetLoader.load();
                        }
                        return true;
                    }
                },
                Actions.fadeOut(FADE_TIME),
                new RunnableAction() {
                    @Override
                    public void run() {
                        game.setScreen(game.getMainMenuScreen());
                    }
                });

        splashImage.addAction(actions);
        stage.addActor(splashImage);
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
}
