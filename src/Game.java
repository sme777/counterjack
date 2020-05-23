import java.util.*;

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

        _playerStands.put(sampleAI, "hit");
        _playerStands.put(samplePlayer, "hit");
        _playerStands.put(_dealer, "hit");
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
        while (players > 0) {
            Player samplePlayer = new Player();
            _players.add(samplePlayer);
            _playerStands.put(samplePlayer, "hit");
            _gameTree.add(samplePlayer);
            players--;

        }
        while (ais > 0) {
            AI sampleAI = new AI(true);
            _ais.add(sampleAI);
            _playerStands.put(sampleAI, "hit");
            _gameTree.add(sampleAI);
            ais--;
        }

        _dealer = new Dealer();
        _playerStands.put(_dealer, "hit");
        _gameTree.add(_dealer);
    }

    public void startGame() {
//        for (int i = 0; i < _players.size(); i++) {
//
//
//
//            while (!(_playerStands.get(_players.get(i)).equals(_stand)
//                    || _playerStands.get(_players.get(i)).equals(_bust)
//                    || _playerStands.get(_players.get(i)).equals(_surrender))) {
//                dealOne();
//            }
//
//            while (!(_playerStands.get(_ais.get(i)).equals(_stand)
//                    || _playerStands.get(_ais.get(i)).equals(_bust)
//                    || _playerStands.get(_ais.get(i)).equals(_surrender))) {
//                dealOne();
//            }
//        }

        LinkedList<Player> playerSequence = (LinkedList<Player>) _gameTree.clone();
        while (playerSequence.size() != 1) {
            Player player = playerSequence.pop();
            playerSequence.add(player);
            if (_playerStands.get(player).equals(_bust)
                    || _playerStands.get(player).equals(_stand)
                    || _playerStands.get(player).equals(_surrender)) {
                playerSequence.remove(player);
            } else {
                dealOne();
            }
        }
        dealRest();
        recount();



    }

    /** Deals one card to the current player. Saves the drawn card to the
     * current table cards, and removes from the deck. Waits for input for
     * a User Player or chooses action for AI and Dealer.
     * @return the drawing card. **/
    public Card dealOne() {
        if (_gameDeck == null) {
            System.out.println("An Error Occurred.");
        }

        if (!_turn) {
            round++;
            Player ply = _gameTree.pop();
            _gameTree.add(ply);
            if (ply instanceof AI) {
                AI aiPlayer = (AI) ply;
                    _drawingCard = _gameDeck.get(0);
                    _gameDeck.remove(0);
                    System.out.println(ply.toString() + _drawingCard);
                    _currentTableCards.add(_drawingCard);

                    aiPlayer.addCard(_drawingCard);
                    if (_dealerCard != null) {
                        aiPlayer.addDealerCard(_dealerCard);
                        _dealerCard = null;
                    }
                    String move = aiPlayer.move();
                    if (aiPlayer.handSum() > 21) {
                        _playerStands.put(aiPlayer, _bust);
                    } else if (move != null) {
                        _playerStands.put(aiPlayer, move);
                    }

            } else if (ply instanceof Dealer && !_suspense){
                _drawingCard = _gameDeck.get(0);
                _gameDeck.remove(0);
                System.out.println(ply.toString() + _drawingCard);
                _currentTableCards.add(_drawingCard);

                Dealer dealer = (Dealer) ply;
                dealer.addCard(_drawingCard);
                _dealerCard = _drawingCard;
                dealer.move();
                _playerStands.put(dealer, "suspense");
                _suspense = !_suspense;
            }
            if (!(_gameTree.getFirst() instanceof AI || _gameTree.getFirst() instanceof Dealer)) {
                _turn = !_turn;
            }
        }  else {

                round++;
                Player ply = _gameTree.pop();
                _gameTree.add(ply);
                _drawingCard = _gameDeck.get(0);
                _gameDeck.remove(0);
                System.out.println(ply.toString() + _drawingCard);
                _currentTableCards.add(_drawingCard);
                ply.addCard(_drawingCard);
                if (round > _players.size() && ply.handSum() < 21) {
                    Scanner myObj = new Scanner(System.in);
                    System.out.println("Player turn: ");
                    String command = myObj.nextLine();
                    try {
                        scannerInterpreter(ply, command);
                    } catch (IllegalArgumentException e) {
                        e.fillInStackTrace();
                    }
                }
                if (ply.handSum() > 21) {
                    _playerStands.put(ply, _bust);
                }

                _turn = !_turn;

            return _drawingCard;
            }
        return null;
    }

    private void dealRest() {
        int count = 0;
        for (String mood: _playerStands.values()) {
            if (mood.equals(_surrender) || mood.equals(_bust) || mood.equals(_stand)) {
                count++;
            }
        }
        if (count == _playerStands.size() - 1) {
            _suspense = !_suspense;
            while (_dealer.handSum() < 17) {
                _drawingCard = _gameDeck.get(0);
                _gameDeck.remove(0);
                System.out.println(_dealer.toString() + _drawingCard);
                _currentTableCards.add(_drawingCard);

                _dealer.addCard(_drawingCard);
                _dealer.hit();
            }
        }

        System.out.println(_currentTableCards);
    }

    private void scannerInterpreter(Player player, String cmd) throws IllegalArgumentException{
        _playerStands.put(player, cmd);
        switch (cmd) {
            case _hit:
                player.hit();
                break;
            case _stand:
                player.stand();
                break;
            case _split:
                player.split();
                break;
            case _double:
                player.doubleHand();
                break;
            case _notsplit:
                player.notSplit();
                break;
            case _surrender:
                player.surrender();
                break;
            default:
                throw new IllegalArgumentException();
        }
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

    private HashMap<Player, String> _playerStands = new HashMap<>();

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

    private int round = 0;

    private final String _hit = "hit";

    private final String _stand = "stand";

    private final String _split = "split";

    private final String _double = "double";

    private final String _notsplit = "don't split";

    private final String _surrender = "surrender";

    private final String _bust = "bust";

    private Boolean _suspense = false;


}
