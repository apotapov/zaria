package com.roundtriangles.games.zaria.es.systems.input;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;

public abstract class InputProcessingSystem extends EntityProcessingSystem implements InputListener {

    protected Array<TouchEvent> touchBuffer;
    protected Array<TouchEvent> touchList;

    public InputProcessingSystem(Aspect aspect) {
        super(aspect);
        touchBuffer = new Array<TouchEvent>();
        touchList = new Array<TouchEvent>();
    }

    @Override
    protected void process(Entity e) {
        synchronized (touchBuffer) {
            touchList.addAll(touchBuffer);
            touchBuffer.clear();
        }
        for (TouchEvent event : touchList) {
            if (!processEvent(e, event)) {
                event.propagateEvent();
            } else {
                event.handled();
            }
        }
        touchList.clear();
    }

    protected boolean processEvent(Entity e, TouchEvent event) {
        switch (event.getType()) {
        case TOUCH_DOWN:
            return processTouchDown(e, event);
        case TOUCH_UP:
            return processTouchUp(e, event);
        case TOUCH_DRAG:
            return processTouchDrag(e, event);
        }
        return false;
    }

    protected abstract boolean processTouchDown(Entity e, TouchEvent event);
    protected abstract boolean processTouchUp(Entity e, TouchEvent event);
    protected abstract boolean processTouchDrag(Entity e, TouchEvent event);

    @Override
    public void handleTouch(TouchEvent event) {
        synchronized (touchBuffer) {
            touchBuffer.add(event);
        }
    }

}
