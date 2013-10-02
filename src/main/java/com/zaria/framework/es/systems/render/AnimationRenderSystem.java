package com.zaria.framework.es.systems.render;

import com.badlogic.gdx.artemis.Aspect;
import com.badlogic.gdx.artemis.ComponentMapper;
import com.badlogic.gdx.artemis.Entity;
import com.badlogic.gdx.artemis.annotations.Mapper;
import com.badlogic.gdx.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.zaria.framework.es.components.movement.PositionComponent;
import com.zaria.framework.es.components.render.AnimationComponent;

public class AnimationRenderSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<AnimationComponent> animationMapper;
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;

    SpriteBatch spriteBatch;

    @SuppressWarnings("unchecked")
    public AnimationRenderSystem(SpriteBatch spriteBatch) {
        super(Aspect.getAspectForAll(AnimationComponent.class, PositionComponent.class));
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
        AnimationComponent animationComponent = animationMapper.get(e);

        animationComponent.incrementStateTime(world.getDelta());
        TextureRegion region = animationComponent.getFrame();
        Vector2 p = position.getPosition();

        int width2 = region.getRegionWidth() / 2;
        int height2 = region.getRegionHeight() / 2;

        spriteBatch.draw(
                region,
                p.x - width2 / 2,
                p.y - height2 / 2,
                width2,
                height2,
                width2,
                height2,
                1,
                1,
                position.getAngle());

        if (animationComponent.isFinished()) {
            e.deleteFromWorld();
        }
    }

}
