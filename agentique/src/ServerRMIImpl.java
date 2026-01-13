import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerRMIImpl extends UnicastRemoteObject implements ServerRMI {
    private Hashtable<String, byte[]> baseDeDonnees = new Hashtable<>();

    public ServerRMIImpl() throws RemoteException {}

    @Override
    public byte[] getFichier(String name) throws RemoteException {
        return baseDeDonnees.get(name);
    }

    @Override
    public List<String> getCatalogue() throws RemoteException {
        return new ArrayList<>(baseDeDonnees.keySet());
    }

    public static void main(String[] args) throws RemoteException{

        ServerRMIImpl serveur = new ServerRMIImpl();
        //On fait la base de donnÃƒÂ©es
        int nbFichiers = 1;
        int tailleFichier = 1024 * 1024; // 1 Mo

        System.out.println("On génère " + nbFichiers + " fichiers");

        for (int i = 1; i <= nbFichiers; i++) {
            byte[] content = new byte[tailleFichier];
            Random r = new Random();
            for (int j = 0; j < tailleFichier; j++) {
                if (j < tailleFichier / 2) {
                    content[j] = (byte) r.nextInt(256); // AlÃƒÂ©atoire
                } else {
                    content[j] = (byte) 'A'; // RÃƒÂ©pÃƒÂ©titif
                }
            }
            serveur.baseDeDonnees.put("file_" + i + ".dat", content);
        }

        try{
            Registry registry = LocateRegistry.createRegistry(2002);
            Naming.rebind("//localhost:2002/ServeurRMIImpl", new ServerRMIImpl());
        }catch(Exception e) {

        }
    }
}