public class Type {
    public int width = 0;
    public String name = "";
    public Type(String s, int w) { name = s; width = w; }

    public static final Type
        Int = new Type("int", 4),
        Float = new Type("float", 8),
        Char = new Type("char", 1),
        Bool = new Type("bool", 1);

    public static boolean numeric(Type p) {
        return p == Type.Char 
            || p == Type.Int 
            || p == Type.Float;
    }

    public static Type max(Type p1, Type p2) {
        if (!numeric(p1) || !numeric(p2)) {
            return null;
        }
        else if (p1 == Type.Float || p2 == Type.Float) {
            return Type.Float;
        }
        else if (p1 == Type.Int || p2 == Type.Int) {
            return Type.Int;
        }
        else { 
            return Type.Char;
        }
    }
}
