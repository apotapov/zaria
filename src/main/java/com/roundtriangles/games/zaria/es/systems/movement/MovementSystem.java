package com.roundtriangles.games.zaria.es.systems.movement;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.roundtriangles.games.zaria.es.components.movement.PositionComponent;
import com.roundtriangles.games.zaria.es.components.movement.VelocityComponent;

public class MovementSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<VelocityComponent> velocityMapper;

    @SuppressWarnings("unchecked")
    public MovementSystem() {
        super(Aspect.getAspectForAll(PositionComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        VelocityComponent velocity = velocityMapper.get(e);

        position.getPosition().x += velocity.getVelocity().x * world.getDelta();
        position.getPosition().y += velocity.getVelocity().y * world.getDelta();

        position.setAngle(velocity.getVelocity().angle() - 90);
    }

}
