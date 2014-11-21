import java.util.LinkedHashMap;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class NodeProperties {
    public String text = null;
    LinkedHashMap<String, String> data = new LinkedHashMap<>();
    ArrayList<String> exprListPrimaries = new ArrayList<String>();

    static String[] _keys = {"primary", "primary_name", "assign_Lvalue",
                        "addop", "mulop", "compop",
                        "jump_label", "on_stmt_exit",
                        "on_else_enter", "on_else_exit",
                        "endwhile_jump", "endwhile_label"};
    public static List<String> keys = Arrays.asList(_keys);

    public NodeProperties() {
        for (String key : keys) {
            data.put(key, null);
        }
    }

    public NodeProperties(String text) {
        this();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getValue(String key) {
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException("key: \"" + key 
                    + "\" not present in NodeProperties object");
        }
        return data.get(key);
    }

    public boolean putValue(String key, String value){
       if (data.containsKey(key)) {
           data.put(key, value);
           return true;
       } else {
           System.out.println("attempted to put invalid key: " + 
                   key + ", " + value);
           return false;
       }
    }

    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    public boolean isNull(String key) {
        return (data.get(key) == null);
    }

    @Override public String toString() {
        StringBuilder str = new StringBuilder();
        Set<String> keys = data.keySet();

        if (keys.size() > 0) {
            str.append(text);
            str.append(" - ");
            for (String key : keys) {
                str.append(key);
                str.append(": ");
                str.append(data.get(key));
                str.append(", ");
            }
        }
        return str.toString();
    }
}
