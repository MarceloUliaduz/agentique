import java.io.*;
import java.net.Socket;
import java.util.Hashtable;

public class AgentImpl implements Agent {

    protected String name;
    protected Node origin;
    protected Hashtable<String, Object> nameServer;

    @Override
    public void init(String name, Node origin) {
        this.name = name;
        this.origin = origin;
    }

    @Override
    public void setNameServer(Hashtable<String, Object> ns) {
        this.nameServer = ns;
    }

    @Override
    public Hashtable<String, Object> getNameServer() {
        return this.nameServer;
    }

    @Override
    public void move(Node target) throws MoveException {
        try {
            Socket s = new  Socket(target.getName(), target.getPort());
            OutputStream os = s.getOutputStream();

            String className = this.getClass().getName();

            // Lecture code du .class
            String classPath = className.replace('.', '/') + ".class";;

            InputStream classStream = this.getClass().getClassLoader().getResourceAsStream(classPath);

            // Lire tous les bytes de la classe
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = classStream.read(buffer)) != -1) {
                byteStream.write(buffer, 0, bytesRead);
            }
            byte[] classBytes = byteStream.toByteArray();
            classStream.close();

            // Serialization, Object.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
            byte[] objectBytes = baos.toByteArray();

            DataOutputStream out = new DataOutputStream(os);

            // Envoie du nom de la classe
            out.writeUTF("AgentImpl");
            out.writeInt(classBytes.length);
            out.write(classBytes);

            // Envoi de l'objet.
            out.writeInt(objectBytes.length);
            out.write(objectBytes);
            s.close();

            //oos.write(load.extractCode(this.name));
        } catch (Exception e) {
            System.err.println("Exception bizarre: " + e.getClass().getName());
            e.printStackTrace();
            throw new MoveException("Erreur: " + e.getMessage());
        }
    }
    @Override
    public void back() throws MoveException {
        move(origin);
    }

    public void main() throws MoveException { // j'ai enlevé le [] args parce que jsp quoi mettre quand je l'appelle

    }

}
