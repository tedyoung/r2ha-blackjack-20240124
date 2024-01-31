package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StubDeck implements Deck {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private final ListIterator<Card> iterator;

    public StubDeck(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        this.iterator = cards.listIterator();
    }

    static Deck playerHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK,
                            Rank.THREE);
    }

    static Deck playerStandsAndBeatsDealer() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK);
    }

    static Deck playerPushesDealer() {
        return new StubDeck(Rank.TEN, Rank.QUEEN,
                            Rank.NINE, Rank.NINE);
    }

    @Override
    public Card draw() {
        return iterator.next();
    }

    @Override
    public int size() {
        // "crash test": blow up if someone calls this, since we don't expect it to be used during our tests
        throw new UnsupportedOperationException();
    }
}