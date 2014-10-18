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
        head = null;
        tail = null;
    }

    public void addNode(String code) {
        Node newNode = new Node(code);
        System.out.println(code);

        if (head == null && tail == null) { // no nodes in list
          head = newNode;
          tail = newNode;
        } else { // put the node at the end of the list
          newNode.prev = tail;
          tail.next = newNode;
          tail = newNode;
        }
    }

    @Override
    public String toString() {
        return head + _toString(head.next);
    }

    private String _toString(Node current) {
        if (current == null) {
            return "";
        } else {
           return current + _toString(current.next);
        }
    }
}
