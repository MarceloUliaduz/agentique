import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LanceurClient {
    public static void main(String[] args) throws Exception{
        if (args.length < 2) {
            System.out.println("Arg 1 : port, Arg 2 : .jar");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);
        String filename = args[1];

        ZipFile zf = new ZipFile(filename);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
        }

        Agent agent = new Hello();

        agent.init(Hello.class.getName(), new Node("localhost", port));
        agent.main();

        System.out.println("premier lancé ok");

        try {
            Server.start(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
