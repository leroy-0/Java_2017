package test;

import java.io.IOException;

public class GameManager {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        if (args.length == 2)
        {
            new Client(args[0], Integer.parseInt(args[1])).connectToServer();
        }
        else
            System.out.println("Usage : java -jar client [IP] [PORT]");
    }

}
