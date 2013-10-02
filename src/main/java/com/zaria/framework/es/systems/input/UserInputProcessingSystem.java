package com.zaria.framework.es.systems.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.zaria.framework.es.systems.input.TouchEvent.TouchType;

public class UserInputProcessingSystem extends VoidEntitySystem implements InputProcessor {

    Camera camera;

    Vector2 touchPoint;
    Vector3 adjustedTouchPoint;
    ListenerNode firstListener;
    ListenerNode lastListener;

    public UserInputProcessingSystem(Camera camera) {
        this.camera = camera;
        this.touchPoint = new Vector2();
        this.adjustedTouchPoint = new Vector3();
        this.setPassive(true);
    }

    @Override
    public void initialize() {
        Gdx.input.setInputProcessor(this);
    }

    public void registerListener(InputListener listener) {
        ListenerNode newListener = new ListenerNode(listener);
        if (firstListener == null) {
            firstListener = newListener;
            lastListener = newListener;
        } else {
            lastListener.next = newListener;
            lastListener = newListener;
        }
    }

    @Override
    protected void processSystem() {
        // do nothing
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return createTouchEvent(TouchType.TOUCH_DOWN, screenX, screenY, pointer);
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return createTouchEvent(TouchType.TOUCH_UP, screenX, screenY, pointer);
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return createTouchEvent(TouchType.TOUCH_DRAG, screenX, screenY, pointer);
    }

    private boolean createTouchEvent(TouchType type, int screenX, int screenY, int pointer) {
        camera.unproject(adjustedTouchPoint.set(screenX, screenY, 0));
        touchPoint.x = adjustedTouchPoint.x;
        touchPoint.y = adjustedTouchPoint.y;
        if (firstListener != null) {
            TouchEvent event = TouchEvent.createEvent(
                    type, touchPoint, pointer, firstListener);
            firstListener.listener.handleTouch(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
