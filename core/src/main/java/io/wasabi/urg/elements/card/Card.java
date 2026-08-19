package io.wasabi.urg.elements.card;

import io.wasabi.urg.elements.HandItem;

public abstract class Card extends HandItem {

    protected enum Rarity {
        COMMON, UNCOMMON, RARE
    }

    protected Rarity cardRarity;
    protected Card(Rarity rarity) {
        super("card", 96, 128);
        this.cardRarity = rarity;
    }
}
