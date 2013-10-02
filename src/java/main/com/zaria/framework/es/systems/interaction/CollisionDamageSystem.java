package com.zaria.framework.es.systems.interaction;

import com.badlogic.gdx.artemis.Aspect;
import com.badlogic.gdx.artemis.ComponentMapper;
import com.badlogic.gdx.artemis.Entity;
import com.badlogic.gdx.artemis.annotations.Mapper;
import com.badlogic.gdx.artemis.managers.GroupManager;
import com.badlogic.gdx.artemis.systems.EntitySystem;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.zaria.framework.es.components.interaction.BoundsComponent;
import com.zaria.framework.es.components.interaction.DamageComponent;
import com.zaria.framework.es.components.interaction.HealthComponent;
import com.zaria.framework.es.components.movement.PositionComponent;

public class CollisionDamageSystem extends EntitySystem {

    @Mapper
    ComponentMapper<PositionComponent> pm;
    @Mapper
    ComponentMapper<BoundsComponent> bm;
    @Mapper
    ComponentMapper<HealthComponent> hm;
    @Mapper
    ComponentMapper<DamageComponent> dm;

    private String damagers;
    private String damaged;

    @SuppressWarnings("unchecked")
    public CollisionDamageSystem(String damagers, String damaged) {
        super(Aspect.getAspectForAll(PositionComponent.class, BoundsComponent.class)
                .one(DamageComponent.class, HealthComponent.class));
        this.damagers = damagers;
        this.damaged = damaged;
    }

    @Override
    protected void processEntities(Array<Entity> entities) {
        GroupManager manager = world.getManager(GroupManager.class);

        for (int i = 0; i < entities.size; i++) {
            Entity e1 = entities.get(i);
            if (manager.isInGroup(e1, damagers)) {
                for (int j = 0; j < entities.size; j++) {
                    Entity e2 = entities.get(j);
                    if (manager.isInGroup(e2, damaged) && collide(e1, e2)) {
                        applyDamage(e1, e2);
                    }
                }
            }
        }
    }

    private boolean collide(Entity e1, Entity e2) {
        PositionComponent p1 = pm.get(e1);
        BoundsComponent b1 = bm.get(e1);
        b1.setPosition(p1.getPosition());

        PositionComponent p2 = pm.get(e2);
        BoundsComponent b2 = bm.get(e2);
        b2.setPosition(p2.getPosition());

        return Intersector.overlaps(b1.getBounds(), b2.getBounds());
    }

    private void applyDamage(Entity damager, Entity damaged) {
        DamageComponent damage = dm.get(damager);
        HealthComponent health = hm.get(damaged);
        if (damage != null && health != null) {
            health.setHealth(health.getHealth() - damage.getDamage());
            if (damage.isDieOnDamage()) {
                damager.deleteFromWorld();
            }

            if (health.getHealth() <= 0) {
                damaged.deleteFromWorld();
            }
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }
}
