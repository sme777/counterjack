public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck(1);
        Game game = new Game(deck, 5, 1);
        game.startGame();
    }
}
