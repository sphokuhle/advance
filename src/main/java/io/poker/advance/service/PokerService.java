package io.poker.advance.service;

import io.poker.advance.enumeration.CardSuitEnum;
import io.poker.advance.enumeration.HandRankingCategoryEnum;
import io.poker.advance.model.Card;
import io.poker.advance.model.Hand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author S'phokuhle on 10/1/2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Service
@Slf4j
public class PokerService {

    private int cardRankSize;

    public List<Card> getAllCards() {
        List<Card> cardList = new ArrayList<>();
        for(int i = 1; i <= cardRankSize; i++) {
            for(CardSuitEnum cardSymbol: CardSuitEnum.values()) {
                if(i == 1) {
                    cardList.add(new Card("A", cardSymbol));
                } else if(i > 1 && i <= 10) {
                    cardList.add(new Card(i + "", cardSymbol));
                } else if(i == 11) {
                    cardList.add(new Card("J", cardSymbol));
                } else if(i == 12) {
                    cardList.add(new Card("Q", cardSymbol));
                } else {
                    cardList.add(new Card("K", cardSymbol));
                }
            }
        }
        return cardList;
    }

    public List<Card> shuffleCards(List<Card> cards) {
        log.info("Shuffling {} cards...", cards.size());
        Collections.shuffle(cards);
        log.debug("Done shuffling");
        return cards;
    }

    public Hand dealCards(List<Card> cards, int numberOfCards) {

        List<Card> dealtCards = new ArrayList<>();
        // Checking if the number of cards to deal is not less than zero
        // or if the there are any deck of cards to deal from
        if(numberOfCards < 1 || cards == null || cards.isEmpty()) {
            int incorrectArgument = (numberOfCards < 1) ? numberOfCards : 0;
            throw new IllegalArgumentException(String.format("Cannot deal %d number of cards. Make sure a correct number of cards is provided",
                    (numberOfCards < 1) ? numberOfCards : 0));
        }
        int randomIndex = 0;
        for(int i = 0; i < numberOfCards; i++) {
            randomIndex = ThreadLocalRandom.current().nextInt(i , cards.size());
            dealtCards.add(cards.get(randomIndex));
            cards.remove(randomIndex);
        }
        printCards(dealtCards);
        return new Hand(dealtCards);
    }

    public HandRankingCategoryEnum evaluatePlayerHand(Hand hand) {
        HandRankingCategoryEnum handRankingCategory  = HandRankingCategoryEnum.HIGH_CARD;
        if(hand.getCards() != null && !hand.getCards().isEmpty()) {
            List<Card> cards = hand.getCards();
            Map<String, Long> rankMappings =
                    cards.stream().collect(Collectors.groupingBy(e -> e.getRank(), Collectors.counting()));
            Map<CardSuitEnum, Long> suitMappings =
                    cards.stream().collect(Collectors.groupingBy(e -> e.getCardSuitEnum(), Collectors.counting()));
            if(suitMappings.containsValue(5L) && isSequential(cards)) {
                handRankingCategory = HandRankingCategoryEnum.STRAIGHT_FLUSH;
            } else if(rankMappings.containsValue(4L))  {
                handRankingCategory = HandRankingCategoryEnum.FOUR_OF_A_KIND;
            }else if(rankMappings.containsValue(3L) && rankMappings.containsValue(2L)) {
                handRankingCategory = HandRankingCategoryEnum.FULL_HOUSE;
            }else if(suitMappings.containsValue(5L) && !isSequential(hand.getCards())) {
                handRankingCategory = HandRankingCategoryEnum.FLUSH;
            } else if(rankMappings.containsValue(1L) && rankMappings.size() == 5 && isSequential(hand.getCards()) && !suitMappings.containsValue(5L)) {
                handRankingCategory = HandRankingCategoryEnum.STRAIGHT;
            } else if(rankMappings.containsValue(3L) && rankMappings.containsValue(1L)) {
                handRankingCategory = HandRankingCategoryEnum.THREE_OF_A_KIND;
            } else if(rankMappings.containsValue(2L) && rankMappings.containsValue(1L)
                    && filterByNumberOfOccurrences(rankMappings,2L) == 2L) {
                handRankingCategory = HandRankingCategoryEnum.TWO_PAIR;
            } else if(rankMappings.containsValue(2L) && rankMappings.containsValue(1L) && filterByNumberOfOccurrences(rankMappings,1L) == 3L
                    && filterByNumberOfOccurrences(rankMappings,2L) == 1L) {
                handRankingCategory = HandRankingCategoryEnum.ONE_PAIR;
            }
            log.info("You have: {}", handRankingCategory.getDescription());

        }
        return handRankingCategory;
    }

    private void printCards(List<Card> cards) {
        StringBuilder sb = new StringBuilder();
        if(!cards.isEmpty()){
            for(Card card: cards) {
                sb.append(card.getRank() + card.getCardSuitEnum().getSuit() + " ");
            }
            log.info("Your Hand: " + sb.toString());
        }
    }

    /**
     * Convert a card rank value from a String to a Long
     * @param rank
     * @return
     */
    private Long convertToLong(String rank) {
        if(rank.equalsIgnoreCase("A")) {
            return 1L;
        } else if(rank.equalsIgnoreCase("J")) {
            return 11L;
        } else if(rank.equalsIgnoreCase("Q")) {
            return 12L;
        } else if(rank.equalsIgnoreCase("K")) {
            return 13L;
        } else {
            return Long.valueOf(rank.trim());
        }
    }

    private boolean isSequential(List<Card> cards) {
        List<String> ranks = cards.stream().map(Card::getRank).collect(Collectors.toList());
        boolean isAhighestRanking = false;
        if(ranks.contains("A") && ranks.contains("K")) {
            isAhighestRanking = true;
        }


        if(isAhighestRanking) {
            //Store card A to replace the alphabet with
            Card cardA =  cards.stream().filter(c -> c.getRank().equalsIgnoreCase("A")).findAny().get();
            List<Card> tempCards = cards.stream().filter(c -> !c.getRank().equalsIgnoreCase("A")).collect(Collectors.toList());
            cardA.setRank("14");
            tempCards.add(0, cardA);
            int i = 0;
            while(i < tempCards.size()) {
                //If the next element is within the array bounds we compare.
                if(i+1 < cards.size()) {
                    if(convertToLong(cards.get(i+1).getRank()) - convertToLong(cards.get(i).getRank()) != 1
                    && convertToLong(cards.get(i+1).getRank()) - convertToLong(cards.get(i).getRank()) != 0) {
                        return false;
                    }
                }
                i++;
            }
        } else {
            int i = 0;
            while(i < cards.size()) {
                //If the next element is within the array bounds we subtract.
                if(i+1 < cards.size()) {
                    if(convertToLong(cards.get(i).getRank()) - convertToLong(cards.get(i+1).getRank()) != 1
                            && convertToLong(cards.get(i).getRank()) - convertToLong(cards.get(i+1).getRank()) != 0) {
                        return false;
                    }
                }
                i++;
            }
        }
        return true;
    }

    private long filterByNumberOfOccurrences(Map<String, Long> rankMappings, long occurrences) {
        return rankMappings.values().stream().filter(value -> value.equals(occurrences)).count();
    }
}
