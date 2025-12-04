import java.net.ServerSocket;

public class ServerImpl implements Server {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            Server serv = new ServerImpl();

            ServerSocket ss = new ServerSocket(port);

            System.out.println("Server démaréééé sur le port : " + port);

            while (true) {
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
