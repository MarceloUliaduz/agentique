import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public interface Server {

    int BUFF_SIZE = 2048;
    Hashtable<String, Object> etatServeur = new Hashtable<>();

    static void start(int port) {
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

    static void gererAgentEntrant(Socket socketClient) {
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
            agent.setNameServer(etatServeur);
            Thread agentThread = new Thread(() -> {
                try {
                    agent.main();
                } catch (MoveException e) {
                    e.printStackTrace();
                }
            });
            agentThread.start(); // on lance le main de l'agent
            socketClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
