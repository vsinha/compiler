import java.util.*;
import java.lang.NullPointerException;

public class SymbolTableTree {

    class SymbolTable {
        String scopeName;
        String scopeType;
        SymbolTable parent;
        ArrayList<SymbolTable> children;
        LinkedHashMap<String, Id> table; // these are local vars
        
        int childIndex;

        public SymbolTable (String scopeName) {
            this.scopeName = scopeName;
            parent = null;
            children = new ArrayList<SymbolTable>();
            table = new LinkedHashMap<String, Id>();
            childIndex = 0;
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
    public void createScope() {
        createScope("BLOCK " + blockCount);
        currentScope.scopeType = "block";
        blockCount += 1;
    }

    public void createScope(String scopeName) {
        SymbolTable st = new SymbolTable(scopeName);
        //System.out.println("created scope: " + scopeName);
        st.scopeType = "function";
        st.parent = currentScope;
        currentScope = st;
        st.parent.children.add(st);
    }

    public boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }

    public boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }


    public Id lookup(String varName) {
        Id id = null;

        //System.out.println("looking up: " + varName);

        try {
            id = _lookup(currentScope, varName);
        } catch (java.lang.NullPointerException e) {
            // lookup failed, it's a string int or float
            if ( isFloat(varName) ) {
                id = new Id(varName, "FLOAT");
                id.setIsRegister();
            } else if ( isInteger(varName) ) {
                id = new Id(varName, "INT");
                id.setIsRegister();
            } else {
                id = null;
            }
        }

        return id;
    }

    private Id _lookup(SymbolTable scope, String varName) {
        if (scope.table.containsKey(varName)) {
            //System.out.println("looking up " + varName);
            return scope.table.get(varName);
        } else {
            // recurse up to find it
            return _lookup(scope.parent, varName);
        }
    }

    public void enterScopeSequentially() {
        //System.out.println("entering a scope");
        currentScope = currentScope.children.get(currentScope.childIndex);
        //System.out.println("just entered scope: " + currentScope.scopeName);
        //System.out.println(currentScope.children);

        // increment
        currentScope.parent.childIndex += 1;
    }

    public void exitScope() {
        // move up a level
        //System.out.println("exiting scope: " + currentScope.scopeName);
        currentScope = currentScope.parent;
        //System.out.println(currentScope.children);
    }

    public void addVariables(ArrayList<String> names, String type) {
        for (String name : names) {
            addVariable(name, type);
        }
    }

    public void addVariable(String name, String type) {
        // check that variable doesn't exist in parent scopes
        checkForShadowInParents(currentScope.parent, name);

        if (currentScope.table.containsKey(name)) {
            System.out.println("DECLARATION ERROR " + name);
            System.exit(0);
        }

        // if it does, add it anyways but print a shadow warning
        currentScope.table.put(name, new Id(name, type));
    }

    public void addRegister(String name, String type) {
        addVariable(name, type);
        currentScope.table.get(name).setIsRegister();
    }

    private void checkForShadowInParents(SymbolTable node, String var) {
        if (node == null)
            return;

        if (node.table.containsKey(var))
            //System.out.println("SHADOW WARNING " + var);

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
