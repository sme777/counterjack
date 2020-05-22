public class Card {

    public Card(String suit, String face) {
        _suit = suit;
        _face = face;

    }

    String getSuit() {
        return _suit;
    }

    String getFace() {
        return _face;
    }
    /** A given suit of a Card. **/
    private String _suit;
    /** A given face of a Card. **/
    private String _face;

    @Override
    public String toString() {
        return _face + " " + _suit;
//                "Card{" +
//                "_suit='" + _suit + '\'' +
//                ", _name='" + _name + '\'' +
//                '}';
    }

    public String concatenate() {
        return _face + _suit;
    }
}
