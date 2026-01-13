public class Hello extends AgentImpl {

  boolean start = true;
  Node n1 = new Node("localhost", 2001);
  Node n2 = new Node("localhost", 2002);
  Node place = null;

  public void main() throws MoveException {

    if (start) {
      start = false;
      place = n1;
      System.out.println("before move to node1: Hello World !!!!");
      move(n1);
    } else if (place != null && place.equals(n1)) {
      place = n2;
      System.out.println("before move to node2: Hello World !!!!");
      move(n2);
    } else if (place != null && place.equals(n2)) {
      place = null;
      System.out.println("before back: Hello World !!!!");
      back();
    } else if (place == null) {
      System.out.println("de retour");
    }

    // Object o = getNameServer().get(this.getClass().getName()+"_lock");
    // synchronized(o) {o.notify();}
  }
}
