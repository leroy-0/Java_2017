package server;

import java.util.Vector;

public class Team {

    private Vector<Player>  players = new Vector<>();
    private int             contractPoints = 0;
    private int             points = 0;
    private int             id;

    Team(int id)
    {
        this.id = id;
    }

    public boolean          getCoinche()
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (players.elementAt(i).getCoinche())
                return (true);
        }
        return (false);
    }

    public int              getContractPoints() { return (contractPoints); }

    public int              getId()
    {
        return (id);
    }

    public int              getPlayerNb()
    {
        return (players.size());
    }

    public Vector<Player>   getPlayers()
    {
        return (players);
    }

    public void             addPlayer(Player player)
    {
        players.addElement(player);
    }

    public void             removePlayer(Player player)
    {
        for (int i = 0; i < this.getPlayerNb(); i++)
        {
            if (players.elementAt(i).getId() == player.getId()) {
                players.remove(i);
                return ;
            }
        }
    }

    public void             resetPoints()
    {
        this.points = 0;
    }

    public void             setPoints(Vector<Team> teams)
    {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                points += teams.elementAt(i).getPlayers().elementAt(j).getBet().getValue();
                teams.elementAt(i).getPlayers().elementAt(j).removeBet();
            }
        }
    }

    public void             setContractCards(String type)
    {
        for (int i = 0; i < players.size(); i++)
        {
            players.elementAt(i).setContractCards(type);
        }
    }

    public void             setContractPoints(int value) { this.contractPoints += value; }

    public int              getPoints()
    {
        return (points);
    }
}
