import java.util.ArrayList;

public class Player {

    /** Constructs a new Player. **/
    public Player(String name) {
        playerHand = new ArrayList<>();
        _name = name;
    }

    /** Add card to the hand. Occurs when
     * a player is dealt a new card. **/
    public void addCard(Card c) {
        playerHand.add(c);
    }

    /** Remove a specific card from the hand.
     * Occurs when splitting. **/
    public void removeCard(Card c) {
        playerHand.remove(c);
    }

    /** Remove all current cards from the hand.
     * Occurs every new hand. **/
    public void removeAll() {
        playerHand.removeAll(playerHand);
    }

    /** Player hits the hand. **/
    public String hit() {
        System.out.println(_name + " Hit");
        return "hit";
    }
    /** Player splits the hand. **/
    public String split() {
        System.out.println(_name + " Split");
        return "split";
    }

    /** Player doubles the hand. **/
    public String doubleHand() {
        System.out.println(_name + " Double");
        return "double";
    }

    /** Player stands the hand. **/
    public String stand() {
        System.out.println(_name + " Stand");
        return "stand";
    }

    /** Player doesn't split the hand. **/
    public String notSplit() {
        System.out.println(_name + " Don't Split");
        return "notsplit";
    }
    /** Player surrenders. **/
    public String surrender() {
        System.out.println(_name + " Surrender");
        return "surrender";
    }

    /** Player goes bust. **/
    public String bust() {
        System.out.println(_name + " Bust!");
        return "bust";
    }

    public String blackjack() {
        System.out.println(_name + " got Blackjack!");
        return "blackjack";
    }

    public int handSum() {
        int sum = 0;
        for (Card card : playerHand) {
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

    public String deal() {
        return _name + " gets: ";
    }
    @Override
    public String toString() {
        return _name;
    }

    /** List of cards of Player's hand. **/
    private ArrayList<Card> playerHand;

    private String _name;
}
