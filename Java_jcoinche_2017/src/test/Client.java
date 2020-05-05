package test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Client {

    private static int PORT = 9123;
    private static String IP = "localhost";
    private static ClientHandler client;
    private static IoConnector connector;

    private static ConnectFuture future;


    public Client(String IpArgs, int PortArgs)
    {
        connector = new NioSocketConnector();
        client = new ClientHandler("Hello Server");
        connector.getSessionConfig().setReadBufferSize(2048);
        IP = IpArgs;
        PORT = PortArgs;
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.setHandler(client);
        future = connector.connect(new InetSocketAddress(IP,PORT));
        future.awaitUninterruptibly();

        if (!future.isConnected())
        {
            return;
        }


    }


    public void connectToServer()
    {

        IoSession session = future.getSession();
        session.getConfig().setUseReadOperation(true);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                client.sendMessage(session, "quit");
                System.out.print("Exiting the Program!");
                connector.dispose(); }
        });
            client.sendMessage(session, "");
            client.sendMessage(session, "");
            client.sendMessage(session, "");
            client.sendMessage(session, "");
            client.sendMessage(session, "");
        connector.dispose();
    }
}
