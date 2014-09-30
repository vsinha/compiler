public class Id {
    String name;
    String type;
    String value;

    public Id (String name, String type) {
        this.name = name;
        this.type = type;
        this.value = null;
    }

    public Id (String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public boolean isString() {
        return type.equals("STRING");
    }

    @Override
    public String toString() {
        if (isString()) {
          return new String("name " + name + " type " + type 
                  + " value " + value); 
        } else {
          return new String("name " + name + " type " + type); 
        }
    }

}
