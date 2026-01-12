import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerRMIImpl extends UnicastRemoteObject implements ServerRMI {
    private Hashtable<String, byte[]> baseDeDonnees = new Hashtable<>();

    public ServerRMIImpl() throws RemoteException {

        //On fait la base de données
        int nbFichiers = 10;
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
            baseDeDonnees.put("file_" + i + ".dat", content);
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