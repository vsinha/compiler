public class TinyLinkedList {
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
            //this.opcode = opcode;

        }

        @Override public String toString () {
            return code;
        }
    }

    public TinyLinkedList(IRLinkedList irll) {


        head = new Node("");
        tail = head;


        //add all the symbols from the symbol table


        irll.head = irll.head.next;

        while( irll.head != null ){
            

            this.convertNode(irll.head);

            irll.head = irll.head.next;
        }


    }

    public void addNode(String code) {
        //System.out.println(code);
        Node newNode = new Node(code);

        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }



    private void convertNode( IRLinkedList.Node inputNode){

        String[] nodeArray = inputNode.code.split(" ");
        String opcode = nodeArray[0];

        if(opcode.equals("LABEL")){
            this.addNode("label " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("ADDI")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("addi " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("ADDF")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("addr " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("SUBI")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("subi " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("SUBF")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("subr " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("MULTI")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("muli " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("MULTF")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("mulr " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("DIVI")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("divi " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("DIVF")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[3]));
            this.addNode("divr " + this.convertRegister(nodeArray[2]) + " " + this.convertRegister(nodeArray[3]));
        }else if (opcode.equals("STOREI")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[2]));
        }else if (opcode.equals("STOREF")) {
            this.addNode("move " + this.convertRegister(nodeArray[1]) + " " + this.convertRegister(nodeArray[2]));
        }else if (opcode.equals("GT")) {
            this.addNode("jgt " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("GE")) {
            this.addNode("jge " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("LT")) {
            this.addNode("jlt " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("LE")) {
            this.addNode("jle " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("NE")) {
            this.addNode("jne " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("EQ")) {
            this.addNode("jeq " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("JUMP")) {
            this.addNode("jmp " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("READI")) {
            this.addNode("sys readi " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("READF")) {
            this.addNode("sys readr " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("WRITEI")) {
            this.addNode("sys writei " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("WRITEF")) {
            this.addNode("sys writer " + inputNode.code.split(" ")[1]);
        }else if (opcode.equals("RET")) {
            this.addNode("sys halt");
        }else if (opcode.equals("LINK")){
            //do nothing (maybe)
        }else{
            this.addNode("error in tiny conversion " + inputNode);
        }
    }

    private String convertRegister(String register){
        if (register.charAt(0) == '$'){
            String number = register.replace("$T", "");

            int registerNumber = Integer.parseInt(number) - 1;

            return "r" + String.valueOf(registerNumber);
        }else{
            return register;
        }
    }


    @Override
    public String toString() {
        return _toString(head.next);
    }

    private String _toString(Node current) {
        if (current == null) {
            return "";
        } else {
           return "\n" + current + _toString(current.next);
        }
    }
}