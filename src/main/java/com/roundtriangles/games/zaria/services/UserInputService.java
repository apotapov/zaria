package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.roundtriangles.games.zaria.services.utils.InputEvent;
import com.roundtriangles.games.zaria.services.utils.InputEvent.InputType;

public class UserInputService implements InputProcessor {

    protected Array<InputEvent> eventBuffer;
    protected Array<InputEvent> eventList;
    protected Pool<InputEvent> eventPool;

    protected Camera camera;
    protected Vector3 screenTouchPoint;
    protected Vector2 gameTouchPoint;

    public UserInputService() {
        eventBuffer = new Array<InputEvent>();
        eventList = new Array<InputEvent>();

        eventPool = new Pool<InputEvent>() {
            @Override
            protected InputEvent newObject() {
                return new InputEvent();
            }
        };
        this.screenTouchPoint = new Vector3();
        this.gameTouchPoint = new Vector2();
    }

    public void initialize(Camera camera) {
        this.camera = camera;
        Gdx.input.setInputProcessor(this);
    }

    public Array<InputEvent> poll() {
        synchronized (eventBuffer) {
            eventPool.freeAll(eventList);
            eventList.clear();
            eventList.addAll(eventBuffer);
            eventBuffer.clear();
            return eventList;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return addTouchEvent(InputType.TOUCH_DOWN, screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return addTouchEvent(InputType.TOUCH_UP, screenX, screenY, pointer);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return addTouchEvent(InputType.TOUCH_DRAG, screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    protected boolean addTouchEvent(InputType type, int x, int y, int pointer) {
        synchronized (eventBuffer) {
            camera.unproject(screenTouchPoint.set(x, y, 0));
            gameTouchPoint.x = screenTouchPoint.x;
            gameTouchPoint.y = screenTouchPoint.y;
            InputEvent event = createTouchEvent(type, gameTouchPoint, pointer);
            eventBuffer.add(event);
            return true;
        }
    }

    protected InputEvent createTouchEvent(InputType type, Vector2 touchPoint, int pointer) {
        InputEvent event = eventPool.obtain();
        event.setType(type);
        event.setTouchPoint(touchPoint);
        event.setPointer(pointer);
        return event;
    }

}
