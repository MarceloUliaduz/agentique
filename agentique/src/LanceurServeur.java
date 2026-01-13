import java.util.Hashtable;
import java.util.Random;

public class LanceurServeur {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Pas de port");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        // On fait la base de données
        int tailleFichier = 1024 * 1024; // 1 Mo

        System.out.println("On génère un fichier");

        byte[] content = new byte[tailleFichier];
        Random r = new Random();
        for (int j = 0; j < tailleFichier; j++) {
            if (j < tailleFichier / 2) {
                content[j] = (byte) r.nextInt(256); // Aléatoire
            } else {
                content[j] = (byte) 'A'; // Répétitif
            }
        }

        // On la met dans le serveur

        System.out.println("Base de données chargée dans DB_FILES");

        try {
            Server serv = new ServerImpl(port);
            serv.initiateData();
            serv.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
