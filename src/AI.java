import java.util.ArrayList;

public class AI extends Player{

    /** Constructor of AI.
     * @param doubleAfterSplit configure
     * if double after split is allowed. **/
    public AI(Boolean doubleAfterSplit) {
        _doubleAfterSplit = doubleAfterSplit;
    }

    /** The move the AI will make based on the heuristics of perfect startegy. **/
    public void move() {
        if (_dealer.size() == 0) {
            System.out.println("Wait till your turn!");
        } else if (_ai.size() == 2 && _ai.get(0) == _ai.get(1)) {
            splitHelper();
        } else if (_ai.size() == 2 && (_ai.contains(new Card("H", "A")) || _ai.contains(new Card("C", "A"))
                || _ai.contains(new Card("D", "A")) || _ai.contains(new Card("S", "A")))) {
            if (!surrenderHelper()) {
                softHelper();
            }
        } else {
            if (!surrenderHelper()) {
                hardHelper();
            }
        }
    }

    /** Adds a new card to the hand of the Dealer.
     * Occurs when a card is dealt to the Dealer.
     * @param c the given card needed to be added.**/
    public void addDealerCard(Card c) {
        _dealer.add(c);
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
    private void splitHelper() {
        if (_ai.get(0).getFace().equals("A")) {
            split();
        } else if (_ai.get(0).getFace().equals("K") || _ai.get(0).getFace().equals("Q")
                || _ai.get(0).getFace().equals("J") || _ai.get(0).getFace().equals("10")) {
            notSplit();
        } else if (_ai.get(0).getFace().equals("9")) {
            if (dealerComparator("7") || dealerComparator("10")
                    || dealerComparator("A") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K")) {
                notSplit();
            } else {
                split();
            }

        } else if (_ai.get(0).getFace().equals("8")) {
            split();
        } else if (_ai.get(0).getFace().equals("7")) {

            if (dealerComparator("8") || dealerComparator("9")
                    || dealerComparator("10") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K")
                    || dealerComparator("A")) {
                notSplit();
            } else {
                split();
            }
        } else if (_ai.get(0).getFace().equals("6")) {
            if (_doubleAfterSplit && dealerComparator("2")) {
                split();
            }
            if (dealerComparator("7") || dealerComparator("8")
                    || dealerComparator("9") || dealerComparator("10")
                    || dealerComparator("J") || dealerComparator("Q")
                    || dealerComparator("K") || dealerComparator("A")) {
                notSplit();
            } else {
                split();
            }
        } else if (_ai.get(0).getFace().equals("5")) {
            notSplit();
        } else if (_ai.get(0).getFace().equals("4")) {
            if (_doubleAfterSplit) {
                if (dealerComparator("5") || dealerComparator("6")) {
                    split();
                } else {
                    notSplit();
                }
            } else {
                notSplit();
            }
        } else if (_ai.get(0).getFace().equals("3")) {
            if (_doubleAfterSplit && (dealerComparator("2")
                    || dealerComparator("3"))) {
                split();
            }
            if (dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6") || dealerComparator("7")) {
                split();
            } else {
                notSplit();
            }
        } else if (_ai.get(0).getFace().equals("2")) {
            if (_doubleAfterSplit && (dealerComparator("2")
                    || dealerComparator("3"))) {
                split();
            }
            if (dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6") || dealerComparator("7")) {
                split();
            } else {
                notSplit();
            }
        } else {
            System.out.println("Wrong config.");
        }
    }

    /** Configures the correct move based on the sum of hand
     * based on the fact that the hand is soft. Calls the necessary
     * function based on perfect strategy. **/
    private void softHelper() {
        if (_ai.size() == 2) {
            if (aiComparator("9", 0) || aiComparator("9", 1)) {
                stand();
            } else if (aiComparator("8", 0) || aiComparator("8", 1)) {
                if (dealerComparator("6")) {
                    doubleHand();
                } else {
                    stand();
                }
            } else if (aiComparator("7", 0) || aiComparator("7", 1)) {
                if (dealerComparator("9") || dealerComparator("10") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K") || dealerComparator("A")) {
                    hit();
                } else if (dealerComparator("7") || dealerComparator("8")) {
                    stand();
                } else {
                    doubleHand();
                }
            } else if (aiComparator("6",0) || aiComparator("6", 1)) {
                if (dealerComparator("3") || dealerComparator("4")
                        || dealerComparator("5") || dealerComparator("6")) {
                    doubleHand();
                } else {
                    hit();
                }
            } else if (aiComparator("5", 0) || aiComparator("5", 1)) {
                if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                    doubleHand();
                } else {
                    hit();
                }
            } else if (aiComparator("4", 0) || aiComparator("4", 1)) {
                if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                    doubleHand();
                } else {
                    hit();
                }
            } else if (aiComparator("3", 0) || aiComparator("3", 1)) {
                if (dealerComparator("5") || dealerComparator("6")) {
                    doubleHand();
                } else {
                    hit();
                }
            } else if (aiComparator("2", 0) || aiComparator("2", 1)) {
                if (dealerComparator("5") || dealerComparator("6")) {
                    doubleHand();
                } else {
                    hit();
                }
            } else {
                System.out.println("Another wrong conflict");
            }
        }
    }

    /** Configures the correct move based on the sum of hand
     * based on the fact that the hand is hard. Calls the necessary
     * function based on perfect strategy. **/
    private void hardHelper() {
        int handCount = handSum(_ai);
        if (handCount == 17) {
            stand();
        } else if (handCount == 16) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                stand();
            } else {
                hit();
            }
        } else if (handCount == 15) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                stand();
            } else {
                hit();
            }
        } else if (handCount == 14) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                stand();
            } else {
                hit();
            }
        } else if (handCount == 13) {
            if (dealerComparator("2") || dealerComparator("3")
                    || dealerComparator("4") || dealerComparator("5")
                    || dealerComparator("6")) {
                stand();
            } else {
                hit();
            }
        } else if (handCount == 12) {
            if (dealerComparator("4") || dealerComparator("5") || dealerComparator("6")) {
                stand();
            } else {
                hit();
            }
        } else if (handCount == 11) {
            doubleHand();
        } else if (handCount == 10) {
            if (dealerComparator("10") || dealerComparator("J")
                    || dealerComparator("Q") || dealerComparator("K")
                    || dealerComparator("A")) {
                hit();
            } else {
                doubleHand();
            }
        } else if (handCount == 9) {
            if (dealerComparator("3") || dealerComparator("4") ||
                    dealerComparator("5") || dealerComparator("6")) {
                doubleHand();
            } else {
                hit();
            }
        } else if (handCount == 8) {
            hit();
        } else if (handCount > 17) {
            stand();
        } else {
            hit();
        }
    }

    /** Configures if AI should surrender.
     * @return true if needs to surrender. **/
    private Boolean surrenderHelper() {
        int handCount = handSum(_ai);
        if (handCount == 16 && (dealerComparator("9") || dealerComparator("10")
                || dealerComparator("J") || dealerComparator("Q")
                || dealerComparator("K") || dealerComparator("A"))) {
            surrender();
            return true;
        } else if (handCount == 15 && (dealerComparator("10")
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

    /** A given game parameter, by default is true. **/
    private Boolean _doubleAfterSplit;
    /** A list of dealer cards. Logic is made from the first card only. **/
    private ArrayList<Card> _dealer = new ArrayList<>();
    /** A list of AI cards at the table. **/
    private ArrayList<Card> _ai = new ArrayList<>();
}
