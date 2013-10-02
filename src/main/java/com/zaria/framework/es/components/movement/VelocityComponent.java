package com.zaria.framework.es.components.movement;

import com.badlogic.gdx.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    private Vector2 velocity;

    public VelocityComponent() {
        velocity = new Vector2();
    }

    @Override
    public void reset() {
        setVelocity(0, 0);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    @Override
    public String toString() {
        return "Velocity" + velocity;
    }

}
