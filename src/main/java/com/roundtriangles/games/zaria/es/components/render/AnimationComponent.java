package com.roundtriangles.games.zaria.es.components.render;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {

    private Animation animation;
    private float stateTime;
    private String name;

    public AnimationComponent() {
    }

    @Override
    public void reset() {
        name = null;
        stateTime = 0;
        setAnimation(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void incrementStateTime(float delta) {
        this.stateTime += delta;
    }

    public TextureRegion getFrame() {
        return animation.getKeyFrame(stateTime);
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    @Override
    public String toString() {
        return "Animation[name=" + name + "]";
    }
}
