import java.util.ArrayList;

public class AI extends Player{

    public AI(Boolean doubleAfterSplit) {
        _doubleAfterSplit = doubleAfterSplit;
    }

    public void move() {
        if (_dealer.size() == 0) {
            System.out.println("Wait till your turn!");
        } else if (_ai.size() == 2 && _ai.get(0) == _ai.get(1)) {
            splitHelper();
        } else if (_ai.size() == 2 && (_ai.contains(new Card("H", "A")) || _ai.contains(new Card("C", "A"))
                || _ai.contains(new Card("D", "A")) || _ai.contains(new Card("S", "A")))) {
            softHelper();
        } else {
            hardHelper();
        }
    }
    public void addDealerCard(Card c) {
        _dealer.add(c);
    }

    public void addCard(Card c) {
        _ai.add(c);
    }

    public void removeCard(Card c) {
        _ai.remove(c);
    }

    public void removeAll() {
        _ai.removeAll(_ai);
        _dealer.removeAll(_ai);
    }

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

    private void configureDestination() {

    }

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

    private Boolean dealerComparator(String k) {
        return _dealer.get(0).getFace().equals(k);
    }

    private Boolean aiComparator(String k, int index) {
        return _ai.get(index).getFace().equals(k);
    }


    private Boolean _doubleAfterSplit;
    private ArrayList<Card> _dealer = new ArrayList<>();
    private ArrayList<Card> _ai = new ArrayList<>();
}
