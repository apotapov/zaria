package com.roundtriangles.games.zaria.es.systems.render;

import com.badlogic.gdx.Gdx;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderInitSystem extends VoidEntitySystem {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public RenderInitSystem(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(0f, 0f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

}
