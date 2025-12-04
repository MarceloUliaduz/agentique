import java.util.Objects;

public class Node {
    private String serverName;
    private int port;

    public Node(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return port == node.port && Objects.equals(serverName, node.serverName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverName, port);
    }
}
