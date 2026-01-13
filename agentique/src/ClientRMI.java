import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class ClientRMI {
    public static void main(String[] args) {
        try {
            // Connexion au serveur 1

            System.out.println("on a pas encore fait lookup");
            ServerRMI serveur = (ServerRMI) Naming.lookup("rmi://localhost:2002/ServeurRMIImpl");
            System.out.println("on a fait lookup");


            // On commence la mesure de temps
            long startTime = System.currentTimeMillis();

            // On récupère le catalogue, puis on demande chaque image
            List<String> catalogue = serveur.getCatalogue();

            System.out.println("on a le catalogue");

            for (String name : catalogue) {
                byte[] rawData = serveur.getFichier(name);
                byte[] compressed = compress(rawData);
                //System.out.println("Fichier " + name + "compressé\n");
            }

            System.out.println("on a tout compressé");

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
            gos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Erreur lors de la compression : " + e.getMessage());
            return data; // En cas d'erreur, on renvoie la donnée non compressée
        }
    }
}