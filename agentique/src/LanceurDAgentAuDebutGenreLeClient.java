import java.net.*;
import java.io.*;

public class LanceurDAgentAuDebutGenreLeClient {
    public static void main(String[] args) throws Exception{
        if (args.length < 1) {
            System.out.println("Pas de port");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        Agent hello = new Hello();

        hello.init("Hello", new Node("localhost", port));
        hello.main();

        System.out.println("premier lancé ok");
    }
}
