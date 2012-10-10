package se.tyboni.poker;

import java.util.ArrayList;
import java.util.List;

public class BinaryConverter {

    private static List<Card> deck = new Deck().getOrderedCardList();


    public static long cardsToLong(List<Card> cards) {

        long val = 0;
        for (Card c : cards) {
            val = val | cardToLong(c);
        }

        return val;
    }

    public static List<Card> longToCards(long binaryCards) {

        List<Card> cards = new ArrayList<Card>();
        long val = binaryCards;

        while (val > 0) {
            long firstCard = Long.lowestOneBit(val);
            cards.add(longToCard(firstCard));
            val = val ^ firstCard;
        }

        return cards;
    }

    public static Card longToCard(long binaryCard) {

        return deck.get(Long.numberOfTrailingZeros(binaryCard));
    }

    public static long cardToLong(Card card) {

        return 1L << deck.indexOf(card);
    }

    public static String printBinaryValue(Long l) {
        return String.format("%52s", Long.toBinaryString(l)).replace(" ", "0");
    }

}
