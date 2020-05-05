package server;

import java.util.Vector;

public class Bid {
    private boolean             bidTurn = true;
    private int                 turn = 0;

    public boolean  isTurn()
    {
        return (bidTurn);
    }

    public int      getTurn()
    {
        return (turn);
    }

    public void     checkBid(Vector<Team> teams)
    {
        int         nb = 0;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getContract().getValue() == 0)
                    nb++;
                else if (teams.elementAt(i).getPlayers().elementAt(j).getContract().getValue() == -1)
                    nb--;
            }
        }

        if (nb == Turn.getPlayerLimit() - 1) {
            this.bidTurn = false;
        }
    }

    public Player   getBidWinner(Vector<Team> teams)
    {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getContract().getValue() != 0)
                    return (teams.elementAt(i).getPlayers().elementAt(j));
            }
        }
        return (null);
    }

    public void     run(Vector<Team> teams, Player player, String to_bid)
    {
        if (to_bid.length() < ("Contract".length()) || !to_bid.substring(0, "Contract".length()).equals("Contract"))
            return ;

        player.setContract(to_bid);
        Communication.sendChatEx(teams, player, "Player:" + player.getId() + ":" + to_bid);
        this.turn = Turn.changeGiver(this.turn);
        this.checkBid(teams);
        if (this.isTurn())
        {
            Communication.sendChatEx(teams, Turn.getPlayerByTurn(teams, this.turn), "Player:" + Turn.getPlayerByTurn(teams, this.turn).getId() + ":Play");
            Turn.getPlayerByTurn(teams, this.turn).sendMsg("Contract:YourTurn");
        }
    }
}
