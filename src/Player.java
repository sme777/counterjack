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
        System.out.println("Hit");
        return "hit";
    }

    public String split() {
        System.out.println("Split");
        return "split";
    }

    public String doubleHand() {
        System.out.println("Double");
        return "double";
    }

    public String stand() {
        System.out.println("Stand");
        return "stand";
    }

    public String notSplit() {
        System.out.println("Don't Split");
        return "notsplit";
    }

    public String surrender() {
        System.out.println("Surrender");
        return "surrender";
    }

    private ArrayList<Card> playerHand;
}
