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
            Agent agent = (Agent) ois.readObject();
            agent.setNameServer(etatServeur);

            // Exécuter le code de l'agent
            Thread fils = new Thread(() -> {
                try {
                    agent.main();
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            });
            fils.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void transfereAgent(Agent agent, Node suivant) throws MoveException {
        try {
            Socket socket = new Socket(suivant.getName(), suivant.getPort());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //TODO : là il faudrait envoyer avec le class loader dans le stream outuput j'imagine ? ou autre chose ?

        } catch (Exception e) {
            throw new MoveException("jsp pourquoi mais ça marche pas");
        }
    }


}
