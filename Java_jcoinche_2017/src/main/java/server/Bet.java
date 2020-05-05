package server;

import java.util.Vector;

public class Bet {
    private Player          winner;
    private int             turn;

    Bet(Vector<Team> teams, Player winner)
    {
        this.winner = winner;
        this.setWinnerTurn(teams);
    }

    public int      getTurn()
    {
        return (turn);
    }

    private boolean     isEndTurn(Vector<Team> teams)
    {
        int             nb = 0;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getBet() != null)
                    nb++;
            }
        }
        return (nb >= Turn.getPlayerLimit() - 1);
    }

    private void        setWinnerTurn(Vector<Team> teams)
    {
        int             nb = 0;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getId() == this.winner.getId()) {
                    this.turn = nb;
                    return;
                }
                nb++;
            }
        }
    }

    private void        setPoints(Vector<Team> teams)
    {
        int             teamIndex = 0;
        int             maxValue = 0;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getBet().getValue() > maxValue) {
                    maxValue = teams.elementAt(i).getPlayers().elementAt(j).getBet().getValue();
                    teamIndex = i;
                }
            }
        }
        teams.elementAt(teamIndex).setPoints(teams);
        for (int i = 0; i < teams.size(); i++) {
            Communication.sendChat(teams, "Team:" + teams.elementAt(i).getId() + ":" + teams.elementAt(i).getPoints());
        }
    }

    public  Team        getWinnerContract(Vector<Team> teams, Player contractHolder)
    {
        int             indexwinner = -1;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPoints() >= contractHolder.getContract().getValue())
                    indexwinner = i;
            }
        }

        if (indexwinner != -1) {
            for (int i = 0; i < teams.size(); i++) {
                for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                    teams.elementAt(i).resetPoints();
                    if (teams.elementAt(i).getCoinche() && i != indexwinner) {
                        indexwinner = i;
                    }
                }
            }
            return (teams.elementAt(indexwinner));
        }
        return (null);
    }

    public void         run(Vector<Team> teams, Player player, String to_bet)
    {
        if (to_bet.length() < "Bet".length() || !to_bet.substring(0, "Bet".length()).equals("Bet"))
            return ;

        player.setBet(to_bet);
        this.turn = Turn.changeGiver(this.turn);
        if (this.isEndTurn(teams))
            this.setPoints(teams);
        else
            Turn.getPlayerByTurn(teams, this.turn).sendMsg("Bet:YourTurn");
    }
}
