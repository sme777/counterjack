import java.util.ArrayList;

public class AI extends Player{

    public AI(Boolean doubleAfterSplit) {
        _doubleAfterSplit = doubleAfterSplit;
    }

    void move() throws Exception {
        if (_dealer.size() == 0) {
            throw new Exception();
        }
        if (_ai.size() == 2 && _ai.get(0) == _ai.get(1)) {
            splitHelper();
        } else if (_ai.contains(new Card("H", "A")) || _ai.contains(new Card("C", "A"))
                || _ai.contains(new Card("D", "A")) || _ai.contains(new Card("S", "A"))) {
            softHelper();
        } else {
            hardHelper();
        }
    }


    void splitHelper() throws Exception{
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

            if (_dealer.get(0).getFace().equals("8") || _dealer.get(0).getFace().equals("9")
                    || _dealer.get(0).getFace().equals("10") || _dealer.get(0).getFace().equals("J")
                    || _dealer.get(0).getFace().equals("Q") || _dealer.get(0).getFace().equals("K")
                    || _dealer.get(0).getFace().equals("A")) {
                notSplit();
            } else {
                split();
            }
        } else if (_ai.get(0).getFace().equals("6")) {
            if (_doubleAfterSplit && _dealer.get(0).getFace().equals("2")) {
                split();
            }
            if (_dealer.get(0).getFace().equals("7") || _dealer.get(0).getFace().equals("8")
                    || _dealer.get(0).getFace().equals("9") || _dealer.get(0).getFace().equals("10")
                    || _dealer.get(0).getFace().equals("J") || _dealer.get(0).getFace().equals("Q")
                    || _dealer.get(0).getFace().equals("K") || _dealer.get(0).getFace().equals("A")) {
                notSplit();
            } else {
                split();
            }
        } else if (_ai.get(0).getFace().equals("5")) {
            notSplit();
        } else if (_ai.get(0).getFace().equals("4")) {
            if (_doubleAfterSplit) {
                if (_dealer.get(0).getFace().equals("5") || _dealer.get(0).getFace().equals("6")) {
                    split();
                } else {
                    notSplit();
                }
            } else {
                notSplit();
            }
        } else if (_ai.get(0).getFace().equals("3")) {
            if (_doubleAfterSplit && (_dealer.get(0).getFace().equals("2")
                    || _dealer.get(0).getFace().equals("3"))) {
                split();
            }
            if (_dealer.get(0).getFace().equals("4") || _dealer.get(0).getFace().equals("5")
                    || _dealer.get(0).getFace().equals("6") || _dealer.get(0).getFace().equals("7")) {
                split();
            } else {
                notSplit();
            }
        } else if (_ai.get(0).getFace().equals("2")) {
            if (_doubleAfterSplit && (_dealer.get(0).getFace().equals("2")
                    || _dealer.get(0).getFace().equals("3"))) {
                split();
            }
            if (_dealer.get(0).getFace().equals("4") || _dealer.get(0).getFace().equals("5")
                    || _dealer.get(0).getFace().equals("6") || _dealer.get(0).getFace().equals("7")) {
                split();
            } else {
                notSplit();
            }
        } else {
            throw new Exception();
        }
    }

    void softHelper() {

    }

    void hardHelper() {
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

    int handSum(ArrayList<Card> array) {
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

    Boolean dealerComparator(String k) {
        return _dealer.get(0).getFace().equals(k);
    }


    Boolean _doubleAfterSplit;
    ArrayList<Card> _dealer;
    ArrayList<Card> _ai;
}
