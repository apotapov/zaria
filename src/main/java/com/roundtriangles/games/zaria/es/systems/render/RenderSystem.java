package com.roundtriangles.games.zaria.es.systems.render;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.roundtriangles.games.zaria.es.components.movement.PositionComponent;
import com.roundtriangles.games.zaria.es.components.render.DrawableComponent;

public class RenderSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<DrawableComponent> drawableMapper;
    SpriteBatch spriteBatch;

    @SuppressWarnings("unchecked")
    public RenderSystem(SpriteBatch spriteBatch) {
        super(Aspect.getAspectForAll(DrawableComponent.class, PositionComponent.class));
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void begin() {
        spriteBatch.begin();
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        DrawableComponent drawable = drawableMapper.get(e);

        Sprite sprite = drawable.getSprite();
        Vector2 p = position.getPosition();

        float width2 = sprite.getWidth() / 2;
        float height2 = sprite.getHeight() / 2;

        spriteBatch.draw(
                sprite,
                p.x - width2,
                p.y - height2,
                width2,
                height2,
                sprite.getWidth(),
                sprite.getHeight(),
                1,
                1,
                position.getAngle() - 90);

    }

}
