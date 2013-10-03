package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class InputEvent implements Poolable {
    public enum TouchType {
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_DRAG
    }

    private TouchType type;
    private Vector2 touchPoint;
    private int pointer;

    public InputEvent() {
        touchPoint = new Vector2();
    }

    public TouchType getType() {
        return type;
    }
    public void setType(TouchType type) {
        this.type = type;
    }
    public Vector2 getTouchPoint() {
        return touchPoint;
    }

    public void setTouchPoint(Vector2 touchPoint) {
        this.touchPoint.set(touchPoint);
    }

    public int getPointer() {
        return pointer;
    }
    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    @Override
    public void reset() {
        touchPoint.set(-1, -1);
        pointer = -1;
    }
}
