package com.zaria.framework.es.components.render;

import com.badlogic.gdx.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DrawableComponent implements Component {
    private Sprite sprite;
    private String name;

    public DrawableComponent() {
    }

    @Override
    public void reset() {
        name = null;
        sprite = null;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sprite[name=" + name + "]";
    }
}
