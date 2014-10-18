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
        Node newNode = new Node(code);

        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    @Override
    public String toString() {
        System.out.println("here");
        
        return "test" + _toString(head.next);
    }

    private String _toString(Node current) {
        if (current == null) {
            return "";
        } else {
           return current + _toString(current.next);
        }
    }
}
