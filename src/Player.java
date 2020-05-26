import java.util.ArrayList;

public class Player {

    /** Constructs a new Player. **/
    public Player(String name) {
        playerHand = new ArrayList<>();
        _name = name;
        _bankroll = 2000;
    }

    public Player(String name, double bankroll) {
        playerHand = new ArrayList<>();
        _name = name;
        _bankroll = bankroll;
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

    /** Player gets a blackjack. **/
    public String blackjack() {
        System.out.println(_name + " got Blackjack!");
        return "blackjack";
    }

    public void addToBankroll(double amount) {
        _bankroll += amount;
    }

    public void subtractFromBankroll(double amount) {
        _bankroll -= amount;
    }

    public double getBankroll() {
        System.out.println(_bankroll);
        return _bankroll;
    }
    /** THe sum of the player's cards.
     * @return the accumulated value of player's card**/
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

    public void rename(String newName) {
        _name = newName;
    }

    /** The value returned when a player is in game and receives a card. **/
    public String deal() {
        return _name + " gets: ";
    }

    /** Overridden value of toString, changed to the name of the Player. **/
    @Override
    public String toString() {
        return _name;
    }

    /** List of cards of Player's hand. **/
    private ArrayList<Card> playerHand;
    /** The name of the Player. **/
    private String _name;

    private double _bankroll;
}
