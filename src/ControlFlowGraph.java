import java.util.ArrayList;
import java.util.LinkedList;

public class ControlFlowGraph {
    // an arraylist of linked lists of CFnodes... lol
    // so we can store 1 list per function, and be able to hold on to
    // successor and predecessor information for the list nodes
    ArrayList<LinkedList<CFNode>> cfLLs;

    class CFNode {
        String[] code;
        ArrayList<CFNode> predecessors;
        ArrayList<CFNode> successors;

        ArrayList<String> gen; // variables created
        ArrayList<String> kill; // variables used

        public CFNode (String[] code) {
            this.code = code;
            this.predecessors = new ArrayList<CFNode>();
            this.successors = new ArrayList<CFNode>();
            this.gen = new ArrayList<String>();
            this.kill = new ArrayList<String>();
        }

        public String getOpcode() {
            // the opcode is the first string in the tokenized code
            return this.code[0];
        }

        public String getLabel() {
            //System.out.println("looking up: " + this.code[0] + " " + this.code[1]);
            if (this.getOpcode().equals("LABEL") || this.getOpcode().equals("JUMP")) { // labels 
                return this.code[1];
            } else if (this.code.length == 4 && this.code[3].startsWith("label")) { // conditionals
                return this.code[3];
            } else { // everything else
                return "";
            }
        }

        public boolean isConditional() {
            return this.code.length == 4;
        }

        public void addSuccessor(CFNode n) {
            successors.add(n);
        }
        
        public void addPredecessor(CFNode n) {
            predecessors.add(n);
        }

        public void uses(String s) {
            //GEN represents all the temporaries and variables that are *used* in an instruction
            this.gen.add(s);
        }

        public void defines(String s) {
            //KILL represents all the temporaries and variables that are *defined* in an instruction
            this.kill.add(s);
        }

        public boolean isLeader() {
            // either has predecessors other than the immediately preceding instruction
            // or has successors other than the immediately following instruction
            return (!node.successors.isEmpty() || !node.predecessors.isEmpty());
        }

        @Override 
        public String toString() {
            String s = "";
            for (int i = 0; i < this.code.length; i++) {
                s += code[i] + " ";
            }
            return s;
        }
    }

    public ControlFlowGraph(LinkedList<String> irLL) {
        //// 1 ////
        // parse the linked list into a list of CFNode linked lists
        // this breaks the IR into individual functions so we can 
        // perform further analysis
        this.cfLLs = new ArrayList<LinkedList<CFNode>>();
        LinkedList<CFNode> currentFunctionLL = null;

        for (String codeUnTokenized : irLL) {
            String[] code = codeUnTokenized.split(" ");
            // check if the code is signifying a new function
            // will be of form "LABEL functionname", where 
            // all other labels  will be of form "LABEL label#" 
            // where "#" is a numeral
            if (code[0].equals("LABEL") && !code[1].startsWith("label")) {

                // add the previously populated list, if any
                if (currentFunctionLL != null && !currentFunctionLL.isEmpty()) {
                    cfLLs.add(currentFunctionLL);
                }

                // we have a new function declaration, so we make a new list
                currentFunctionLL = new LinkedList<CFNode>();
            }

            // if we get here and the list is still null, we have a problem
            // todo: exception handling, potentially

            // now add nodes to the list
            currentFunctionLL.add(new CFNode(code));
        }
        // add the final function
        cfLLs.add(currentFunctionLL);

        // now we have individual linked lists for each function
        //printCFLL();

        // note that each node technically has the next node as a successor and 
        // the previous node as a predecessor, but we're using the linked list builtins
        // to handle that.
        /*
            // ... maybe we should fix this ^^^
            // add every node's immediately previous node as a predecessor
            // and every node (except for return statements) next node as a successor
            for (LinkedList<CFNode> ll : cfLLs) {
            }
        */

        //// 2 ////
        // for each function, populate the successor and predecessor nodes,
        // turning the individual function linked lists into actual
        // control flow graphs
        for (LinkedList<CFNode> ll : cfLLs) {
            System.out.println();
            for (CFNode node : ll) {
                //System.out.println(node);

                // when we see an unconditional jump, add the target as a successor
                // of the jump, and the jump statement as a predecessor of the target
                if (node.getOpcode().equals("JUMP") || node.isConditional()) {
                    //System.out.println("here");

                    String jumpLabel = node.getLabel();
                    //System.out.println("looking for: " + jumpLabel);

                    // find the CFNode with the corresponding label in the function
                    for (CFNode targetNode : ll) {

                        if (targetNode.getOpcode().equals("LABEL") 
                                && targetNode.getLabel().equals(jumpLabel)) {
                            //System.out.println("adding " + node + " and " + targetNode);
                            // when we find it, link the two nodes together
                            node.addSuccessor(targetNode);
                            targetNode.addPredecessor(node);
                        }
                    }
                }
            }

            // print it
            for (CFNode node : ll) {
                //System.out.println(node.toString() + " " + node.successors + " " + node.predecessors);
            }
        }

        // compute GEN and KILL sets
        for (LinkedList<CFNode> ll : cfLLs) {
            for (CFNode node : ll) {
                computeGenKill(node);
                System.out.println(node.toString() + " " + node.successors + " " + node.predecessors
                        +  "  " + node.gen + " " + node.kill);
            }
            System.out.println();
        }
    }


    public void printCFLL() {
        for (LinkedList<CFNode> ll : cfLLs) {
            for (CFNode node : ll) {
                System.out.println(node.toString());
            }
            System.out.println();
        }
    }


    public void computeGenKill(CFNode node) {
        String opcode = node.getOpcode();

        // be safe
        if (opcode == null || opcode.equals("RET")) {
            return;
        }

        if (opcode.startsWith("ADD") || opcode.startsWith("SUB") // arithmetics
         || opcode.startsWith("DIV") || opcode.startsWith("MUL")) {
            node.uses(node.code[1]);
            node.uses(node.code[2]);
            node.defines(node.code[3]);

        } else if (opcode.startsWith("STORE")) { // store
            node.uses(node.code[1]);
            node.defines(node.code[2]);
        } else if (opcode.startsWith("STORE")) { // load
            node.defines(node.code[1]);
            node.uses(node.code[2]);

        } else if (opcode.startsWith("GE") || opcode.startsWith("LE")  // comparators
                || opcode.startsWith("NE") || opcode.startsWith("EQ")) {
            node.uses(node.code[1]);
            node.uses(node.code[2]);
            // code[3] is the jump label

        } else if (opcode.startsWith("WRITE")) { // write & read
            node.uses(node.code[1]);
        } else if (opcode.startsWith("READ")) {
            node.defines(node.code[1]);

        } else if (opcode.startsWith("PUSH")) { // push & pop
            if (node.code.length > 1) {
                node.uses(node.code[1]);
            }
        } else if (opcode.startsWith("POP")) {
            if (node.code.length > 1) {
                node.defines(node.code[1]);
            }
        }
                

    }
}






