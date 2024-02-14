package com.advance.epiassessment.cardgame.utils;

// CardUtils.java
import java.util.HashMap;
import java.util.Map;

public class CardUtils {
	private static final Map<String, Integer> rankValues = new HashMap<>();

	static {
		rankValues.put("2", 2);
		rankValues.put("3", 3);
		rankValues.put("4", 4);
		rankValues.put("5", 5);
		rankValues.put("6", 6);
		rankValues.put("7", 7);
		rankValues.put("8", 8);
		rankValues.put("9", 9);
		rankValues.put("10", 10);
		rankValues.put("Jack", 11);
		rankValues.put("Queen", 12);
		rankValues.put("King", 13);
		rankValues.put("Ace", 14);
		// for now i assumed these are the cards and the ranks we are dealing with, with
		// no special cards :)
	}

	public static int getRankValue(String rank) {
		return rankValues.get(rank);
	}
}