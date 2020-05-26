import java.util.ArrayList;

public class Dealer extends Player{

    /** Constructs a new Dealer. **/
    public Dealer(String name) {
        super(name);

        _dealer = new ArrayList<>();
    }

    public Dealer(String name, double bank) {
        super(name, bank);
        _dealer = new ArrayList<>();

    }

    /** Initialize a dealer move. Occurs when
     *  other players have gone bust or chose
     *  to stand/surrender the hand. **/
    public void move() {
        _numberOfCards++;
        hit();
    }

    /** Adds a ard to the Dealers hand.
     * @param c is the given card to be added. **/
    public void addCard(Card c) {
        _dealer.add(c);
    }

    /** Removes all cards from the dealers hand.
     * Occurs at the end of each hand. **/
    public void removeAll() {
        _dealer.removeAll(_dealer);
    }

    /** Calculates the sum of current hand.
     * @return the sum of the hand. **/
    public int handSum() {
        int sum = 0;
        for (Card card : _dealer) {
            if (card.getFace().equals("A")) {
                if (sum < 11) {
                    sum += 11;
                } else {
                    sum += 1;
                }
            } else if (card.getFace().equals("J") || card.getFace().equals("Q")
                    || card.getFace().equals("K")) {
                sum += 10;
            } else {
                sum += Integer.parseInt(card.getFace());
            }
        }
        return sum;
    }

    /** Number of cards a dealer has, initialized at 0.**/
    private int _numberOfCards = 0;
    /** A list of dealers Cards. **/
    private ArrayList<Card> _dealer;

}
