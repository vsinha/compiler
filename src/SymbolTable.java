import java.util.*;

public class SymbolTable {
    private Hashtable<String, Id> table;
    protected SymbolTable outer;

    public SymbolTable(SymbolTable st) {
        table = new Hashtable<String, Id>();
        outer = st;
    }

    public void put(String token, Type type, int bytesize) {
        table.put(token, new Id(token, type, bytesize));
    }

    public Id get(String token) {
        for (SymbolTable tab = this; tab != null; tab = tab.outer) {
            Id id = (Id)(tab.table.get(token));
            if (id != null) {
                return id;
            }
        }
        return null; // symbol not found
    }
}

