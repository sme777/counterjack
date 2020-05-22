import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    public Game(Deck deck) {
        _deck = deck;
        _gameDeck = deck.getRandomDeck();
        _turn = true;
        subtractCards();
        adderCards();
    }

    public Game(Deck deck, ArrayList<AI> ais, ArrayList<Player> players, Dealer dealer) {
        _deck = deck;
        _gameDeck = deck.getRandomDeck();
        _turn = true;
        _players = players;
        _ais = ais;
        _dealer = dealer;
    }

    public Card dealOne() {
        if (_gameDeck == null) {
            System.out.println("An Error Occurred.");
        }

        _dealingCard = _gameDeck.get(0);
        _gameDeck.remove(0);
        System.out.println(_dealingCard);
        _currentTableCards.add(_dealingCard);
        return _dealingCard;

    }

    public void recount() {
        for (int i = 0; i < _currentTableCards.size(); i++) {
            if (_addOneCards.contains(_currentTableCards.get(i).getFace())) {
                count++;
            } else if (_subtractOneCards.contains(_currentTableCards.get(i).getFace())) {
                count--;
            } else {
                continue;
            }
        }
        System.out.println(count);
    }

    public void clearTableCards() {
        _currentTableCards.removeAll(_currentTableCards);
        System.out.println(_currentTableCards.size());
    }

    private void adderCards() {
        String[] cards = {"2", "3", "4", "5", "6"};
        _addOneCards.addAll(Arrays.asList(cards));
    }

    private void subtractCards() {
        String[] cards = {"10", "J", "Q", "K", "A"};
        _subtractOneCards.addAll(Arrays.asList(cards));
    }


    private ArrayList<Card> _currentTableCards = new ArrayList<>();
    private ArrayList<Player> _players;
    private ArrayList<AI> _ais;
    private ArrayList<String> _addOneCards = new ArrayList<>();
    private ArrayList<String> _subtractOneCards = new ArrayList<>();

    private Dealer _dealer;
    private Boolean _turn;
    private Card _dealingCard;
    private ArrayList<Card> _gameDeck;
    private Deck _deck;
    private int count = 0;
}
