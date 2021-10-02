package io.poker.advance.model;

import io.poker.advance.enumeration.CardSuitEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author S'phokuhle on 10/1/2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {
    private String rank;
    private CardSuitEnum cardSuitEnum;
}
