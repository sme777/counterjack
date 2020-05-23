import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Game {

    /** Constructs a new Game, with default one User Player
     * and a default one AI.
     * @param deck takes in a specified deck. **/
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
    
    /** Constructs a new Game with different inputs.
     * @param deck the supplied game deck.
     * @param ais number of AIs.
     * @param players number of User Players. **/
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

    /** Deals one card to the current player. Saves the drawn card to the
     * current table cards, and removes from the deck. Waits for input for
     * a User Player or chooses action for AI and Dealer.
     * @return the drawing card. **/
    public Card dealOne() {
        if (_gameDeck == null) {
            System.out.println("An Error Occurred.");
        }

        _drawingCard = _gameDeck.get(0);
        _gameDeck.remove(0);
        System.out.println(_drawingCard);
        _currentTableCards.add(_drawingCard);
        if (!_turn) {
            Player ply = _gameTree.pop();
            _gameTree.add(ply);
            if (ply instanceof AI) {
                AI aiPlayer = (AI) ply;
                aiPlayer.addCard(_drawingCard);
                if (_dealerCard != null) {
                    aiPlayer.addDealerCard(_dealerCard);
                    _dealerCard = null;
                }
                aiPlayer.move();
            } else if (ply instanceof Dealer){
                Dealer dealer = (Dealer) ply;
                _dealerCard = _drawingCard;
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
        return _drawingCard;

    }

    /** Recounts the card count at the table. **/
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

    /** Clears the current table cards. **/
    public void clearTableCards() {
        _currentTableCards.removeAll(_currentTableCards);
        System.out.println(_currentTableCards.size());
    }

    /** Configures the additive cards. **/
    private void adderCards() {
        String[] cards = {"2", "3", "4", "5", "6"};
        _addOneCards.addAll(Arrays.asList(cards));
    }

    /** Configures the subtractive cards. **/
    private void subtractCards() {
        String[] cards = {"10", "J", "Q", "K", "A"};
        _subtractOneCards.addAll(Arrays.asList(cards));
    }

    /** A linked list of players in order, including the AIs and the dealer.**/
    private LinkedList<Player> _gameTree = new LinkedList<>();
    /** A list of card currently on the table. **/
    private ArrayList<Card> _currentTableCards = new ArrayList<>();
    /** A list of Players in the game. **/
    private ArrayList<Player> _players = new ArrayList<>();
    /** A list of AIs in the game. **/
    private ArrayList<AI> _ais = new ArrayList<>();
    /** A list of cards that add 1 to count/ **/
    private ArrayList<String> _addOneCards = new ArrayList<>();
    /** A list of cards that subtract 1 from count. **/
    private ArrayList<String> _subtractOneCards = new ArrayList<>();
    /** The game dealer. **/
    private Dealer _dealer;
    /** The Turn of the player. **/
    private Boolean _turn;
    /** The new card that is being drawn.**/
    private Card _drawingCard;
    /** A list of card currently in the game. **/
    private ArrayList<Card> _gameDeck;
    /** The first dealer card. **/
    private Card _dealerCard;
    /** The given deck of the new game. **/
    private Deck _deck;
    /** Current count of the cards. Initialized at 0. **/
    private int count = 0;
}
