package com.roundtriangles.games.zaria.es.components.interaction;

import com.artemis.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class BoundsComponent implements Component {
    private Circle bounds;

    public BoundsComponent() {
        bounds = new Circle();
    }

    public void setBounds(float x, float y, float radius) {
        bounds.set(x, y, radius);
    }

    public void setBounds(Vector2 position, float radius) {
        bounds.setPosition(position);
        bounds.setRadius(radius);
    }

    public void setPosition(Vector2 position) {
        bounds.setPosition(position);
    }

    public Circle getBounds() {
        return bounds;
    }

    @Override
    public void reset() {
        //bounds.set(0, 0, 0);
    }

    @Override
    public String toString() {
        return "Bounds[" + bounds + "]";
    }
}
