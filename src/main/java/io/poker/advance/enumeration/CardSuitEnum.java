package io.poker.advance.enumeration;

import lombok.Getter;

/**
 * @author S'phokuhle on 10/1/2021
 */
@Getter
public enum CardSuitEnum {
    CLUB("♣"), DIAMOND("♦"), HEART("♥"), SPADE("♠");
    private String suit;

    private CardSuitEnum(String suit) {
        this.suit = suit;
    }
}
