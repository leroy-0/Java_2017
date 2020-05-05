package test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter
{
    private final String values;
    private boolean finished;

    public ClientHandler(String values)
    {
        this.values = values;
    }

    public boolean isFinished()
    {
        return finished;
    }

    @Override
    public void sessionOpened(IoSession session)
    {
        session.write(values);
    }


    @Override
    public void sessionClosed(IoSession session)
    {
        System.out.print("Connection Lost.\nStopping the client\n");
        session.closeNow();
        Runtime.getRuntime().exit(10);
    }

    public void sendMessage(IoSession session, String message)
    {
        session.write(message);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
    {
        System.out.println("Received -> " + message.toString());
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
    {
        session.closeNow();
    }

}