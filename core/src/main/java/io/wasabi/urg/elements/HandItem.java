package io.wasabi.urg.elements;

import com.badlogic.gdx.graphics.Texture;

import io.wasabi.urg.managers.RendererManager;
import io.wasabi.urg.managers.TextureManager;
import io.wasabi.urg.util.tweens.Tween;

public abstract class HandItem extends GameObject {
    private final Texture sprite;
    private final float width;
    private final float height;
    private float x;
    private float y;
    private boolean dragging;
    private float targetX;
    private float targetY;
    private boolean hasTarget;
    private Tween tweenX;
    private Tween tweenY;
    private static final float SNAP_DURATION = 0.25f;

    protected HandItem(String textureCategory, float width, float height) {
        this.width = width;
        this.height = height;
        this.sprite = TextureManager.getInstance().getTexture(
            getClass().getSimpleName(), textureCategory);
    }

    public void roundStartEffect() {}
    public void beforeSpinEffect() {}
    public void afterSpinEffect() {}
    public void roundEndEffect() {}

    @Override
    public void update(float delta) {
        if (dragging) return;

        if (tweenX != null && !tweenX.isComplete()) {
            x = tweenX.update(delta);
        }
        if (tweenY != null && !tweenY.isComplete()) {
            y = tweenY.update(delta);
        }
    }

    @Override
    public void render() {
        RendererManager.getInstance().getSpriteBatch().draw(sprite, x, y, width, height);
    }

    public boolean contains(float worldX, float worldY) {
        return worldX >= x && worldX <= x + width
            && worldY >= y && worldY <= y + height;
    }

    public void setTargetPosition(float newTargetX, float newTargetY) {
        if (hasTarget
            && Math.abs(newTargetX - targetX) < 0.01f
            && Math.abs(newTargetY - targetY) < 0.01f) {
            return;
        }

        hasTarget = true;
        targetX = newTargetX;
        targetY = newTargetY;
        tweenX = new Tween(SNAP_DURATION, x, targetX, Tween.TweenStyle.QUAD, Tween.TweenDirection.OUT);
        tweenY = new Tween(SNAP_DURATION, y, targetY, Tween.TweenStyle.QUAD, Tween.TweenDirection.OUT);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public boolean isDragging() { return dragging; }

    public void setDragging(boolean dragging) {
        boolean wasDragging = this.dragging;
        this.dragging = dragging;

        if (wasDragging && !dragging) {
            hasTarget = false;
        }
    }
}