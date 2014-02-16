package com.roundtriangles.games.zaria.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.roundtriangles.games.zaria.AbstractGame;

public abstract class AbstractScreen<T extends AbstractGame<?>> implements Screen {

    public final T game;
    public final Stage stage;
    public boolean paused;

    public AbstractScreen(T game) {
        this.game = game;
        stage = new Stage();
        paused = false;
    }

    public abstract void initialize();

    @Override
    public void render(float delta) {
        if (!paused) {
            // the following code clears the screen with the given RGB color (black)
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, true);
    }

    @Override
    public void show() {
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
