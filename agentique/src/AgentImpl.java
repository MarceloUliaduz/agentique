import java.io.ObjectOutputStream;
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
            Socket socket = new Socket(this.name, target.getPort());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //Envoi Object
            oos.writeObject(this);
            //Envoi Code (Loader.extractCode)
            Loader load = new Loader();
            oos.write(load.extractCode(this.name));
        } catch (Exception e) {
            throw new MoveException("jsp pourquoi mais ça marche pas");
        }
    }

    @Override
    public void back() throws MoveException {
        move(origin);
    }


    public void main() throws MoveException { // j'ai enlevé le [] args parce que jsp quoi mettre quand je l'appelle

    }

}
