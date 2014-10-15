import java.util.*;

public class SymbolTableTree {

    class SymbolTable {
        String scopeName;
        String scopeType;
        SymbolTable parent;
        ArrayList<SymbolTable> children;
        LinkedHashMap<String, Id> table;

        public SymbolTable (String scopeName) {
            this.scopeName = scopeName;
            parent = null;
            children = new ArrayList<SymbolTable>();
            table = new LinkedHashMap<String, Id>();
        }

        public void printTable() {
            System.out.println("Symbol table " + scopeName);
            Set<String> keys = table.keySet();
            for (String key : keys) {
                System.out.println(table.get(key));
            }
        }
    }

    SymbolTable global = null; // top level scope, has no parents
    SymbolTable currentScope = null;
    int blockCount = 1;

    public SymbolTableTree () { 
        // push GLOBAL scope to start us off
        global = new SymbolTable("GLOBAL");
        currentScope = global;
    }

    // come up with a "BLOCK %d" name 
    public void enterScope() {
        enterScope("BLOCK " + blockCount);
        currentScope.scopeType = "block";
        blockCount += 1;
    }

    public void enterScope(String scopeName) {
        SymbolTable st = new SymbolTable(scopeName);
        st.scopeType = "function";
        st.parent = currentScope;
        currentScope = st;
        st.parent.children.add(st);
    }

    public void exitScope() {
        currentScope = currentScope.parent;
    }

    public void addVariables(ArrayList<String> names, String type) {
        for (String name : names) {
            addVariable(name, type);
        }
    }
    
    /*
    private int computeScopeSize(SymbolTable scope) {
        Set<String> keys = scope.table.keySet();
        int size = 0;

        for (String key : keys) {
            size += table.get(key).size;
        }
        return size;
    }

    private int computeOffset(SymbolTable scope) {
        int offset = _computeOffset(scope, computeScopeSize(scope)); 
    }

    private int _computeOffset(SymbolTable scope, int offset) {
        if (scope.scopeType.equals("block")) {
            SymbolTable parent = scope.parent;
            offset += _computeOffset(scope, offset);
        } else { // scope is a function
            return offset + computeScopeSize(scope);
        }
    }
    */

    public void addVariable(String name, String type) {
        // check that variable doesn't exist in parent scopes
        checkForShadowInParents(currentScope.parent, name);

        //int offset = computeOffset(); 

        if (currentScope.table.containsKey(name)) {
            System.out.println("DECLARATION ERROR " + name);
            System.exit(0);
        }

        // if it does, add it anyways but print a shadow warning
        currentScope.table.put(name, new Id(name, type));
    }

    private void checkForShadowInParents(SymbolTable node, String var) {
        if (node == null) {
            return;
        }

        if (node.table.containsKey(var)) {
            //System.out.println("SHADOW WARNING " + var);
        }

        checkForShadowInParents(node.parent, var);
    }


    public void addString(String name, String value) {
        currentScope.table.put(name, new Id(name, "STRING", value));
    }


    public void printTree() {
        _printTree(global);
    }

    // recursion helper
    private void _printTree(SymbolTable st) {
        st.printTable();
        System.out.println();
        for (SymbolTable child : st.children) {
            _printTree(child);
        }
    }

}
