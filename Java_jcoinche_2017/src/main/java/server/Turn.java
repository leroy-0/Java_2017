package server;

import java.util.Random;
import java.util.Vector;

public class Turn {

    private static int                  maxCards = 32;
    private static int                  playerLimit = 4;
    private static int                  gamePointsLimit = 1000;

    public static int       getGamePointsLimit() { return(gamePointsLimit); }

    public static int       getPlayerLimit()
    {
        return (playerLimit);
    }

    public static int       getMaxCards()
    {
        return (playerLimit);
    }

    public static Player    getPlayerByTurn(Vector<Team> teams, int turn)
    {
        int           nb = 0;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (nb == turn)
                    return (teams.elementAt(i).getPlayers().elementAt(j));
                nb++;
            }
        }
        return (null);
    }

    public static int       changeGiver(int turn)
    {
        turn++;
        if (turn >= playerLimit)
            turn = 0;
        return (turn);
    }

    public static void      distribute(Vector<Team> teams)
    {
        Vector<ACard>       deck = Turn.initDeck();
        Random              random = new Random();
        ACard               card;
        int                 index;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                teams.elementAt(i).getPlayers().elementAt(j).removeCards();
                teams.elementAt(i).getPlayers().elementAt(j).setCoinche(false);
                teams.elementAt(i).getPlayers().elementAt(j).removeBet();
                teams.elementAt(i).resetPoints();
                for (int k = 0; k < (maxCards / playerLimit); k++) {
                    index = random.nextInt() % deck.size();
                    if (index < 0)
                        index *= -1;
                    card = deck.elementAt(index);
                    deck.remove(index);
                    teams.elementAt(i).getPlayers().elementAt(j).setCard(card);
                    teams.elementAt(i).getPlayers().elementAt(j).sendMsg("Card:"+ card.getClass().getSimpleName()
                            + ":" + card.getName() + ":" + card.getValue());
                }
            }
        }
    }

    private static Vector<ACard>    initDeck()
    {
        Vector<ACard>  cards = new Vector<>();

        cards.addElement(new Heart("As", 11, false));
        cards.addElement(new Heart("Roi", 4, false));
        cards.addElement(new Heart("Dame", 3, false));
        cards.addElement(new Heart("Valet", 2, false));
        cards.addElement(new Heart("10", 10, false));
        cards.addElement(new Heart("9", 0, false));
        cards.addElement(new Heart("8", 0, false));
        cards.addElement(new Heart("7", 0, false));

        cards.addElement(new Tile("As", 11, false));
        cards.addElement(new Tile("Roi", 4, false));
        cards.addElement(new Tile("Dame", 3, false));
        cards.addElement(new Tile("Valet", 2, false));
        cards.addElement(new Tile("10", 10, false));
        cards.addElement(new Tile("9", 0, false));
        cards.addElement(new Tile("8", 0, false));
        cards.addElement(new Tile("7", 0, false));

        cards.addElement(new Clover("As", 11, false));
        cards.addElement(new Clover("Roi", 4, false));
        cards.addElement(new Clover("Dame", 3, false));
        cards.addElement(new Clover("Valet", 2, false));
        cards.addElement(new Clover("10", 10, false));
        cards.addElement(new Clover("9", 0, false));
        cards.addElement(new Clover("8", 0, false));
        cards.addElement(new Clover("7", 0, false));

        cards.addElement(new Pike("As", 11, false));
        cards.addElement(new Pike("Roi", 4, false));
        cards.addElement(new Pike("Dame", 3, false));
        cards.addElement(new Pike("Valet", 2, false));
        cards.addElement(new Pike("10", 10, false));
        cards.addElement(new Pike("9", 0, false));
        cards.addElement(new Pike("8", 0, false));
        cards.addElement(new Pike("7", 0, false));
        return (cards);
    }
}
