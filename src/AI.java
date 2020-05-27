import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {

    /** Constructor of AI.
     * @param doubleAfterSplit configure
     * if double after split is allowed. **/
    public AI(String name, Boolean doubleAfterSplit) {
        super(name);

        _doubleAfterSplit = doubleAfterSplit;
        _betHeuristic = 0.01;
        _bet = getBankroll() * _betHeuristic;
    }

    public AI(String name, Boolean doubleAfterSplit, double bankroll) {
        super(name, bankroll);

        _doubleAfterSplit = doubleAfterSplit;
        _betHeuristic = 0.01;
        _bet = getBankroll() * _betHeuristic;
    }

    /** The move the AI will make based on the heuristics of perfect startegy. **/
    public String move() {
        if (_dealer.size() == 0) {
            return null;
        } else if (_ai.size() == 2 && _ai.get(0).getFace().equals(_ai.get(1).getFace())) {
            return splitHelper();
        } else if (_ai.size() == 2 && isASoftHand()) {
            if (surrenderHelper()) {
                return "surrender";
            } else {
                return softHelper(_ai);
            }
        } else {
            if (surrenderHelper()) {
                return "surrender";
            } else {
                return hardHelper(handSum(_ai));
            }
        }

    }

    /** Evaluates if the hand is soft i.e. includes ace(s). **/
    private Boolean isASoftHand() {
        for (Card c: _ai) {
            if (c.getFace().equals("A")) {
                return true;
            }
        }
        return false;
    }
    

    /** Adds a new card to the hand of the Dealer.
     * Occurs when a card is dealt to the Dealer.
     * @param c the given card needed to be added.**/
    public void addDealerCard(Card c) {
        if (_dealer.size() == 0) {
            _dealer.add(c);
        }
    }

    /** Adds a new card to the hand of the AI.
     * Occurs when a card is dealt to the AI.
     * @param c the given card needed to be added.**/
    public void addCard(Card c) {
        _ai.add(c);
    }

    /** Removes a specific card. Occurs during splits.
     * @param c the given card needed to be removed. **/
    public void removeCard(Card c) {
        _ai.remove(c);
    }

    /** Removes all dealer and AI cards. Occurs at each new hand.**/
    public void removeAll() {
        _ai.removeAll(_ai);
        _dealer.removeAll(_ai);
    }

    /** Configures whether the hand needs to be split.
     *  Calls necessary functions based on perfect strategy. **/
    private String splitHelper() {
        switch (_ai.get(0).getFace()) {
            case "A":
            case "8":
                return split();
            case "K":
            case "Q":
            case "J":
            case "10":
            case "5":
                notSplit();
                return hardHelper(20);
            case "9":
                if (dealerComparator("7") || dealerComparator("10")
                        || dealerComparator("A") || dealerComparator("J")
                        || dealerComparator("Q") || dealerComparator("K")) {
                    notSplit();
                    return hardHelper(18);
                } else {
                    return split();
                }
            case "7":

                if (dealerComparator("8") || dealerComparator("9")
                        || dealerComparator("10") || dealerComparator("J")
                        || dealerComparator("Q") || dealerComparator("K")
                        || dealerComparator("A")) {
                    notSplit();
                    return hardHelper(14);
                } else {
                    return split();
                }
            case "6":
                if (_doubleAfterSplit && dealerComparator("2")) {
                    return split();
                }
                if (dealerComparator("7") || dealerComparator("8")
                        || dealerComparator("9") || dealerComparator("10")
                        || dealerComparator("J") || dealerComparator("Q")
                        || dealerComparator("K") || dealerComparator("A")) {
                    notSplit();
                    return hardHelper(12);
                } else {
                    return split();
                }
            case "4":
                if (_doubleAfterSplit) {
                    if (dealerComparator("5") || dealerComparator("6")) {
                        return split();
                    } else {
                        notSplit();
                        return hardHelper(8);
                    }
                } else {
                    notSplit();
                    return hardHelper(8);
                }
            case "3":
                if (_doubleAfterSplit && (dealerComparator("2")
                        || dealerComparator("3"))) {
                    return split();
                }
                if (dealerComparator("4") || dealerComparator("5")
                        || dealerComparator("6") || dealerComparator("7")) {
                    return split();
                } else {
                    notSplit();
                    return hardHelper(6);
                }
            case "2":
                if (_doubleAfterSplit && (dealerComparator("2")
                        || dealerComparator("3"))) {
                    return split();
                }
                if (dealerComparator("4") || dealerComparator("5")
                        || dealerComparator("6") || dealerComparator("7")) {
                    return split();
                } else {
                    notSplit();
                    hardHelper(4);
                }
            default:
                System.out.println("Wrong config.");
                return null;
        }
    }

    /** Configures the correct move based on the sum of hand
     * based on the fact that the hand is soft. Calls the necessary
     * function based on perfect strategy. **/
    private String softHelper(ArrayList<Card> array) {
        if (array.size() == 2) {
            if (aiComparator("9", 0) || aiComparator("9", 1)) {
                return stand();
            } else if (aiComparator("8", 0) || aiComparator("8", 1)) {
                if (dealerComparator("6")) {
                    return doubleHand();
                } else {
                    return stand();
                }
            } else if (aiComparator("7", 0) || aiComparator("7", 1)) {
                if (dealerComparator("9") || dealerComparator("10") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K") || dealerComparator("A")) {
                    return hit();
                } else if (dealerComparator("7") || dealerComparator("8")) {
                    return stand();
                } else {
                    return doubleHand();
                }
            } else if (aiComparator("6", 0) || aiComparator("6", 1)) {
                if (dealerComparator("3") || dealerComparator("4")
                        || dealerComparator("5") || dealerComparator("6")) {
                    return doubleHand();
                } else {
                    return hit();
                }
            } else if (aiComparator("5", 0) || aiComparator("5", 1)) {
                if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                    return doubleHand();
                } else {
                    return hit();
                }
            } else if (aiComparator("4", 0) || aiComparator("4", 1)) {
                if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                    return doubleHand();
                } else {
                    return hit();
                }
            } else if (aiComparator("3", 0) || aiComparator("3", 1)) {
                if (dealerComparator("5") || dealerComparator("6")) {
                    return doubleHand();
                } else {
                    return hit();
                }
            } else if (aiComparator("2", 0) || aiComparator("2", 1)) {
                if (dealerComparator("5") || dealerComparator("6")) {
                    return doubleHand();
                } else {
                    return hit();
                }
            } else {
                return blackjack();
            }
        } else {
            ArrayList<Card> aces = new ArrayList<>();
            int score = 0;
            for (Card c: _ai) {
                if (!c.getFace().equals("A")) {
                    if (c.getFace().equals("J")
                            || c.getFace().equals("Q")
                            || c.getFace().equals("K")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(c.getFace());
                    }
                } else {
                    aces.add(c);
                }
            }
            if (aces.size() == 1) {
                if (score + 11 > 21) {
                    score += 1;
                    return hardHelper(score);
                } else {
                    ArrayList<Card> arr = new ArrayList<>();
                    arr.add(new Card("M", "A"));
                    arr.add(new Card("M", String.valueOf(score)));
                    score += 11;
                    return softHelper(arr);
                }
            } else if (aces.size() == 2) {
                if (score + 12 > 21) {
                    score += 2;
                    return hardHelper(score);
                } else {
                    ArrayList<Card> arr = new ArrayList<>();
                    arr.add(new Card("M", "A"));
                    arr.add(new Card("M", String.valueOf(score + 1)));
                    score += 12;
                    return softHelper(arr);
                }
            } else if (aces.size() == 3) {
                if (score + 13 > 21) {
                    score += 3;
                    return hardHelper(score);
                } else {
                    ArrayList<Card> arr = new ArrayList<>();
                    arr.add(new Card("M", "A"));
                    arr.add(new Card("M", String.valueOf(score + 2)));
                    score += 13;
                    return softHelper(arr);

                }
            } else if (aces.size() == 4) {
                if (score + 14 > 21) {
                    score += 4;
                    hardHelper(score);
                } else {
                    ArrayList<Card> arr = new ArrayList<>();
                    arr.add(new Card("M", "A"));
                    arr.add(new Card("M", String.valueOf(score + 3)));
                    score += 14;
                    return softHelper(arr);
                }
            }
        }
        return null;
    }

    /** Configures the correct move based on the sum of hand
     * based on the fact that the hand is hard. Calls the necessary
     * function based on perfect strategy. **/
    private String hardHelper(int value) {

        if (value == 17) {
            return stand();
        } else if (value == 16) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (value == 15) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (value == 14) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (value == 13) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (value == 12) {
            if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (value == 11) {
            return doubleHand();
        } else if (value == 10) {
            if (dealerComparator("10") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K")
                    || dealerComparator("A")) {
                return hit();
            } else {
                return doubleHand();
            }
        } else if (value == 9) {
            if (dealerComparator("3") || dealerComparator("4")
                    || dealerComparator("5") || dealerComparator("6")) {
                return doubleHand();
            } else {
                return hit();
            }
        } else if (value == 8) {
            return hit();
        } else if (value > 17) {
            if (value == 21) {
                return blackjack();
            } else if (value > 21) {
                return bust();
            }
            return stand();
        } else {
            return hit();
        }
    }

    /** Configures if AI should surrender.
     * @return true if needs to surrender. **/
    private Boolean surrenderHelper() {
        int handCount = handSum(_ai);
        if (handCount == 16 && !isASoftHand() && (dealerComparator("9") || dealerComparator("10")
                || dealerComparator("J") || dealerComparator("Q")
                || dealerComparator("K") || dealerComparator("A"))) {
            surrender();
            return true;
        } else if (handCount == 15 && !isASoftHand() && (dealerComparator("10")
                || dealerComparator("J") || dealerComparator("Q")
                || dealerComparator("K"))) {
            surrender();
            return true;
        } else {
            return false;
        }
    }

    /** Calculates the sum of the current hand.
     * @param array a given array list
     * @return value of hand sum. **/
    private int handSum(ArrayList<Card> array) {
        int sum = 0;
        for (Card card : array) {
            if (card.getFace().equals("A")) {
                sum += 11;
            } else if (card.getFace().equals("J") || card.getFace().equals("Q")
                    || card.getFace().equals("K")) {
                sum += 10;
            } else {
                sum += Integer.parseInt(card.getFace());
            }
        }
        return sum;
    }

    public double bet() {
        Random rand = new Random();
        int count = _betChoice.length;
        int actualBet = rand.nextInt(count);
        _bet = _betChoice[actualBet] * _bet;
        return _bet;
    }

    public double getBet() {
        return _bet;
    }

    public void resetInitialBet() {
        _bet = getBankroll() * _betHeuristic;
    }

    /** Compares the value of a card to that of the dealer.
     * @param k the string value of the given card.  **/
    private Boolean dealerComparator(String k) {
        return _dealer.get(0).getFace().equals(k);
    }

    /** Compares the value of a card to a value of a
     * card in the hand at a specific index.
     * @param index of the card,
     * @param k the string value of the card **/
    private Boolean aiComparator(String k, int index) {
        return _ai.get(index).getFace().equals(k);
    }

    /** A given game parameter, by default is true. **/
    private Boolean _doubleAfterSplit;

    /** A list of dealer cards. Logic is made from the first card only. **/
    private ArrayList<Card> _dealer = new ArrayList<>();
    /** A list of AI cards at the table. **/
    private ArrayList<Card> _ai = new ArrayList<>();
    //private double _bankroll;
    private double _betHeuristic;
    private double _bet;
    private final int[] _betChoice = {1, 2, 3, 4, 5, 6};
}
