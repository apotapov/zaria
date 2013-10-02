package com.zaria.framework.es.components.utils;

import com.badlogic.gdx.artemis.Component;
import com.badlogic.gdx.artemis.Entity;
import com.badlogic.gdx.artemis.utils.SafeArray;

public class DebugComponent implements Component {
    private SafeArray<Component> entityComponents;
    private Entity entity;

    public DebugComponent() {
        entityComponents = new SafeArray<Component>();
    }

    @Override
    public void reset() {
        setEntity(null);
        entityComponents.clear();
    }

    public SafeArray<Component> getEntityComponents() {
        return entityComponents;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entity: ");
        sb.append(entity);
        sb.append(" (");
        for (int i = 0; i < entityComponents.size; i++) {
            sb.append(entityComponents.get(i));
            sb.append(";");
        }
        sb.append(")");
        return sb.toString();
    }
}