package io.wasabi.urg.managers;

import java.util.List;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.wasabi.urg.elements.HandItem;

public abstract class HandItemInputHandler<T extends HandItem> extends InputAdapter {
    private final Viewport viewport;
    private T draggedItem;
    private final Vector2 dragOffset = new Vector2();

    protected HandItemInputHandler(Viewport viewport) {
        this.viewport = viewport;
    }

    protected abstract List<T> getItems();
    protected abstract int getClosestIndex(float itemX, int count, float worldWidth);
    protected abstract void reorderItem(T item, int newIndex);

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 world = screenToWorld(screenX, screenY);
        List<T> items = getItems();

        for (int i = items.size() - 1; i >= 0; i--) {
            T item = items.get(i);
            if (item.contains(world.x, world.y)) {
                draggedItem = item;
                draggedItem.setDragging(true);
                dragOffset.set(world.x - item.getX(), world.y - item.getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (draggedItem == null) return false;

        Vector2 world = screenToWorld(screenX, screenY);
        draggedItem.setPosition(world.x - dragOffset.x, world.y - dragOffset.y);

        List<T> items = getItems();
        int currentIndex = items.indexOf(draggedItem);
        int closestIndex = getClosestIndex(
            draggedItem.getX(), items.size(), viewport.getWorldWidth());

        if (closestIndex != currentIndex) {
            reorderItem(draggedItem, closestIndex);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (draggedItem == null) return false;

        draggedItem.setDragging(false);
        draggedItem = null;
        return true;
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector3 world = viewport.unproject(new Vector3(screenX, screenY, 0));
        return new Vector2(world.x, world.y);
    }
}