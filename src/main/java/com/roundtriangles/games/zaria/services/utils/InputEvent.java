package com.roundtriangles.games.zaria.es.systems.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TouchEvent implements Poolable {
    public enum TouchType {
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_DRAG
    }

    private TouchType type;
    private Vector2 touchPoint;
    private int pointer;
    private ListenerNode currentListener;

    private static Pool<TouchEvent> pool = new Pool<TouchEvent>() {

        @Override
        protected TouchEvent newObject() {
            return new TouchEvent();
        }

    };

    public static TouchEvent createEvent(TouchType type, Vector2 touchPoint,
            int pointer, ListenerNode startListenerNode) {
        TouchEvent event = pool.obtain();
        event.setType(type);
        event.setTouchPoint(touchPoint);
        event.setPointer(pointer);
        event.setCurrentListener(startListenerNode);
        return event;
    }

    private TouchEvent() {
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
    public ListenerNode getCurrentListener() {
        return currentListener;
    }
    public void setCurrentListener(ListenerNode currentListener) {
        this.currentListener = currentListener;
    }

    public void propagateEvent() {
        if (currentListener.next != null) {
            currentListener = currentListener.next;
            currentListener.listener.handleTouch(this);
        } else {
            // done with the event
            pool.free(this);
        }
    }

    public void handled() {
        pool.free(this);
    }

    @Override
    public void reset() {
        touchPoint.set(0, 0);
        pointer = -1;
        currentListener = null;
    }
}
