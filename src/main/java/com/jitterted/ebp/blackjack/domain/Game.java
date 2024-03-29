package com.jitterted.ebp.blackjack.domain;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private boolean playerDone;

    public Game(Deck deck) {
        this.deck = deck;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        if (playerHand.isBlackjack()) {
            playerDone = true;
        }
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.isBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES_DEALER;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    // 1. Return Hand instance field - breaks encapsulation: allows callers to modify internals of Game
    //    (Violates Game's integrity) - also not a Snapshot (point in time view)
    // 2. List<Card> - Stream<Card>: what about faceUpCard(), and value()
    //    2a. Could have separate Query methods for the other aspects
    // 3. HandDto - just has "primitive" data (Strings & numbers), however DTOs live in Adapters
    //==> 4. HandValue, HandInfo, HandView
    //       - Value Object with Rich Domain Types: Cards, Face Up Card, int
    // 5. HandSnapshot - contains internal data from Hand, used exclusively for persistence
    public Hand dealerHand() {
        return dealerHand;
    }

    public Hand playerHand() {
        return playerHand;
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
    }

    public void playerStands() {
        playerDone = true;
        dealerTurn();
    }

    public boolean isPlayerDone() {
        return playerDone;
    }

}
