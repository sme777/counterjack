import java.util.ArrayList;

public class Dealer extends Player{

    public Dealer() {
        _dealer = new ArrayList<>();
    }

    public void move() {
        _numberOfCards++;
        if (_numberOfCards > 1) {
            if (handSum() < 17) {
                hit();
            } else {
                stand();
            }
        }
    }

    public void addCard(Card c) {
        _dealer.add(c);
    }

    public void removeAll() {
        _dealer.removeAll(_dealer);
    }

    private int handSum() {
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
    private int _numberOfCards = 0;
    private ArrayList<Card> _dealer;
}
