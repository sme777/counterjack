public class Card {

    /** Constructs a new Card class.
     * @param suit the given suit.
     * @param face the give face value.**/
    public Card(String suit, String face) {
        _suit = suit;
        _face = face;

    }

    /** Gets the suit of the card. **/
    String getSuit() {
        return _suit;
    }

    /** Gets the face of the card. **/
    String getFace() {
        return _face;
    }

    /** A given suit of a Card. **/
    private String _suit;
    /** A given face of a Card. **/
    private String _face;

    @Override
    /** Overrides default toString to face and suit of a card. **/
    public String toString() {
        return _face + " " + _suit;
    }

    /** Concatenates face and suit of a card.**/
    public String concatenate() {
        return _face + _suit;
    }
}
