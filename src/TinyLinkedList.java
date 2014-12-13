import java.util.*;

public class TinyLinkedList {
    Node head;
    Node tail;

    static final int NUMREGISTERS = 15;

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

    SymbolTableTree symbols;

    public TinyLinkedList(IRLinkedList irll, SymbolTableTree symbols) {
        head = new Node("");
        tail = head;
        this.symbols = symbols;

        // add strings
        Set<String> keys = symbols.global.table.keySet();
        for (String key : keys) {
            Id var = symbols.lookup(key);
            String varName = symbols.global.table.get(key).toString().split(" ")[1];
            if (var.isString()) {
                addNode("str " + varName + " " + var.value);
            } else {
                addNode("var " + varName);
            }
        }

        irll.head = irll.head.next;

        while( irll.head != null ){
            convertNode(irll.head);
            irll.head = irll.head.next;
        }
        addNode("end");
    }


    public void addNode(String code) {
        //System.out.println(code);
        Node newNode = new Node(code);

        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    // check this flag to know that we should jump over other function
    // declarations to start in 'main'
    private boolean finishedAddingGlobals = false;
    private String currentFunction = "";

    private void convertNode( IRLinkedList.Node inputNode){
        String[] nodeArray = {};
        String opcode = "";

        try { 
            nodeArray = inputNode.code.split(" ");
            opcode = nodeArray[0];
        } catch (NullPointerException e) {
            opcode = inputNode.code;
        }

        String[] tokens = inputNode.code.split(" ");

        if (opcode == null) {
            addNode("error in tiny conversion: opcode is null");
        } else if(opcode.equals("LABEL")) {
            String labelName = tokens[1];
            // check to jump over other function declarations
            if (finishedAddingGlobals == false) {
                addNode("push");
                pushRegisters(NUMREGISTERS);
                addNode("jsr main");
                addNode("sys halt");
                finishedAddingGlobals = true;
            }

            // check if the label we're at is a function name
            if (symbols.functions.containsKey(labelName)) {
                currentFunction = labelName;
            }


            addNode("label " + tokens[1]);
        } else if (opcode.equals("ADDI")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("addi " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("ADDF")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("addr " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("SUBI")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("subi " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("SUBF")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("subr " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("MULTI")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("muli " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("MULTF")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("mulr " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("DIVI")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("divi " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("DIVF")) {
            moveConversion(nodeArray[1], nodeArray[3]);
            addNode("divr " + convertRegister(nodeArray[2]) + " " 
                    + convertRegister(nodeArray[3]));
        } else if (opcode.equals("STOREI")) {
            moveConversion(nodeArray[1], nodeArray[2]);
        } else if (opcode.equals("STOREF")) {
            moveConversion(nodeArray[1], nodeArray[2]);
        } else if (opcode.equals("GT") || opcode.equals("GTI")) {
            addNode("cmpi " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jgt " + tokens[3]);
        } else if (opcode.equals("GTF")) {
            addNode("cmpr " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jgt " + tokens[3]);
        } else if (opcode.equals("GE") || opcode.equals("GEI")) {
            addNode("cmpi " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jge " + tokens[3]);
        } else if (opcode.equals("GEF")) {
            addNode("cmpr " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jge " + tokens[3]);
        } else if (opcode.equals("LT") || opcode.equals("LTI")) {
            addNode("cmpi " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jlt " + tokens[3]);
        } else if (opcode.equals("LTF")) {
            addNode("cmpr " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jlt " + tokens[3]);        
        } else if (opcode.equals("LE") || opcode.equals("LEI")) {
            addNode("cmpi " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jle " + tokens[3]);
        } else if (opcode.equals("LEF")) {
            addNode("cmpr " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jle " + tokens[3]);
        } else if (opcode.equals("NE") || opcode.equals("NEI")) {
            addNode("cmpi " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jne " + tokens[3]);
        } else if (opcode.equals("NEF")) {
            addNode("cmpr " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jne " + tokens[3]);
        } else if (opcode.equals("EQI") || opcode.equals("EQ")) {
            addNode("cmpi " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jeq " + tokens[3]);
        } else if (opcode.equals("EQF")){
            addNode("cmpr " + convertRegister(tokens[1]) 
                    + " " + convertRegister(tokens[2]));
            addNode("jeq " + tokens[3]);

        } else if (opcode.equals("JUMP")) {
            addNode("jmp " + tokens[1]);
        } else if (opcode.equals("READI")) {
            addNode("sys readi " + convertRegister(tokens[1]));
        } else if (opcode.equals("READF")) {
            addNode("sys readr " + convertRegister(tokens[1]));
        } else if (opcode.equals("WRITEI")) {
            addNode("sys writei " + convertRegister(tokens[1]));
        } else if (opcode.equals("WRITEF")) {
            addNode("sys writer " + convertRegister(tokens[1]));
        } else if (opcode.equals("WRITES")) {
            addNode("sys writes " + convertRegister(tokens[1]));
        } else if (opcode.equals("RET")) {
            addNode("unlnk");
            addNode("ret");
        } else if (opcode.equals("LINK")) {
            int numLocals = symbols.functions.get(currentFunction).numLocals();
            addNode("link " + numLocals);
        } else if (opcode.equals("PUSH")) {
            if (tokens.length > 1) {
                addNode("push " + convertRegister(tokens[1]));
            } else {
                addNode("push");
            }
        } else if (opcode.equals("JSR")) {
            pushRegisters(NUMREGISTERS);
            addNode("jsr " + tokens[1]);
            popRegisters(NUMREGISTERS);
        } else if (opcode.equals("POP")) {
            if (tokens.length > 1) {
                addNode("pop " + convertRegister(tokens[1]));
            } else {
                addNode("pop");
            }
        } else {
            addNode("error in tiny conversion: opcode is: " + inputNode);
        }
    }

    private void pushRegisters(int num) {
        for (int i = 0; i < num; i++) {
            addNode("push r" + i);
        }
    }

    private void popRegisters(int num) {
        for (int i = num - 1; i >= 0; i--) {
            addNode("pop r" + i);
        }
    }
    private String convertRegister(String register){
        if (register.charAt(0) == '$'){
            if (register.charAt(1) == 'T') {
                String number = register.replace("$T", "");
                int registerNumber = Integer.parseInt(number) - 1;
                return "r" + String.valueOf(registerNumber);
            } else if (register.charAt(1) == 'L') {
                String number = register.replace("$L", "");
                int registerNumber = Integer.parseInt(number);
                return "$-" + String.valueOf(registerNumber);
            } else if (register.charAt(1) == 'P') {
                String number = register.replace("$P", "");
                int registerNumber = Integer.parseInt(number) - 1 + 2 + NUMREGISTERS;
                return "$" + String.valueOf(registerNumber);
            } else if (register.charAt(1) == 'R') {
                int returnLoc = symbols.functions.get(currentFunction).numParams() + 2 + NUMREGISTERS;
                return "$" + returnLoc; 
            } else {
                return "ERR";
            }

        } else {
            return register;
        }
    }


    private void moveConversion(String operand1, String operand2){
        if (convertRegister(operand1).equals(operand1) 
                && convertRegister(operand2).equals(operand2)){
            addNode("error in tiny conversion: " 
                    + operand1 + " and " 
                    + operand2 + " are both variables. Node not added");
        } else {
            addNode("move " 
                    + convertRegister(operand1) + " " 
                    + convertRegister(operand2));
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
