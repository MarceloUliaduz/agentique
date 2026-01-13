import java.io.Serializable;
import java.util.Hashtable;

public interface Agent extends Serializable {
    void init(String name, Node origin);

    void setNameServer(Hashtable<String, Object> ns);

    Hashtable<String, Object> getNameServer();

    void move(Node target) throws MoveException;

    void back() throws MoveException;

    void main() throws MoveException; // j'ai enlevé le [] args parce que jsp quoi mettre quand je l'appelle
}
