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
        //TODO: implémenter move
        ServerImpl.transfereAgent(this,target);
    }

    @Override
    public void back() throws MoveException {
        move(origin);
    }

    @Override
    public void main() throws MoveException { // j'ai enlevé le [] args parce que jsp quoi mettre quand je l'appelle

    }

}
