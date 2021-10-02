package io.poker.advance.service;

import io.poker.advance.enumeration.CardSuitEnum;
import io.poker.advance.enumeration.HandRankingCategoryEnum;
import io.poker.advance.model.Card;
import io.poker.advance.model.Hand;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author S'phokuhle on 10/2/2021
 */
//@RunWith(JUnitPlatform.class)
public class PokerServiceTest {
    private PokerService pokerService;
    private List<Card> cards;
    private Hand hand;

    @Before
    public void init() {
        pokerService = new PokerService(13);
        cards = pokerService.getAllCards();
        pokerService.shuffleCards(cards);
    }

    @Test
    public void testDealCards(){
        Hand hand = pokerService.dealCards(cards, 5);
        assertThat(hand.getCards().size()).isEqualTo(5);
    }

    @Test
    public void testEvaluatePlayerHand() {
        hand = new Hand(createFlush());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.FLUSH);

        hand = new Hand(createStraightFlush());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.STRAIGHT_FLUSH);

        hand = new Hand(createStraight());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.STRAIGHT);

        hand = new Hand(createFourOfAKind());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.FOUR_OF_A_KIND);

        hand = new Hand(createFullHouse());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.FULL_HOUSE);

        hand = new Hand(createThreeOfAKind());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.THREE_OF_A_KIND);

        hand = new Hand(createTwoPair());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.TWO_PAIR);

        hand = new Hand(createPair());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.ONE_PAIR);

        hand = new Hand(createHighCard());
        assertThat(pokerService.evaluatePlayerHand(hand)).isEqualTo(HandRankingCategoryEnum.HIGH_CARD);
    }

    private List<Card> createStraightFlush() {
        return Arrays.asList(new Card("J", CardSuitEnum.CLUB), new Card("10", CardSuitEnum.CLUB),
        new Card("9", CardSuitEnum.CLUB), new Card("8", CardSuitEnum.CLUB), new Card("7", CardSuitEnum.CLUB));
    }

    private List<Card> createFourOfAKind() {
        return Arrays.asList(new Card("2", CardSuitEnum.SPADE), new Card("2", CardSuitEnum.HEART),
                new Card("2", CardSuitEnum.DIAMOND), new Card("2", CardSuitEnum.CLUB), new Card("7", CardSuitEnum.CLUB));
    }

    private List<Card> createFullHouse() {
        return Arrays.asList(new Card("2", CardSuitEnum.SPADE), new Card("2", CardSuitEnum.HEART),
                new Card("2", CardSuitEnum.DIAMOND), new Card("3", CardSuitEnum.CLUB), new Card("3", CardSuitEnum.CLUB));
    }

    private List<Card> createFlush() {
        return Arrays.asList(new Card("Q", CardSuitEnum.HEART), new Card("6", CardSuitEnum.HEART),
                new Card("9", CardSuitEnum.HEART), new Card("10", CardSuitEnum.HEART), new Card("7", CardSuitEnum.HEART));
    }

    private List<Card> createStraight() {
        return Arrays.asList(new Card("Q", CardSuitEnum.DIAMOND), new Card("J", CardSuitEnum.HEART),
                new Card("10", CardSuitEnum.SPADE), new Card("9", CardSuitEnum.CLUB), new Card("8", CardSuitEnum.HEART));
    }

    private List<Card> createThreeOfAKind() {
        return Arrays.asList(new Card("Q", CardSuitEnum.DIAMOND), new Card("Q", CardSuitEnum.HEART),
                new Card("Q", CardSuitEnum.SPADE), new Card("9", CardSuitEnum.CLUB), new Card("8", CardSuitEnum.HEART));
    }

    private List<Card> createTwoPair() {
        return Arrays.asList(new Card("Q", CardSuitEnum.DIAMOND), new Card("Q", CardSuitEnum.HEART),
                new Card("K", CardSuitEnum.SPADE), new Card("K", CardSuitEnum.CLUB), new Card("8", CardSuitEnum.HEART));
    }

    private List<Card> createPair() {
        return Arrays.asList(new Card("Q", CardSuitEnum.DIAMOND), new Card("Q", CardSuitEnum.HEART),
                new Card("K", CardSuitEnum.SPADE), new Card("6", CardSuitEnum.CLUB), new Card("4", CardSuitEnum.HEART));
    }

    private List<Card> createHighCard() {
        return Arrays.asList(new Card("Q", CardSuitEnum.DIAMOND), new Card("3", CardSuitEnum.HEART),
                new Card("K", CardSuitEnum.SPADE), new Card("6", CardSuitEnum.CLUB), new Card("4", CardSuitEnum.HEART));
    }
}
