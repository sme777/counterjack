public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Game game = new Game(deck);
        Card x = game.dealOne();
        Card y = game.dealOne();
        //Card z = game.dealOne();
        game.recount();
        game.clearTableCards();
        game.recount();
        System.out.println(x.concatenate());
        GUI _gui = new GUI();
        _gui.loadImage(x.concatenate());

        _gui.loadImage(y.concatenate());

        //_gui.loadImage(z.concatenate());
    }
}
