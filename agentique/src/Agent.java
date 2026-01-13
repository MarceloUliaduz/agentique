import java.io.Serializable;
import java.util.Hashtable;

public interface Agent extends Serializable {
    /**
     * Permet d'initialiser un agent.
     *
     * @param name   nom de la classe agent.
     * @param origin noeud d'origine
     */
    void init(String name, Node origin);

    /**
     * Getter.
     * Permet d'obtenir la table des données server
     *
     * @return la table
     */
    Hashtable<String, Object> getNameServer();

    /**
     * Setter.
     * Permet de redéfinir la table des données server
     *
     * @param ns la nouvelle table
     */
    void setNameServer(Hashtable<String, Object> ns);

    /**
     * Se déplacer à sur un autre noeud
     *
     * @param target
     * @throws MoveException
     */
    void move(Node target) throws MoveException;

    /**
     * Retourne au noeud d'origine
     *
     * @throws MoveException
     */
    void back() throws MoveException;

    void main() throws MoveException; // j'ai enlevé le [] args parce que jsp quoi mettre quand je l'appelle
}
