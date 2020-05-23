public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Game game = new Game(deck);
        game.startGame();
    }
}
