package io.wasabi.urg.managers;

import java.util.List;

import com.badlogic.gdx.utils.viewport.Viewport;

import io.wasabi.urg.elements.card.Card;
import io.wasabi.urg.state.RunState;
import io.wasabi.urg.ui.CardLayout;

public class CardInputHandler extends HandItemInputHandler<Card> {
    private final RunState runState;

    public CardInputHandler(RunState runState, Viewport viewport) {
        super(viewport);
        this.runState = runState;
    }

    @Override
    protected List<Card> getItems() {
        return runState.getOwnedCards();
    }

    @Override
    protected int getClosestIndex(float itemX, int count, float worldWidth) {
        return CardLayout.getClosestIndex(itemX, count, worldWidth);
    }

    @Override
    protected void reorderItem(Card card, int newIndex) {
        runState.reorderCard(card, newIndex);
    }
}
