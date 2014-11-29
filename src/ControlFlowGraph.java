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
        
        public CFNode (String[] code) {
            this.code = code;
        }

        public String getOpcode() {
            // the opcode is the first string in the tokenized code
            return this.code[0];
        }

        @Override 
        public String toString() {
            return this.code;
        }
    }

    public ControlFlowGraph(LinkedList<String> irLL) {
        //// 1 ////
        // parse the linked list into a list of CFNode linked lists
        // this breaks the IR into individual functions so we can 
        // perform further analysis
        this.cfLLs = new ArrayList<LinkedList<CFNode>>();
        LinkedList<CFNode> currentFunctionLL = null;

        System.out.println(irLL);

        for (String codeUnTokenized : irLL) {
            String[] code = codeUnTokenized.split(" ");
            // check if the code is signifying a new function
            // will be of form "LABEL functionname", where 
            // all other labels  will be of form "LABEL label#" 
            // where "#" is a numeral
            if (code.contains("LABEL") && !code.split(" ")[1].startsWith("label")) {

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
        printCFLL();


    }


    public void printCFLL() {
        for (LinkedList<CFNode> ll : cfLLs) {
            for (CFNode node : ll) {
                System.out.println(node.toString());
            }
            System.out.println();
        }
    }
}
