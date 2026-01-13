import java.io.*;
import java.net.Socket;
import java.util.Hashtable;

public interface Server {

    int BUFF_SIZE = 2048;
    void start();
    Hashtable<String, Object> getEtatServeur();
    void gererAgentEntrant(Socket socketClient);
    byte[] getData();
    void initiateData();
}
