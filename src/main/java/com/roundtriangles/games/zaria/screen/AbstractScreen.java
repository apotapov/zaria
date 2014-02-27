package com.roundtriangles.games.zaria.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.roundtriangles.games.zaria.AbstractGame;

public abstract class AbstractScreen<T extends AbstractGame<?>> implements Screen {

    public final T game;
    public AbstractScreen<T> parentScreen;

    protected Image backgroundImage;
    protected float fadeTime;

    public final Stage stage;
    public boolean paused;

    public AbstractScreen(T game, float fade) {
        this(game, null, null, fade);
    }

    public AbstractScreen(T game, AbstractScreen<T> parentScreen, float fade) {
        this(game, parentScreen, null, fade);
    }

    public AbstractScreen(T game, Image backgroundImage, float fade) {
        this(game, null, backgroundImage, fade);
    }

    public AbstractScreen(T game, AbstractScreen<T> parentScreen, Image backgroundImage, float fade) {
        this.game = game;
        this.parentScreen = parentScreen;
        stage = new Stage();
        paused = false;

        setBackgroundImage(backgroundImage);
        this.fadeTime = fade;
    }

    public abstract void initialize();

    public void setBackgroundImage(Image backgroundImage) {
        if (this.backgroundImage != null) {
            backgroundImage.remove();
        }
        this.backgroundImage = backgroundImage;
        if (backgroundImage != null) {
            stage.addActor(backgroundImage);
        }
    }

    public void switchScreen(Screen screen) {
        if (fadeTime > 0) {
            if (backgroundImage != null) {
                backgroundImage.addAction(Actions.fadeOut(fadeTime));
            }
        }
        game.setScreen(screen);
    }

    public boolean back() {
        if (parentScreen != null) {
            switchScreen(parentScreen);
            return true;
        }
        return false;
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            // the following code clears the screen with the given RGB color (black)
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(delta);
            stage.draw();

            Table.drawDebug(stage);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, true);
        if (backgroundImage != null) {
            float ratioDifference = (backgroundImage.getHeight() / backgroundImage.getWidth()) / (((float) height) / width);

            float imageWidth = width;
            float imageHeight = height * ratioDifference;

            backgroundImage.setWidth(imageWidth);
            backgroundImage.setHeight(imageHeight);
            backgroundImage.setPosition(0, (height - imageHeight) / 2);
        }
    }

    @Override
    public void show() {
        if (backgroundImage != null && fadeTime > 0) {
            backgroundImage.getColor().a = 0;
            backgroundImage.addAction(Actions.fadeIn(fadeTime));
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        paused = true;
        game.soundService.pauseAll();
    }

    @Override
    public void resume() {
        paused = false;
        game.soundService.play();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
