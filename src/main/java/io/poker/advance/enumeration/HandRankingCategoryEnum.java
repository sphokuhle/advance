package io.poker.advance.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author S'phokuhle on 10/1/2021
 */
@Getter
public enum HandRankingCategoryEnum {
    STRAIGHT_FLUSH("Straight Flush"), FOUR_OF_A_KIND("Four of a Kind"), FULL_HOUSE("Full House"),
    FLUSH("Flush"), STRAIGHT("Straight"), THREE_OF_A_KIND("Three of a Kind"),
    TWO_PAIR("Two Pair"), ONE_PAIR("One Pair"), HIGH_CARD("High Cards"),
    DEFAULT("");
    private String description;
    private HandRankingCategoryEnum(String description) {
        this.description = description;
    }
}
