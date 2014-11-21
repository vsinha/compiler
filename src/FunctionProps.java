import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class FunctionProps {
    private String funcName;
    private String returnType;
    LinkedHashMap<String, String> params;
    LinkedHashMap<String, String> locals;

    public FunctionProps (String funcName) {
        this.funcName = funcName;
        params = new LinkedHashMap<String, String>();
        locals = new LinkedHashMap<String, String>();
    }

    public void addParam(String name, String type) {
        params.put(name, type);
    }

    public void printParams() {
        Set<String> keys = params.keySet();
        for (String key : keys) {
            System.out.println(key + " : " + params.get(key));
        }
    }

    public void addLocal(String name, String type) {
        locals.put(name, type);
    }

    public int numLocals() {
        return locals.size();
    }

    public int numParams() {
        return params.size();
    }

    public String getName() {
        return this.funcName;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        return this.returnType;
    }
}
