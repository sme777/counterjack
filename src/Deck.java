import java.util.ArrayList;
import java.util.Random;


public class Deck {

    /** Initiated by a default one deck of cards. **/
    public Deck() {
        numberOfDeck = 1;
    }

    /** Initiated by a given number of deck of cards.
     * @param n number of decks in a current game. **/
    public Deck(int n) {
        numberOfDeck = n;
    }

    /** Generates an organized deck. **/
    public void generateOrganizedDeck() {
        for (int n = numberOfDeck; n > 0; n--) {
            for (int i = 0; i < suits.length; i++) {
                for (int v = 0; v < card.length; v++) {
                    Card cr = new Card(suits[i], card[v]);
                    organizedDeck.add(cr);
                }
            }
        }
    }

    /** Generates a random deck. **/
    public void generateRandomDeck() {
        if (organizedDeck.size() == 0) {
            getOrganizedDeck();
        }
        int count = numberOfCards * numberOfDeck;
        Random random = new Random();
        ArrayList<Card> copyDeck = organizedDeck;
        while (count > 0) {
            int n = random.nextInt(count);
            randomDeck.add(copyDeck.get(n));
            copyDeck.remove(n);
            count--;
        }
    }

    void setCardPenetration() {
        Random random = new Random();
        int minimumCards = (int) penetrationHeuristic * numberOfDeck * numberOfCards;
        penetrationPosition = random.nextInt(numberOfDeck * numberOfCards - minimumCards)
                + minimumCards;
    }

    int getPenetrationPosition() {
        setCardPenetration();
        return penetrationPosition;
    }

    /** Returns a generated organized deck.
     * @return an arraylist of cards. **/
    ArrayList<Card> getOrganizedDeck() {
        generateOrganizedDeck();
        return organizedDeck;
    }

    /** Returns a generated random deck.
     * @return an arraylist of cards. **/
    ArrayList<Card> getRandomDeck() {
        generateRandomDeck();
        return randomDeck;
    }


    /** An array of cards, regardless of suits. **/
    private final String[] card = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    /** An array of suits, with the first letter of the suit. **/
    private final String[] suits = {"D", "S", "C", "H"};
    /** An arraylist of and organized deck. **/
    private ArrayList<Card> organizedDeck = new ArrayList<>();
    /** An arraylist of a randomly shuffled deck. **/
    private ArrayList<Card> randomDeck = new ArrayList<>();
    /** Number of cards in a single deck. **/
    private final int numberOfCards = 52;
    /** Number of deck of a game. **/
    private int numberOfDeck;
    /** A card penetration heuristic for a game, by default 0.8 **/
    private double penetrationHeuristic = 0.8;
    /** Position of penetration in a given deck. **/
    private int penetrationPosition;
}
