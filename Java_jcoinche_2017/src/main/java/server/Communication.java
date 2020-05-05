package server;

import java.util.Vector;

public class Communication {

    public static void    sendChat(Vector<Team> teams, String msg)
    {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                teams.elementAt(i).getPlayers().elementAt(j).sendMsg(msg);
            }
        }
    }

    public static void     sendChatEx(Vector<Team> teams, Player player, String msg)
    {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getId() != player.getId())
                    teams.elementAt(i).getPlayers().elementAt(j).sendMsg(msg);
            }
        }
    }

    public static void     sendCardInfo(Player player)
    {
        Vector<ACard> cards;
            cards = player.getCards();
            for (int k = 0; k < cards.size(); k++)
            {
                player.sendMsg("Card:"+ cards.elementAt(k).getClass().getSimpleName()
                        + ":" + cards.elementAt(k).getName() + ":" + cards.elementAt(k).getValue());
            }
            }
}


