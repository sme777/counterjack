import java.util.ArrayList;

public class AI extends Player{

    /** Constructor of AI.
     * @param doubleAfterSplit configure
     * if double after split is allowed. **/
    public AI(String name, Boolean doubleAfterSplit) {
        super(name);
        _name = name;
        _doubleAfterSplit = doubleAfterSplit;
    }

    /** The move the AI will make based on the heuristics of perfect startegy. **/
    public String move() {
        if (_dealer.size() == 0) {
            return null;
        } else if (_ai.size() == 2 && _ai.get(0) == _ai.get(1)) {
            return splitHelper();
        } else if (_ai.size() == 2 && isASoftHand()) {
                if (!surrenderHelper()) {
                    softHelper();
                }
        }
        else {
            if (!surrenderHelper()) {
                return hardHelper();
            }
        }
        return null;
    }

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
                return split();
            case "K":
            case "Q":
            case "J":
            case "10":
                return notSplit();
            case "9":
                if (dealerComparator("7") || dealerComparator("10")
                        || dealerComparator("A") || dealerComparator("J")
                        || dealerComparator("Q") || dealerComparator("K")) {
                    return notSplit();
                } else {
                    return split();
                }

            case "8":
                return split();
            case "7":

                if (dealerComparator("8") || dealerComparator("9")
                        || dealerComparator("10") || dealerComparator("J")
                        || dealerComparator("Q") || dealerComparator("K")
                        || dealerComparator("A")) {
                    return notSplit();
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
                    return notSplit();
                } else {
                    return split();
                }
            case "5":
                return notSplit();
            case "4":
                if (_doubleAfterSplit) {
                    if (dealerComparator("5") || dealerComparator("6")) {
                        return split();
                    } else {
                        return notSplit();
                    }
                } else {
                    return notSplit();
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
                    return notSplit();
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
                    return notSplit();
                }
            default:

                System.out.println("Wrong config.");
                return null;
        }
    }

    /** Configures the correct move based on the sum of hand
     * based on the fact that the hand is soft. Calls the necessary
     * function based on perfect strategy. **/
    private String softHelper() {
        if (_ai.size() == 2) {
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
            } else if (aiComparator("6",0) || aiComparator("6", 1)) {
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

                System.out.println("Another wrong conflict");
                return null;
            }
        } else {
            System.out.println("Not yet configured setting");
            return null;
        }
    }

    /** Configures the correct move based on the sum of hand
     * based on the fact that the hand is hard. Calls the necessary
     * function based on perfect strategy. **/
    private String hardHelper() {
        int handCount = handSum(_ai);
        if (handCount == 17) {
            return stand();
        } else if (handCount == 16) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (handCount == 15) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (handCount == 14) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (handCount == 13) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (handCount == 12) {
            if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                return stand();
            } else {
                return hit();
            }
        } else if (handCount == 11) {
            return doubleHand();
        } else if (handCount == 10) {
            if (dealerComparator("10") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K")
                    || dealerComparator("A")) {
                return hit();
            } else {
                return doubleHand();
            }
        } else if (handCount == 9) {
            if (dealerComparator("3") || dealerComparator("4") ||
                    dealerComparator("5") || dealerComparator("6")) {
                return doubleHand();
            } else {
                return hit();
            }
        } else if (handCount == 8) {
            return hit();
        } else if (handCount > 17) {
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

    private void configureDestination() {

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

    @Override
    public String toString() {
        return  _name +" gets: ";
    }

    private String _name;
    /** A given game parameter, by default is true. **/
    private Boolean _doubleAfterSplit;
    /** A list of dealer cards. Logic is made from the first card only. **/
    private ArrayList<Card> _dealer = new ArrayList<>();
    /** A list of AI cards at the table. **/
    private ArrayList<Card> _ai = new ArrayList<>();
}
