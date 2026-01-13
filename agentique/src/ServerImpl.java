import java.net.*;
import java.io.*;
import java.util.*;

public class ServerImpl implements Server {
    private byte[] data;
    private int port;
    private Hashtable<String, Object> etatServeur = new Hashtable<>();

    public ServerImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() {
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

    @Override
    public Hashtable<String, Object> getEtatServeur() {
        return etatServeur;
    }

    @Override
    public void gererAgentEntrant(Socket socketClient) {
        try {
            InputStream is = socketClient.getInputStream();
            DataInputStream in = new DataInputStream(is);

            String className = in.readUTF();
            int classLen = in.readInt();
            byte[] classBytes = in.readNBytes(classLen);

            int objLen = in.readInt();
            byte[] objBytes = in.readNBytes(objLen);

            Loader loader = new Loader();
            loader.addClass(className, classBytes);

            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc)
                        throws IOException, ClassNotFoundException {
                    return loader.loadClass(desc.getName());
                }
            };

            Agent agent = (Agent) ois.readObject();
            if (data != null) {
                etatServeur.put("data", data);
            }

            agent.setNameServer(etatServeur);
            Thread agentThread = new Thread(() -> {
                try {
                    agent.main();
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            });
            agentThread.start();
            socketClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public void initiateData() {
        int tailleFichier = 1024 * 1024; // 1 Mo

        byte[] data = new byte[tailleFichier];
        Random r = new Random();
        for (int j = 0; j < tailleFichier; j++) {
            if (j < tailleFichier / 2) {
                data[j] = (byte)r.nextInt(256); // Aléatoire
            } else {
                data[j] = (byte)'A'; // Répétitif
            }
        }
    }
}
