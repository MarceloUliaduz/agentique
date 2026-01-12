import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPOutputStream;

public class AgentImage extends AgentImpl {
    boolean start = true;
    Node n1 = new Node("localhost",2001); // à modifier en focntion de la machine ENSEEIHT
    Node n2 = new Node("localhost",2002); // à modifier en focntion de la machine ENSEEIHT
    Node place = null;

    private Hashtable<String, byte[]> results = new Hashtable<>();

    public void main() throws MoveException {

        if (start) { // On commence par aller au serveur 1
            start = false;
            place = n1;
            System.out.println("Avant de lancer l'opération");
            move(n1);
        }
        else if (place != null && place.equals(n1)) { // on demande l'annuaire au serveur 1 et on va sur le 2
            place = n2;
            System.out.println("Demande le catalogue");
            // on demande l'annuaire au serveur 1
            Hashtable<String, byte[]> db = (Hashtable<String, byte[]>) getNameServer().get("DB_FILES");
            // On compresse les images
            if (db != null) {
                Enumeration<String> keys = db.keys();
                while (keys.hasMoreElements()) {
                    String fileName = keys.nextElement();
                    byte[] rawContent = db.get(fileName);

                    // On compresse et on stocke dans l'agent
                    results.put(fileName, compress(rawContent));
                    System.out.println("Fichier " + fileName + " compressé localement");
                }
            }
            System.out.println("Avant de retourner à la maison");
            back();
        }else if (place == null){ //name.hasMoreElements();
            System.out.println("de retour");
            for (String fileName : results.keySet()) {
                byte[] compressed = results.get(fileName);
                System.out.println("Fichier : " + fileName + " | Taille compressée : " + compressed.length + " bytes");
            }
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
