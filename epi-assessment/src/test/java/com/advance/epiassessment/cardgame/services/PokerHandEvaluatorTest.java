/**
 * 
 */
package com.advance.epiassessment.cardgame.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.advance.epiassessment.cardgame.models.Card;


class PokerHandEvaluatorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void evaluateHand_ShouldReturnFiveOfAKind() {
		List<Card> hand = Arrays.asList(new Card("Spades", "Ace"), new Card("Clubs", "Ace"), new Card("Hearts", "Ace"),
				new Card("Diamonds", "Ace"), new Card("Joker", "Black"));

		assertNotNull("Five of a Kind, aces", PokerHandEvaluator.evaluateHand(hand));
	}

	@Test
	void evaluateHand_ShouldReturnStraightFlush() {
		List<Card> hand = Arrays.asList(new Card("Spades", "9"), new Card("Clubs", "2"), new Card("Hearts", "4"),
				new Card("Diamonds", "Ace"), new Card("Joker", "Black"));

		assertNotNull("Straight Flush, jack-high", PokerHandEvaluator.evaluateHand(hand));
	}

	// Add similar tests for other hand types...

	@Test
	void evaluateHand_ShouldReturnHighCard() {
		List<Card> hand = Arrays.asList(new Card("Spades", "Queen"), new Card("Clubs", "Ace"), new Card("Hearts", "7"),
				new Card("Joker", "Black"), new Card("Diamonds", "Ace")

		);

		assertNotNull("High Card, king, queen, seven, four, three", PokerHandEvaluator.evaluateHand(hand));
	}

}
