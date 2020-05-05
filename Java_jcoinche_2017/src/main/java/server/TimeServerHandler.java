package server;
import java.util.Date;
import  java.util.Vector;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter
{
    private Vector<Game>        games = new Vector<>();

    TimeServerHandler()
    {
        games.addElement(new Game(games.size()));
    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {

    }

    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
        String  str = message.toString();
        int     i = 0;

        if( str.trim().equalsIgnoreCase("quit") ) {
            session.closeNow();
            return;
        }

        while (i < games.size()) {
            if (games.elementAt(i).run(session, str))
            {
                Game            newGame = new Game(i);
                Vector<Team>    teams = games.elementAt(i).getTeams();
                for (int j = 0; j < teams.size(); j++)
                {
                    for (int k = 0; k < teams.elementAt(j).getPlayerNb(); k++) {
                        newGame.addPlayer(teams.elementAt(j).getPlayers().elementAt(k).getSession());
                    }
                }
                games.add(i, newGame);
            }
            i++;
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception
    {

        for (int i = 0; i < games.size(); i++)
        {
            if (games.elementAt(i).addPlayer(session))
                return;
        }
        games.addElement(new Game(games.size()));
        games.elementAt(games.size() - 1).addPlayer(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {
        for (int i = 0; i < games.size(); i++)
        {
            if (games.elementAt(i).removePlayer(session))
                return;
        }
    }
}