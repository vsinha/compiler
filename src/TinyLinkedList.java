import java.util.*;

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
        }

        @Override public String toString () {
            return code;
        }
    }


    public TinyLinkedList(IRLinkedList irll, SymbolTableTree symbols) {
        head = new Node("");
        tail = head;

            Set<String> keys = symbols.global.table.keySet();
            for (String key : keys) {
                Id var = symbols.lookup(key);
                String varName = symbols.global.table.get(key).toString().split(" ")[1];
                if (var.isString()) {
                    this.addNode("str " + varName + " " + var.value);
                } else {
                    this.addNode("var " + varName);
                }
            }

        irll.head = irll.head.next;

        while( irll.head != null ){
            this.convertNode(irll.head);
            irll.head = irll.head.next;
        }
    }


    public void addNode(String code) {
        System.out.println(code);
        Node newNode = new Node(code);

        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }


    private void convertNode( IRLinkedList.Node inputNode){
        String[] nodeArray = {};
        String opcode = "";

        try { 
            nodeArray = inputNode.code.split(" ");
            opcode = nodeArray[0];
        } catch (NullPointerException e) {
            opcode = inputNode.code;
        }

        if (opcode == null) {
            this.addNode("error in tiny conversion: opcode is null");
        } else if(opcode.equals("LABEL")) {
            this.addNode("label " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("ADDI")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("addi " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("ADDF")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("addr " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("SUBI")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("subi " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("SUBF")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("subr " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("MULTI")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("muli " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("MULTF")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("mulr " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("DIVI")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("divi " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("DIVF")) {
            this.moveConversion(nodeArray[1], nodeArray[3]);
            this.addNode("divr " + this.convertRegister(nodeArray[2]) + " " 
                    + this.convertRegister(nodeArray[3]));
        } else if (opcode.equals("STOREI")) {
            this.moveConversion(nodeArray[1], nodeArray[2]);
        } else if (opcode.equals("STOREF")) {
            this.moveConversion(nodeArray[1], nodeArray[2]);
        } else if (opcode.equals("GT") || opcode.equals("GTI")) {
            this.addNode("cmpi " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jgt " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("GTF")) {
            this.addNode("cmpr " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jgt " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("GE") || opcode.equals("GEI")) {
            this.addNode("cmpi " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jge " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("GEF")) {
            this.addNode("cmpr " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jge " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("LT") || opcode.equals("LTI")) {
            this.addNode("cmpi " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jlt " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("LTF")) {
            this.addNode("cmpr " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jlt " + inputNode.code.split(" ")[3]);        
        } else if (opcode.equals("LE") || opcode.equals("LEI")) {
            this.addNode("cmpi " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jle " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("LEF")) {
            this.addNode("cmpr " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jle " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("NE") || opcode.equals("NEI")) {
            this.addNode("cmpi " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jne " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("NEF")) {
            this.addNode("cmpr " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jne " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("EQI") || opcode.equals("EQ")) {
            this.addNode("cmpi " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jeq " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("EQF")){
            this.addNode("cmpr " + this.convertRegister(inputNode.code.split(" ")[1]) 
                    + " " + this.convertRegister(inputNode.code.split(" ")[2]));
            this.addNode("jeq " + inputNode.code.split(" ")[3]);
        } else if (opcode.equals("JUMP")) {
            this.addNode("jmp " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("READI")) {
            this.addNode("sys readi " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("READF")) {
            this.addNode("sys readr " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("WRITEI")) {
            this.addNode("sys writei " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("WRITEF")) {
            this.addNode("sys writer " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("WRITES")) {
            this.addNode("sys writes " + inputNode.code.split(" ")[1]);
        } else if (opcode.equals("RET")) {
            this.addNode("sys halt");
        } else if (opcode.equals("LINK")) {
            //do nothing (maybe)
        } else {
            this.addNode("error in tiny conversion: opcode is: " + inputNode);
        }
    }

    private String convertRegister(String register){
        if (register.charAt(0) == '$'){
            String number = register.replace("$T", "");
            int registerNumber = Integer.parseInt(number) - 1;
            return "r" + String.valueOf(registerNumber);

        } else {
            return register;
        }
    }


    private void moveConversion(String operand1, String operand2){
        if (this.convertRegister(operand1).equals(operand1) 
                && this.convertRegister(operand2).equals(operand2)){
            this.addNode("error in tiny conversion: " 
                    + operand1 + " and " 
                    + operand2 + " are both variables. Node not added");
        } else {
            this.addNode("move " 
                    + this.convertRegister(operand1) + " " 
                    + this.convertRegister(operand2));
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
