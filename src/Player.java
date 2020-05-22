import java.util.ArrayList;

public class Player {

    public Player() {
        playerHand = new ArrayList<>();
    }

    public void addCard(Card c) {
        playerHand.add(c);
    }

    public void removeCard(Card c) {
        playerHand.remove(c);
    }

    public void removeAll() {
        playerHand.removeAll(playerHand);
    }

    public String hit() {
        return "hit";
    }

    public String split() {
        return "split";
    }

    public String doubleHand() {
        return "double";
    }

    public String stand() {
        return "stand";
    }

    public String notSplit() {
        return "notsplit";
    }

    public String surrender() {
        return "surrender";
    }

    private ArrayList<Card> playerHand;
}
