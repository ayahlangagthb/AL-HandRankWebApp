package com.advance.epiassessment.cardgame.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.advance.epiassessment.cardgame.models.Card;
import com.advance.epiassessment.cardgame.utils.CardUtils;

@Service
public class PokerHandEvaluator {
	private static final Logger logger = LoggerFactory.getLogger(PokerHandEvaluator.class);

	public static String evaluateHand(List<Card> hand) {
		try {

			if (isNOfAKind(hand, 5)) {
				return "Five of a Kind, " + getRankOfNOfAKind(hand, 5);
			}

			if (isNOfAKind(hand, 4)) {
				return "Four of a Kind, " + getRankOfNOfAKind(hand, 4);
			}
			if (isNOfAKind(hand, 3)) {
				if (isFullHouse(hand)) {
					return "Full House, " + getRankOfFullHouse(hand);
				} else {
					return "Three of a Kind, " + getRankOfNOfAKind(hand, 3);
				}
			}
			if (isStraightFlush(hand)) {
				return "Straight Flush, " + getRankOfStraightFlush(hand);
			}
			if (isFlush(hand)) {
				return "Flush, " + getRankOfFlush(hand);
			}

			if (isStraight(hand)) {
				return "Straight, " + getRankOfStraight(hand);
			}

			if (isTwoPair(hand)) {
				return "Two Pair, " + getRankOfTwoPair(hand);
			}

			if (isOnePair(hand)) {
				return "One Pair, " + getRankOfOnePair(hand);
			}

			if (isHighCard(hand)) {
				return "High Card, " + getRankOfHighCard(hand);
			}

		} catch (Exception e) {
			logger.error("Error occurred while evaluating the hand", e);
			return "Error occurred during evaluation";
		}
		// For simplicity, let's just return a placeholder for other hands.
		return "Unknow/unimplemented Hand Rank";
	}

	// this generic logic is to evaluate, 5,4,3 of a kind in a hand
	private static boolean isNOfAKind(List<Card> hand, int n) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		return rankCount.containsValue((long) n);
	}

	private static String getRankOfNOfAKind(List<Card> hand, int n) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		return rankCount.entrySet().stream().filter(entry -> entry.getValue() == n).findFirst().map(Map.Entry::getKey)
				.orElse("Unknown Rank");
	}

	// This logic is to evaluate a straight flush
	private static boolean isStraightFlush(List<Card> hand) {
		List<String> ranks = hand.stream().map(Card::getRank).collect(Collectors.toList());

		Collections.sort(ranks);

		boolean isSequential = true;

		for (int i = 1; i < ranks.size(); i++) {
			if (CardUtils.getRankValue(ranks.get(i)) != CardUtils.getRankValue(ranks.get(i - 1)) + 1) {
				isSequential = false;
				break;
			}
		}

		boolean isSameSuit = hand.stream().map(Card::getSuit).distinct().count() == 1;

		return isSequential && isSameSuit;
	}

	private static String getRankOfStraightFlush(List<Card> hand) {
		return hand.get(4).getRank(); // The highest-ranking card in a straight flush
	}

	// this is logic to rank a full house
	private static boolean isFullHouse(List<Card> hand) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		return rankCount.containsValue(3L) && rankCount.containsValue(2L);
	}

	private static String getRankOfFullHouse(List<Card> hand) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		String tripletRank = rankCount.entrySet().stream().filter(entry -> entry.getValue() == 3L).findFirst()
				.map(Map.Entry::getKey).orElse("Unknown Rank");

		String pairRank = rankCount.entrySet().stream().filter(entry -> entry.getValue() == 2L).findFirst()
				.map(Map.Entry::getKey).orElse("Unknown Rank");

		return tripletRank + " over " + pairRank;
	}

	// this is logic to evaluate a flush
	private static boolean isFlush(List<Card> hand) {
		return hand.stream().map(Card::getSuit).distinct().count() == 1;
	}

	private static String getRankOfFlush(List<Card> hand) {
		Collections.sort(hand, (c1, c2) -> CardUtils.getRankValue(c2.getRank()) - CardUtils.getRankValue(c1.getRank()));
		return hand.stream().map(Card::getRank).collect(Collectors.joining(", "));
	}

	// this is logic to evaluate a straight
	private static boolean isStraight(List<Card> hand) {
		List<String> ranks = hand.stream().map(Card::getRank).collect(Collectors.toList());

		Collections.sort(ranks);

		boolean isSequential = true;
		for (int i = 1; i < ranks.size(); i++) {
			if (CardUtils.getRankValue(ranks.get(i)) != CardUtils.getRankValue(ranks.get(i - 1)) + 1) {
				isSequential = false;
				break;
			}
		}

		return isSequential;
	}

	private static String getRankOfStraight(List<Card> hand) {
		return hand.get(4).getRank(); // The highest-ranking card in a straight
	}

	private static boolean isTwoPair(List<Card> hand) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		return rankCount.values().stream().filter(count -> count == 2L).count() == 2;
	}

	// This is logic to calculate two pair
	private static String getRankOfTwoPair(List<Card> hand) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		List<String> pairs = rankCount.entrySet().stream().filter(entry -> entry.getValue() == 2L)
				.map(Map.Entry::getKey).collect(Collectors.toList());

		Collections.sort(pairs);

		return pairs.get(1) + " and " + pairs.get(0); // Higher-ranking pair first
	}

	// this is logic to calculate one pair
	private static boolean isOnePair(List<Card> hand) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		return rankCount.containsValue(2L);
	}

	private static String getRankOfOnePair(List<Card> hand) {
		Map<String, Long> rankCount = hand.stream()
				.collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

		String pairRank = rankCount.entrySet().stream().filter(entry -> entry.getValue() == 2L).findFirst()
				.map(Map.Entry::getKey).orElse("Unknown Rank");

		return pairRank;
	}

	// this is logic to calculate the HighCard
	private static boolean isHighCard(List<Card> hand) {
		return !isNOfAKind(hand, 2) && !isStraight(hand) && !isFlush(hand);
	}

	private static String getRankOfHighCard(List<Card> hand) {
		Collections.sort(hand, (c1, c2) -> CardUtils.getRankValue(c2.getRank()) - CardUtils.getRankValue(c1.getRank()));
		return hand.stream().map(Card::getRank).collect(Collectors.joining(", "));
	}

}