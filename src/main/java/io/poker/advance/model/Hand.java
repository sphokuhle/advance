package io.poker.advance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author S'phokuhle on 10/1/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Hand {
    List<Card> cards;
}
