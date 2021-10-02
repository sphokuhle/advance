package io.poker.advance.service;

import io.poker.advance.model.Card;
import io.poker.advance.model.Hand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * @author S'phokuhle on 10/2/2021
 */
@Component
public class StartUp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("***INSTRUCTIONS***");
        System.out.println("Press 1 to shuffle the deck\n"
                +"Press 2 to deal a hand of 5 cards\n"
                +"Press 3 to restart the process\n"
                +"Press 4 to terminate the program");
        System.out.println("******************\n");
        Scanner sc = null;
        int option = 0;

        while(true) {
            PokerService pokerService = new PokerService(13);
            List<Card> cards = pokerService.getAllCards();
            sc = new Scanner(System.in);
            System.out.println("Enter 1 to shuffle the deck: ");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    pokerService.shuffleCards(cards);
                    System.out.println("Enter 2 to deal a hand: ");
                    option = sc.nextInt();
                case 2:
                    System.out.println("Enter the number of cards to deal(5): ");
                    option = sc.nextInt();
                    Hand hand = pokerService.dealCards(cards, option);
                    pokerService.evaluatePlayerHand(hand);
                    System.out.println("Enter 3 to start over: ");
                    option = sc.nextInt();
                case 3:
                    if(option == 4){
                        System.exit(-1);
                    }
                    break;
            }
        }
    }
}
