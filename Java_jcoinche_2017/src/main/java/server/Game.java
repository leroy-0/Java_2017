package server;

import org.apache.mina.core.session.IoSession;

import  java.util.Vector;

public class Game {
    private Vector<Team>         teams = new Vector<>();
    private Bid                  bid = new Bid();
    private Bet                  bet;
    private int                  id;

    Game(int id)
    {
        this.id = id;
        teams.addElement(new Team(1));
        teams.addElement(new Team(2));
    }

    private void    restart()
    {
        this.bid = new Bid();
        Turn.distribute(teams);
    }

    private boolean isReady()
    {
        return (this.getPlayerNb() >= Turn.getPlayerLimit());
    }

    private void    initGame()
    {
        Communication.sendChat(this.teams, "Game:Start");
        Turn.distribute(this.teams);
        Communication.sendChat(this.teams, "Contract:Start");
        Turn.getPlayerByTurn(this.teams, bid.getTurn()).sendMsg("Contract:YourTurn");
        Communication.sendChatEx(teams, Turn.getPlayerByTurn(teams, bid.getTurn()), "Player:" + Turn.getPlayerByTurn(teams, bid.getTurn()).getId() + ":Play");
    }

    public int      getPlayerNb()
    {
        int         playerNb = 0;

        for (int i = 0; i < teams.size(); i++) {
            playerNb += teams.elementAt(i).getPlayerNb();
        }
        return (playerNb);
    }

    public Vector<Team>    getTeams()
    {
        return (teams);
    }

    public Player       getPlayer(IoSession session)
    {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getSession() == session)
                    return (teams.elementAt(i).getPlayers().elementAt(j));
            }
        }
        return (null);
    }

    private boolean      betTurn(IoSession session, String action)
    {
        Team            winner;

        if (Turn.getPlayerByTurn(teams, bet.getTurn()).getId() == getPlayer(session).getId())
            getPlayer(session).sendMsg("Command:Received:Ok");
        bet.run(teams, this.getPlayer(session), action);
        winner = bet.getWinnerContract(teams, bid.getBidWinner(teams));
        if (winner != null) {
            if (winner.getCoinche() && bid.getBidWinner(teams).getId() == winner.getId())
                winner.setContractPoints(bid.getBidWinner(teams).getContract().getValue() * 2);
            else
                winner.setContractPoints(bid.getBidWinner(teams).getContract().getValue());
            Communication.sendChat(teams, "Team:" + winner.getId() + ":" + "Won:Bet" + ":" + winner.getContractPoints());
            this.restart();
            for (int i = 0; i < teams.size(); i++)
            {
                if (teams.elementAt(i).getPoints() >= Turn.getGamePointsLimit()) {
                    Communication.sendChat(teams, "Team:" + teams.elementAt(i).getId() + ":" + "Won:Game:" + teams.elementAt(i).getContractPoints());
                    return (true);
                }
            }
        }
        if (teams.size() > 0 && teams.elementAt(0).getPlayers().elementAt(0).getCards().size() == 0) {
            this.restart();
        }
        return (false);
    }

    private boolean     bidTurn(IoSession session, String action)
    {
        if (Turn.getPlayerByTurn(teams, bid.getTurn()).getId() == getPlayer(session).getId())
            getPlayer(session).sendMsg("Command:Received:Ok");
        bid.run(teams, this.getPlayer(session), action);
        if (!bid.isTurn())
        {
            for (int i = 0; i < teams.size(); i++) {
                teams.elementAt(i).setContractCards(bid.getBidWinner(teams).getContract().getType());
            }
            Communication.sendChat(this.teams, "Contract:End");
            Communication.sendChat(this.teams, "Player:" + bid.getBidWinner(teams).getId()
                    + ":" + bid.getBidWinner(teams).getContract().getValue()
                    + ":" + bid.getBidWinner(teams).getContract().getType());
            Communication.sendChat(this.teams, "Bet:Start");
            bet = new Bet(teams, bid.getBidWinner(teams));
            bid.getBidWinner(teams).sendMsg("Bet:YourTurn");
        }
        return (false);
    }

    public boolean     run(IoSession session, String action)
    {
        if (this.getPlayer(session) == null || action.isEmpty() || !isReady())
            return (false);

        if (action.substring(0, "Card:Info".length()).equals("Card:Info"))
            Communication.sendCardInfo(getPlayer(session));
        else if (action.substring(0, "Chat:".length()).equals("Chat:"))
            Communication.sendChatEx(teams, getPlayer(session), "Player:" + getPlayer(session).getId() + ":" + action);

        if (bid.isTurn()) {
            return (this.bidTurn(session, action));
        }
        return (this.betTurn(session, action));
    }

    public boolean  addPlayer(IoSession session)
    {
        if (isReady())
            return (false);

        int         nb;
        int         i = 0;

        while (i < teams.size())
        {
            if (teams.elementAt(i).getPlayerNb() < Turn.getPlayerLimit() / 2) {
                if (i != 0)
                    nb = teams.elementAt(i).getPlayerNb() + i + 2;
                else
                    nb = teams.elementAt(i).getPlayerNb() + i + 1;
                Player player = new Player(session, nb);
                teams.elementAt(i).addPlayer(player);
                Communication.sendChatEx(teams, player, "Player:" + player.getId() + ":Enter");
                player.sendMsg("Game:Enter #" + this.id);
                if (isReady())
                    this.initGame();
                else
                    player.sendMsg("Game:Wait");
                break;
            }
            i++;
        }
        return (true);
    }

    public boolean  removePlayer(IoSession session)
    {
        if (isReady())
            this.restart();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.elementAt(i).getPlayerNb(); j++) {
                if (teams.elementAt(i).getPlayers().elementAt(j).getSession() == session) {
                    Communication.sendChatEx(teams, teams.elementAt(i).getPlayers().elementAt(j),"Player:" + teams.elementAt(i).getPlayers().elementAt(j).getId() + ":Quit");
                    teams.elementAt(i).removePlayer(teams.elementAt(i).getPlayers().elementAt(j));
                    return (true);
                }
            }
        }
        return (false);
    }
}
