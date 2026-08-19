package io.wasabi.urg.managers;

import java.util.List;

import com.badlogic.gdx.utils.viewport.Viewport;

import io.wasabi.urg.elements.charm.AbstractCharm;
import io.wasabi.urg.state.RunState;
import io.wasabi.urg.ui.CharmLayout;

public class CharmInputHandler extends HandItemInputHandler<AbstractCharm> {
    private final RunState runState;

    public CharmInputHandler(RunState runState, Viewport viewport) {
        super(viewport);
        this.runState = runState;
    }

    @Override
    protected List<AbstractCharm> getItems() {
        return runState.getOwnedCharms();
    }

    @Override
    protected int getClosestIndex(float itemX, int count, float worldWidth) {
        return CharmLayout.getClosestIndex(itemX, count, worldWidth);
    }

    @Override
    protected void reorderItem(AbstractCharm charm, int newIndex) {
        runState.reorderCharm(charm, newIndex);
    }
}
