package com.zaria.framework.es.components.movement;

import com.badlogic.gdx.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    private Vector2 position;
    private float angle;

    public PositionComponent() {
        position = new Vector2();
    }

    @Override
    public void reset() {
        setPosition(0, 0);
        setAngle(0);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "Position[position=" + position + ",angle=" + angle + "]";
    }
}
