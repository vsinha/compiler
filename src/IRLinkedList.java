import java.util.LinkedList;

public class IRLinkedList {
    Node head;
    Node tail;

    class Node {
        Node prev;
        Node next;
        String code;

        public Node (String code) {
            this.code = code;
            this.prev = null;
            this.next = null;
        }

        @Override public String toString () {
            return code;
        }
    }

    public IRLinkedList() {
        head = new Node("");
        tail = head;
    }

    public void addNode(String code) {
        //System.out.println(code);
        Node newNode = new Node(code);

        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    public LinkedList<String> getLinkedList() {
       LinkedList<String> ll = new LinkedList<String>();

       Node curr = this.head;
       while (curr != null) {
           if (curr.code != null && !curr.code.isEmpty()) {
               ll.add(curr.code);
           }
           curr = curr.next;
       }

       return ll;
    }

    @Override
    public String toString() {
        return _toString(head.next);
    }

    private String _toString(Node current) {
        if (current == null) {
            return "";
        } else {
           return "\n" + ';' + current + _toString(current.next);
        }
    }
}
