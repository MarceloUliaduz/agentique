import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerRMI extends Remote {
    // Récupérer le fichier de nom name
    byte[] getFichier(String name) throws RemoteException;

    // Récupérer une liste de noms de fichiers pour choisir
    List<String> getCatalogue() throws RemoteException;
}

