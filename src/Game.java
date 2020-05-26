import java.util.*;

public class Game {

    /** Constructs a new Game, with default one User Player
     * and a default one AI.
     * @param deck takes in a specified deck. **/
    public Game(Deck deck) {
        _deck = deck;
        _gameDeck = deck.getRandomDeck();
        _turn = true;
        _penetration = _deck.getPenetrationPosition();
        AI sampleAI = new AI("AI", true);
        Player samplePlayer = new Player("User Player");
        _ais.add(sampleAI);
        _players.add(samplePlayer);
        _dealer = new Dealer("Dealer");

        _gameTree.add(samplePlayer);
        _gameTree.add(sampleAI);
        _gameTree.add(_dealer);

        _playerStands.put(sampleAI, "hit");
        _playerStands.put(samplePlayer, "hit");
        _playerStands.put(_dealer, "hit");

        _playerCards.put(samplePlayer, new ArrayList<Card>());
        _playerCards.put(sampleAI, new ArrayList<Card>());
        _playerCards.put(_dealer, new ArrayList<Card>());

        _winnersAndLosers.put("winners", new ArrayList<Player>());
        _winnersAndLosers.put("losers", new ArrayList<Player>());
        _winnersAndLosers.put("break", new ArrayList<Player>());
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
        _penetration = _deck.getPenetrationPosition();
        while (players > 0) {
            Player samplePlayer = new Player("Player " + String.valueOf(players));
            _players.add(samplePlayer);
            _playerStands.put(samplePlayer, "hit");
            _gameTree.add(samplePlayer);
            _playerCards.put(samplePlayer, new ArrayList<Card>());
            players--;

        }
        while (ais > 0) {
            AI sampleAI = new AI("AI " + String.valueOf(ais), true);
            _ais.add(sampleAI);
            _playerStands.put(sampleAI, "hit");
            _gameTree.add(sampleAI);
            _playerCards.put(sampleAI, new ArrayList<Card>());
            ais--;
        }

        _dealer = new Dealer("Dealer");
        _playerStands.put(_dealer, "hit");
        _gameTree.add(_dealer);
        _playerCards.put(_dealer, new ArrayList<Card>());

        _winnersAndLosers.put("winners", new ArrayList<Player>());
        _winnersAndLosers.put("losers", new ArrayList<Player>());
        _winnersAndLosers.put("break", new ArrayList<Player>());

        adderCards();
        subtractCards();
    }

    /** The loop of the game, initiates a new game. **/
    public void startGame() {
        while (_gameDeck.size() > _penetration) {
            LinkedList<Player> playerSequence = (LinkedList<Player>) _gameTree.clone();
            while (playerSequence.size() != 1) {
                Player player = playerSequence.pop();
                playerSequence.add(player);
                if (_playerStands.get(player).equals(_bust)
                        || _playerStands.get(player).equals(_stand)
                        || _playerStands.get(player).equals(_surrender)
                        || _playerStands.get(player).equals(_double)
                        || _playerStands.get(player).equals(_blackjack)) {
                    playerSequence.remove(player);
                    _gameTree.remove(player);
                    if (_turn) {
                        _turn = false;
                    }
                } else {
                    dealOne();
                }
            }
            dealRest();
            recount();
            evaluateWinners();
            System.out.println(_winnersAndLosers);
            reset();
        }
        shuffle();
        startGame();


    }

    /** Deals one card to the current player. Saves the drawn card to the
     * current table cards, and removes from the deck. Waits for input for
     * a User Player or chooses action for AI and Dealer.
     * @return the drawing card. **/
    public Card dealOne() {
        if (_gameDeck == null) {
            System.out.println("An Error Occurred.");
        }
        if (_gameDeck.size() == 0) {
            shuffle();
        }
        if (!_turn) {
            Player ply = _gameTree.pop();
            _gameTree.add(ply);
            if (ply instanceof AI) {
                AI aiPlayer = (AI) ply;
                if (_playerCards.get(aiPlayer).size() > 1) {
                    String move = aiPlayer.move();
                    if (aiPlayer.handSum() > 21) {
                        _playerStands.put(aiPlayer, _bust);
                    } else if (move != null) {
                        _playerStands.put(aiPlayer, move);
                    }
                }
                if (_playerStands.get(aiPlayer).equals(_split)) {
                    split(aiPlayer);
                }

                if (!(_playerStands.get(aiPlayer).equals(_bust)
                        || _playerStands.get(aiPlayer).equals(_surrender)
                        || _playerStands.get(aiPlayer).equals(_stand)
                        || _playerStands.get(aiPlayer).equals(_blackjack))) {
                    _drawingCard = _gameDeck.get(0);
                    _gameDeck.remove(0);
                    System.out.println(ply.deal() + _drawingCard);
                    _currentTableCards.add(_drawingCard);
                    _playerCards.get(aiPlayer).add(_drawingCard);
                    aiPlayer.addCard(_drawingCard);
                    if (_dealerCard != null) {
                        aiPlayer.addDealerCard(_dealerCard);
                    }
                }
            } else if (ply instanceof Dealer && !_suspense) {
                _drawingCard = _gameDeck.get(0);
                _gameDeck.remove(0);
                System.out.println(ply.deal() + _drawingCard);
                _currentTableCards.add(_drawingCard);
                _playerCards.get(_dealer).add(_drawingCard);
                Dealer dealer = (Dealer) ply;
                dealer.addCard(_drawingCard);
                _dealerCard = _drawingCard;
                _playerStands.put(dealer, "suspense");
                _suspense = !_suspense;
            }
            if (!(_gameTree.getFirst() instanceof AI)
                    && !(_gameTree.getFirst() instanceof Dealer)) {
                _turn = !_turn;
                if (_playerCards.get(_gameTree.getFirst()).size() > 1 && ply.handSum() < 21) {
                    if (!(_playerStands.get(_gameTree.getFirst()).equals(_stand)
                            || _playerStands.get(_gameTree.getFirst()).equals(_bust)
                            || _playerStands.get(_gameTree.getFirst()).equals(_surrender)
                            || _playerStands.get(_gameTree.getFirst()).equals(_double))) {
                        Scanner myObj = new Scanner(System.in);
                        System.out.println("Player turn: ");
                        _playerChoice = myObj.nextLine();
                    }
                }
            }
        }  else {
            Player ply = _gameTree.pop();
            _gameTree.add(ply);
            _turn = !_turn;
            if (_playerChoice != null) {
                try {
                    scannerInterpreter(ply, _playerChoice);
                } catch (IllegalArgumentException e) {
                    e.fillInStackTrace();
                }
            }
            if (!(_playerStands.get(ply).equals(_bust)
                    || _playerStands.get(ply).equals(_surrender)
                    || _playerStands.get(ply).equals(_stand)
                    || _playerStands.get(ply).equals(_blackjack))) {
                _drawingCard = _gameDeck.get(0);
                _gameDeck.remove(0);
                System.out.println(ply.deal() + _drawingCard);
                _currentTableCards.add(_drawingCard);
                _playerCards.get(ply).add(_drawingCard);
                ply.addCard(_drawingCard);
                if (ply.handSum() > 21) {
                    _playerStands.put(ply, _bust);
                }
                return _drawingCard;
            }
        }
        return null;
    }

    // update the card list, stands etc.
    private void split(Player player) {
        System.out.println("This will split the pair and add a second hand for " + player.toString());
        if (player instanceof AI) {
            AI ai = (AI) player;
            AI newAi = new AI(ai.toString() + " Hand 2" , true);
            ai.rename(ai.toString() + " Hand 1");
            _gameTree.addFirst(newAi);
            _ais.add(newAi);
            Card splitCard = _playerCards.get(ai).remove(1);
            ArrayList<Card> splitPlayerArray = new ArrayList<>();
            splitPlayerArray.add(splitCard);
            _playerCards.put(newAi, splitPlayerArray);
            _playerStands.put(ai, _hit);
            _playerStands.put(newAi, _hit);
        } else {
            Player newPlayer = new Player(player.toString() + " Hand 2");
            player.rename(player.toString() + " Hand 1");
            _gameTree.add(_gameTree.indexOf(player) + 1, newPlayer);
            _players.add(newPlayer);
            Card splitCard = _playerCards.get(player).remove(1);
            ArrayList<Card> splitPlayerArray = new ArrayList<>();
            splitPlayerArray.add(splitCard);
            _playerCards.put(newPlayer, splitPlayerArray);
            _playerStands.put(newPlayer, _hit);
            _playerStands.put(player, _hit);
        }
    }

    /** Deals the rest of the cards to the dealer when all players and AIs have settled. **/
    private void dealRest() {
        int currCount = 0;
        for (String mood: _playerStands.values()) {
            if (mood.equals(_surrender) || mood.equals(_bust) || mood.equals(_stand)
                    || mood.equals(_double) || mood.equals(_blackjack)) {
                currCount++;
            }
        }
        if (currCount == _playerStands.size() - 1) {
            _suspense = !_suspense;

            while (_dealer.handSum() < 17) {
                if (_gameDeck.size() == 0) {
                    shuffle();
                }
                _drawingCard = _gameDeck.get(0);
                _gameDeck.remove(0);
                _dealer.hit();
                System.out.println(_dealer.deal() + _drawingCard);
                _currentTableCards.add(_drawingCard);
                _playerCards.get(_dealer).add(_drawingCard);
                _dealer.addCard(_drawingCard);

            }
        }
        System.out.println(_currentTableCards);
    }

    /** Interprets the result of user input.
     * @param player given player that is inputting.
     * @param cmd the supplied command of the player.**/
    private void scannerInterpreter(Player player, String cmd) throws IllegalArgumentException {
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
    private void recount() {
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

    /** Counts the value of a hand.
     * @param array supplied list of card,
     * @return the value of accumulated card. **/
    private int count(ArrayList<Card> array) {
        int sum = 0;
        ArrayList<Card> arr = new ArrayList<>();
        for (Card card : array) {
            if (card.getFace().equals("A")) {
                arr.add(card);
            } else if (card.getFace().equals("J") || card.getFace().equals("Q")
                    || card.getFace().equals("K")) {
                sum += 10;
            } else {
                sum += Integer.parseInt(card.getFace());
            }
        }
        if (arr.size() != 0) {
            if (arr.size() == 1) {
                if (sum + 11 > 21) {
                    sum += 1;
                } else {
                    sum += 11;
                }
            } else if (arr.size() == 2) {
                if (sum + 12 > 21) {
                    sum += 2;
                } else {
                    sum += 12;
                }
            } else if (arr.size() == 3) {
                if (sum + 13 > 21) {
                    sum += 3;
                } else {
                    sum += 13;
                }
            } else if (arr.size() == 4) {
                if (sum + 14 > 21) {
                    sum += 4;
                } else {
                    sum += 14;
                }
            } else {
                System.out.println("Unknown Exception.");
            }
        }
        return sum;
    }

    /** Evaluates winners and losers after each hand. **/
    private void evaluateWinners() {
        for (Player player : _playerCards.keySet()) {
            if (!(player instanceof Dealer)) {
                int handValue = count(_playerCards.get(player));
                int dealerValue = count(_playerCards.get(_dealer));
                if (handValue > dealerValue) {
                    if (handValue > 21) {
                        _winnersAndLosers.get("losers").add(player);
                    } else {
                        _winnersAndLosers.get("winners").add(player);
                    }
                } else {
                    if (dealerValue > 21) {
                        _winnersAndLosers.get("winners").add(player);
                    } else if (dealerValue == handValue) {
                        _winnersAndLosers.get("break").add(player);
                    } else {
                        _winnersAndLosers.get("losers").add(player);
                    }
                }
            }
        }
    }

    /** Resets the data structures of the game after a hand is dealt. **/
    private void reset() {
        _currentTableCards.removeAll(_currentTableCards);
        _winnersAndLosers.put("winners", new ArrayList<Player>());
        _winnersAndLosers.put("losers", new ArrayList<Player>());
        _winnersAndLosers.put("break", new ArrayList<Player>());
        _playerStands.replaceAll((p, v) -> _hit);
        _playerCards = new HashMap<>();
        _dealer.removeAll();
        _turn = true;

        _drawingCard = null;
        _dealerCard = null;
        _playerChoice = null;
        reconstructGameTree();

    }

    /** Reconstructs the game tree after a hand is dealt. **/
    private void reconstructGameTree() {
        _gameTree = new LinkedList<Player>();
        int playerNumber = 0;
        while (playerNumber < _players.size()) {
            _gameTree.add(_players.get(playerNumber));
            _playerCards.put(_players.get(playerNumber), new ArrayList<Card>());
            _players.get(playerNumber).removeAll();
            playerNumber++;
        }
        int aiNumber = 0;
        while (aiNumber < _ais.size()) {
            _gameTree.add(_ais.get(aiNumber));
            _playerCards.put(_ais.get(aiNumber), new ArrayList<Card>());
            _ais.get(aiNumber).removeAll();
            aiNumber++;
        }
        _playerCards.put(_dealer, new ArrayList<Card>());
        _gameTree.add(_dealer);
    }

    /** Shuffles the game deck. Occurs at the penetration level,
     * below if a hand is still in progress or at 0 when the
     * deck finishes while being dealt. **/
    private void shuffle() {
        System.out.println("Shuffling the deck!");
        if (_gameDeck.size() == _penetration
                || _gameDeck.size() == 0
                || _gameDeck.size() < _penetration) {
            _gameDeck = _deck.getRandomDeck();
        }
        count = 0;
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
    /** A map of winners, losers and break-evens after each hand. **/
    private HashMap<String, ArrayList<Player>> _winnersAndLosers = new HashMap<>();
    /** A map of where player stands during a hand. **/
    private HashMap<Player, String> _playerStands = new HashMap<>();
    /** A map of a player and the list of card currently holding. **/
    private HashMap<Player, ArrayList<Card>> _playerCards = new HashMap<>();
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
    /** Penetration level of the current deck. **/
    private int _penetration;
    /** Variable containing the String hit. **/
    private final String _hit = "hit";
    /** Variable containing the String stand. **/
    private final String _stand = "stand";
    /** Variable containing the String split **/
    private final String _split = "split";
    /** Variable containing the String double. **/
    private final String _double = "double";
    /** Variable containing the String don't split. **/
    private final String _notsplit = "don't split";
    /** Variable containing the String surrender. **/
    private final String _surrender = "surrender";
    /** Variable containing the String bust. **/
    private final String _bust = "bust";
    /** Variable containing the String blackjack. **/
    private final String _blackjack = "blackjack";
    /** A boolean corresponding whether the dealer is suspense/dealing card to players. **/
    private Boolean _suspense = false;
    /** An inputted result of the playing result, corresponds to decision during the game. **/
    private String _playerChoice;


}
