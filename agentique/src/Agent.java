import java.io.Serializable;
import java.util.Hashtable;

public interface Agent extends Serializable {
    void init(String name, Node origin);
    void setNameServer(Hashtable<String, Object> ns);
    Hashtable<String, Object> getNameServer();
    void move(Node target) throws MoveException;
    void back() throws MoveException;
    void main(String[] args) throws MoveException;
}
