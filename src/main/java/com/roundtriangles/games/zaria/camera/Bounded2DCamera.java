package com.roundtriangles.games.zaria.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Bounded2DCamera extends OrthographicCamera {

    public static final int DEFAULT_MAX_ZOOM = 1;
    public static final float DEFAULT_ZOOM_STEP = 1.1f;

    private int boundsWidth;
    private int boundsHeight;

    public float minZoom;
    public float maxZoom;
    public float zoomStep;

    private float minX, maxX, minY, maxY;

    public Bounded2DCamera() {
    }

    public void initialize(int boundsWidth, int boundsHeight, float minZoom, float zoomStep) {
        this.boundsWidth = boundsWidth;
        this.boundsHeight = boundsHeight;
        this.minZoom = minZoom;
        this.maxZoom = minZoom;
        this.zoomStep = zoomStep;
    }

    public void shiftCamera(Vector2 shift) {
        position.add(shift.x, shift.y, 0);
        ensurePosition();
    }

    public void zoomIn() {
        if (zoom > minZoom) {
            zoom /= zoomStep;
            if (zoom < minZoom) {
                zoom = minZoom;
            }
            recalculateBounds();
            ensurePosition();
        }
    }

    public void zoomOut() {
        if (zoom < maxZoom) {
            zoom *= zoomStep;
            if (zoom > maxZoom) {
                zoom = maxZoom;
            }
            recalculateBounds();
            ensurePosition();
        }
    }

    public void resetPosition() {
        position.set(minX, maxY, 0);
        zoom = maxZoom;
    }

    public void resize(int width, int height) {
        viewportWidth = width;
        viewportHeight = height;
        maxZoom = Math.min(boundsWidth / viewportWidth, boundsHeight / viewportHeight);
        zoom = maxZoom;

        recalculateBounds();
        ensurePosition();
    }

    private void ensurePosition(){
        if (position.x < minX) {
            position.x = minX;
        } else if (position.x > maxX){
            position.x = maxX;
        }
        if (position.y < minY) {
            position.y = minY;
        } else if (position.y > maxY) {
            position.y = maxY;
        }
    }

    private void recalculateBounds() {
        this.minX = viewportWidth * zoom / 2;
        this.maxX = boundsWidth - viewportWidth * zoom / 2;
        this.minY = viewportHeight * zoom / 2;
        this.maxY = boundsHeight - viewportHeight * zoom / 2;
    }
}
