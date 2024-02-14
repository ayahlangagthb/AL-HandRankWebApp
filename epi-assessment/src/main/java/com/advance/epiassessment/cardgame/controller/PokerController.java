package com.advance.epiassessment.cardgame.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.advance.epiassessment.cardgame.models.Card;
import com.advance.epiassessment.cardgame.models.Deck;
import com.advance.epiassessment.cardgame.services.PokerHandEvaluator;

@Controller
public class PokerController {

	private static final Logger logger = LoggerFactory.getLogger(PokerController.class);

	@Autowired
	static PokerHandEvaluator pokerHandEvaluator;

	@GetMapping("/playhandrank")
	@ResponseBody
	public String evaluateHand() {
		try {
			Deck deck = new Deck();
			deck.shuffleDeck();
			logger.info("shuffling... shuffling ... shuffling...");

			logger.info("ready to deal 5 cards to a hand ...");
			logger.info("===================================");

			List<Card> hand = deck.dealHand(5);

			logger.info("Your hand: {}", hand.toString());
			logger.info("============evaluating Hand========");
			String handRank = PokerHandEvaluator.evaluateHand(hand);

			logger.info("You have: {}", handRank);

			return displayHandRankGame(hand, handRank);
		} catch (Exception e) {
			logger.error("Error occurred while evaluating the hand", e);
			return "Error occurred during evaluation";
		}
	}

	private static String displayHandRankGame(List<Card> hand, String handRank) {

		try {
			StringBuilder response = new StringBuilder();
			response.append("<html><head><title>Card Evaluator</title></head><body>");
			response.append("<a>").append("shuffling... shuffling ... shuffling...").append("</a>");
			response.append("<h2>Your Hand:</h2><ul>");
			for (Card card : hand) {
				response.append("<li>").append(card).append("</li>");
			}
			response.append("</ul>");
			response.append("<h2>Hand Evaluation:</h2>");
			response.append("<p>").append("You have: ").append(handRank).append("</p>");
			response.append("</body></html>");

			return response.toString();
		} catch (Exception e) {
			logger.error("Error occurred while evaluating the hand", e);
			return "Error occurred during evaluation";
		}
	}
}