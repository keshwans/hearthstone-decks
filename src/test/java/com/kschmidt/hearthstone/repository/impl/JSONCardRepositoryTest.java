package com.kschmidt.hearthstone.repository.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kschmidt.hearthstone.domain.Card;
import com.kschmidt.hearthstone.domain.PlayerClass;
import com.kschmidt.hearthstone.domain.Rarity;
import com.kschmidt.hearthstone.repository.CardRepository;

public class JSONCardRepositoryTest {

	@Test
	public void testFindCard() throws JsonParseException, JsonMappingException,
			IOException {

		CardRepository cardRepository = new JSONCardRepository("cards.collectible.json");
		List<Card> cards = cardRepository.getCards();
		assertTrue(cards.size() > 0);

		Card card = cardRepository.findCard("Deathwing");
		assertNotNull(card);
		assertThat(card.getRarity(), equalTo(Rarity.LEGENDARY));
		Assert.assertThat(card.getPlayerClass(), equalTo(PlayerClass.NEUTRAL));
		assertThat(card.getCardSet(), equalTo("EXPERT1"));

		card = cardRepository.findCard("Frostbolt");
		assertNotNull(card);
		assertThat(card.getRarity(), equalTo(Rarity.COMMON));
		assertThat(card.getPlayerClass(), equalTo(PlayerClass.MAGE));
		assertThat(card.getCardSet(), equalTo("CORE"));
		
		card = cardRepository.findCard("Reno Jackson");
		assertNotNull(card);
		assertThat(card.getRarity(), equalTo(Rarity.LEGENDARY));
		assertThat(card.getPlayerClass(), equalTo(PlayerClass.NEUTRAL));
		assertThat(card.getCardSet(), equalTo("LOE"));
		
		card = cardRepository.findCard("Yogg-Saron, Hope's End");
		assertNotNull(card);
		assertThat(card.getRarity(), equalTo(Rarity.LEGENDARY));
		assertThat(card.getPlayerClass(), equalTo(PlayerClass.NEUTRAL));
		assertThat(card.getCardSet(), equalTo("OG"));
	}
}
