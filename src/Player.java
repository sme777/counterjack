import java.util.ArrayList;

public class Player {

    /** Constructs a new Player. **/
    public Player() {
        playerHand = new ArrayList<>();
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
        System.out.println("Hit");
        return "hit";
    }
    /** Player splits the hand. **/
    public String split() {
        System.out.println("Split");
        return "split";
    }

    /** Player doubles the hand. **/
    public String doubleHand() {
        System.out.println("Double");
        return "double";
    }

    /** Player stands the hand. **/
    public String stand() {
        System.out.println("Stand");
        return "stand";
    }

    /** Player doesn't split the hand. **/
    public String notSplit() {
        System.out.println("Don't Split");
        return "notsplit";
    }
    /** Player surrenders. **/
    public String surrender() {
        System.out.println("Surrender");
        return "surrender";
    }

    /** Player goes bust. **/
    public String bust() {
        System.out.println("Bust!");
        return "bust";
    }

    /** List of cards of Player's hand. **/
    private ArrayList<Card> playerHand;
}
