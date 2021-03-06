package com.kschmidt.hearthstone.domain;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kschmidt.hearthstone.App;
import com.kschmidt.hearthstone.repository.MongoDeckRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class DiffAnalyzerIntegrationTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(DiffAnalyzerIntegrationTest.class);

	@Autowired
	private MongoDeckRepository mongoDeckRepository;

	@Autowired
	private Deck userDeck;

	@Test
	public void testDiffAnalyzer() throws IOException {
		List<DeckDiff> diffs = DeckDiff.diffDecks(userDeck,
				mongoDeckRepository.findAll());
		LOG.debug(diffs.toString());
		DiffAnalyzer analyzer = new DiffAnalyzer(diffs);
		// analyzer.filterByPercentComplete(90);
		analyzer.filterByMaxRequiredDust(1820);
		//analyzer.filterByCardSet("The Grand Tournament");
		analyzer.filterByDate("20150907");

		//Deck allMissing = analyzer.getAllMissingCards();
		// LOG.info(allMissing.sortByDustValue().toString());
		// LOG.info(allMissing.sortByNumCards().toString());
		LOG.info(analyzer.getCardRatings().toString());
		
		diffs.sort(new Comparator<DeckDiff>() {

			@Override
			public int compare(DeckDiff arg0, DeckDiff arg1) {
				return Double.compare(arg0.getRankingMetric(), arg1.getRankingMetric());
			}
			
		});
		for (DeckDiff diff : diffs) {
			if (diff.isMissingCards()) {
				LOG.info(diff.getDesiredDeck().getName() + "(" + diff.getRankingMetric() + "), (" + diff.getRequiredDust() + "), (" + diff.getRating() + "), ("+diff.getLastUpdated() + ")");
			}
		}
	}
}
