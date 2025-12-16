import java.net.*;
import java.io.*;
import java.util.*;

public class ServerImpl implements Server{
    private int port;
    private static final int BUFF_SIZE = 2048;
    private static Hashtable<String, Object> etatServeur = new Hashtable<>();

    public ServerImpl(int port) {
        this.port = port;
    }

    public static void start(int port) {


        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server démaré sur le port : " + port);

            while (true) {
                Socket cs = ss.accept(); // cs pour ClientSocket
                Thread fils = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            gererAgentEntrant(cs);
                        } catch (MoveException e) {
                            e.printStackTrace();
                        }
                    }
                });

                fils.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gererAgentEntrant(Socket socketClient) {
        try {
            ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());
            // TODO:Réception du code Agent (ns) avec le Class Loader
            // TODO:a = réception de l'objet Agent

            // Exécuter le code de l'agent
            Thread fils = new Thread(() -> {
                try {
                    // TODO:a.ns.main
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            });
            fils.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
