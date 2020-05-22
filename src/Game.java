import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Game {

    public Game(Deck deck) {
        _deck = deck;
        _gameDeck = deck.getRandomDeck();
        _turn = true;
        AI sampleAI = new AI(true);
        Player samplePlayer = new Player();
        _ais.add(sampleAI);
        _players.add(samplePlayer);
        _dealer = new Dealer();

        _gameTree.add(samplePlayer);
        _gameTree.add(sampleAI);
        _gameTree.add(_dealer);

        subtractCards();
        adderCards();
    }

    public Game(Deck deck, int ais, int players) {
        _deck = deck;
        _gameDeck = deck.getRandomDeck();
        _turn = true;
        while (ais > 0) {
            AI sampleAI = new AI(true);
            _ais.add(sampleAI);
            ais--;
        }
        while (players > 0) {
            Player samplePlayer = new Player();
            _players.add(samplePlayer);
            players--;
        }

        _dealer = new Dealer();
    }

    public Card dealOne() {
        if (_gameDeck == null) {
            System.out.println("An Error Occurred.");
        }

        _dealingCard = _gameDeck.get(0);
        _gameDeck.remove(0);
        System.out.println(_dealingCard);
        _currentTableCards.add(_dealingCard);
        if (!_turn) {
            Player ply = _gameTree.pop();
            _gameTree.add(ply);
            if (ply instanceof AI) {
                AI aiPlayer = (AI) ply;
                aiPlayer.addCard(_dealingCard);
                if (_dealerCard != null) {
                    aiPlayer.addDealerCard(_dealingCard);
                    _dealerCard = null;
                }
                aiPlayer.move();
            } else if (ply instanceof Dealer){
                Dealer dealer = (Dealer) ply;
                _dealerCard = _dealingCard;
                dealer.move();
            }
            if (!(_gameTree.getFirst() instanceof AI || _gameTree.getFirst() instanceof Dealer)) {
                _turn = !_turn;
            }
        }  else {
            System.out.println("Player turn: ");
            Player ply = _gameTree.pop();
            _gameTree.add(ply);
            _turn = !_turn;
        }
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

    private LinkedList<Player> _gameTree = new LinkedList<>();
    private ArrayList<Card> _currentTableCards = new ArrayList<>();
    private ArrayList<Player> _players = new ArrayList<>();
    private ArrayList<AI> _ais = new ArrayList<>();
    private ArrayList<String> _addOneCards = new ArrayList<>();
    private ArrayList<String> _subtractOneCards = new ArrayList<>();


    private Dealer _dealer;
    private Boolean _turn;
    private Card _dealingCard;
    private ArrayList<Card> _gameDeck;
    private Card _dealerCard;
    private Deck _deck;
    private int count = 0;
}
