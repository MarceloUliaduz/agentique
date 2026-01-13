import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class ServerRMIImpl extends UnicastRemoteObject implements ServerRMI {
    private Hashtable<String, byte[]> baseDeDonnees = new Hashtable<>();

    public ServerRMIImpl() throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException {

        ServerRMIImpl serveur = new ServerRMIImpl();
        //On fait la base de données
        int nbFichiers = 500;
        int tailleFichier = 1024 * 1024; // 1 Mo

        System.out.println("On génère " + nbFichiers + " fichiers");

        for (int i = 1; i <= nbFichiers; i++) {
            byte[] content = new byte[tailleFichier];
            Random r = new Random();
            for (int j = 0; j < tailleFichier; j++) {
                if (j < tailleFichier / 2) {
                    content[j] = (byte) r.nextInt(256); // Aléatoire
                } else {
                    content[j] = (byte) 'A'; // Répétitif
                }
            }
            serveur.baseDeDonnees.put("file_" + i + ".dat", content);
        }

        try {
            LocateRegistry.createRegistry(2002);
            Naming.rebind("//localhost:2002/ServeurRMIImpl", serveur);
        } catch (Exception e) {

        }
    }

    @Override
    public byte[] getFichier(String name) throws RemoteException {
        return baseDeDonnees.get(name);
    }

    @Override
    public List<String> getCatalogue() throws RemoteException {
        return new ArrayList<>(baseDeDonnees.keySet());
    }
}