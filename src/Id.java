public class Id {
    String name;
    String type;
    String value;
    int size;
    int offset;

    public Id (String name, String type) {
        this.name = name;
        this.type = type;
        this.value = null;
    }


    // for string variables
    public Id (String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public boolean isString() {
        return type.equals("STRING");
    }

    private int sizeof(String type) {
        if (type.equals("INT")) {
            return 8;
        }
        else if (type.equals("FLOAT")) {
            return 32;
        }
        else if (type.equals("STRING")) {
            return 8;
        }
        else {
            return -1;
        }
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
