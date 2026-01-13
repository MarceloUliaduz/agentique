import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LanceurClient {
    public static void main(String[] args) throws Exception{
        if (args.length < 3) {
            System.out.println("Utilisation : \n LanceurClient [port] [.jar] [nomDuProgrammeAgent]");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        String filename = args[1];
        String agentName = args[2];

        try {
            Class<?> cls = Class.forName(agentName);
            Agent agent = (Agent) cls.getDeclaredConstructor().newInstance();

            agent.init(agentName, new Node("localhost", port));
            agent.main();

            Server serv = new ServerImpl(port);
            serv.initiateData();
            serv.start();
        } catch (ClassNotFoundException e) {
            System.err.println("Programme " + e.getMessage() + " non trouvé");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
