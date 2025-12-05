import java.util.Objects;

public class Node {
    private String name;
    private int port;

    public Node(String name, int port) {
        this.name = name;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return port == node.port && Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, port);
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(final int port) {
        this.port = port;
    }
}
