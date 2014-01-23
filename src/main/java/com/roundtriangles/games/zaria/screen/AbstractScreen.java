package com.roundtriangles.games.zaria;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AbstractScreen<T> implements Screen {

    protected final T game;
    protected final Stage stage;
    protected boolean paused;

    public AbstractScreen(T game) {
        this.game = game;
        stage = new Stage();
        paused = false;
    }

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
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
