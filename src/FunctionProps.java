import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class FunctionProps {
    private String funcName;
    private String returnType;
    LinkedHashMap<String, String> params;

    public FunctionProps (String funcName, String returnType) {
        this.funcName = funcName;
        this.returnType = returnType;
        params = new LinkedHashMap<String, String>();
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

    public String getName() {
        return this.funcName;
    }

    public String getReturnType() {
        return this.returnType;
    }
}
