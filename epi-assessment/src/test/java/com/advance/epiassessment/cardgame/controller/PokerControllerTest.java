package com.advance.epiassessment.cardgame.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.advance.epiassessment.cardgame.models.Card;
import com.advance.epiassessment.cardgame.services.PokerHandEvaluator;

@ExtendWith(MockitoExtension.class)
class PokerControllerTest {

	@Mock
	private PokerHandEvaluator pokerHandEvaluator;

	@InjectMocks
	private PokerController pokerController;
	@Mock
	List<Card> listOfCards;
	private MockMvc mockMvc;

	@Test
	void evaluateHand_ShouldReturnHtmlResponse() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(pokerController).build();

		mockMvc.perform(get("/playhandrank")).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=ISO-8859-1"));

		assertNotNull("Five of a Kind, aces", content().toString());
	}
}