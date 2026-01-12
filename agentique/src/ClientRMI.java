import java.rmi.Naming;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class ClientRMI {
    public static void main(String[] args) {
        try {
            // Connexion au serveur 1
            ServerRMI serveur = (ServerRMI) Naming.lookup("rmi://147.127.135.71:2002/ServeurRMIImpl");

            // On commence la mesure de temps
            long startTime = System.currentTimeMillis();

            // On récupère le catalogue, puis on demande chaque image
            List<String> catalogue = serveur.getCatalogue();

            for (String name : catalogue) {
                byte[] rawData = serveur.getFichier(name);
                byte[] compressed = compress(rawData);
                System.out.println("Fichier " + name + "compressé\n");
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Temps total RMI pour " + catalogue.size() + " appels : " + (endTime - startTime) + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] compress(byte[] data) {
        if (data == null || data.length == 0) {
            return data;
        }

        try { // compression à revoir
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gos = new GZIPOutputStream(baos);
            gos.write(data);
            gos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Erreur lors de la compression : " + e.getMessage());
            return data; // En cas d'erreur, on renvoie la donnée non compressée
        }
    }
}