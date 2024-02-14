package com.advance.epiassessment.cardgame.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> cards;

	public Deck() {
		cards = new ArrayList<>();
		initializeDeck();
	}

	private void initializeDeck() {
		String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
		String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };

		for (String suit : suits) {
			for (String rank : ranks) {
				cards.add(new Card(suit, rank));
			}
		}
	}

	public void shuffleDeck() {
		Collections.shuffle(cards);
	}

	public List<Card> dealHand(int numCards) {
		List<Card> hand = new ArrayList<>();
		for (int i = 0; i < numCards; i++) {
			hand.add(cards.remove(0));
		}
		return hand;
	}
}
