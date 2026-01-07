public class LanceurServeur {

    public static void main(String[] args){
        if (args.length < 1) {
            System.out.println("Pas de port");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        try {
            Server.start(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
