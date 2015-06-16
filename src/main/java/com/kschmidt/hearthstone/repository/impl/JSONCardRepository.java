package com.kschmidt.hearthstone.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.kschmidt.hearthstone.domain.Card;
import com.kschmidt.hearthstone.repository.CardRepository;

public class JSONCardRepository implements CardRepository {

	private List<Card> cards = new ArrayList<Card>();

	@SuppressWarnings("unchecked")
	public JSONCardRepository(String filename) throws JsonParseException,
			JsonMappingException, IOException {
		File jsonCardFile = new File(this.getClass().getClassLoader()
				.getResource("AllSets.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Map<String, List<Map<String, String>>> data = mapper.readValue(
				jsonCardFile, Map.class);
		for (String setName : data.keySet()) {
			List<Map<String, String>> cardsInSet = data.get(setName);
			for (Map<String, String> cardData : cardsInSet) {
				if (Arrays.asList(new String[] { "Minion", "Spell", "Weapon" })
						.contains(cardData.get("type"))) {
					Card card = new Card(cardData.get("id"),
							cardData.get("name"), cardData.get("rarity"));
					cards.add(card);
				}
			}
		}
	}

	@Override
	public List<Card> getCards() {
		return cards;
	}

	@Override
	public Card findCard(final String cardName) {
		return Iterables.find(cards, new Predicate<Card>() {
			@Override
			public boolean apply(Card card) {
				return Objects.equal(card.getName().toLowerCase(),
						cardName.toLowerCase());
			}
		});
	}

}