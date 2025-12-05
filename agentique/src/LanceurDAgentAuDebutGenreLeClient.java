import java.net.*;
import java.io.*;

public class LanceurDAgentAuDebutGenreLeClient {
    public static void main(String[] args) throws Exception{
        Hello hello = new Hello();
        hello.init("Hello", new Node("localhost", 2000));
        Socket socket = new Socket("localhost", 2001);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //TODO : comme l'autre là il faut le loader ? l'envoyer dans l'oos ?
        socket.close();
        System.out.println("premier lancé ok");
    }
}
