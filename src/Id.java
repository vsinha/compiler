public class Id {
    String name;
    String type;
    String value;
    int size;
    int offset;
    boolean isRegister;
    boolean isParameter;
    private String stackAddress;

    public Id (String name, String type) {
        this.name = name;
        this.type = type;
        this.value = null;
        this.isRegister = false;
        this.isParameter = false;
        this.stackAddress = null;
    }

    // for string variables
    public Id (String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isRegister = false;
        this.isParameter = false;
    }

    public void setIsRegister() {
        this.isRegister = true;
        this.stackAddress = this.name;
    }

    public boolean isRegister() {
        return this.isRegister;
    }

    public void setIsParameter() {
        this.isParameter = true;
    }

    public boolean isParameter() {
        return this.isParameter;
    }

    public boolean isString() {
        return type.equals("STRING");
    }

    public void setStackAddress(String stackAddress) {
        this.stackAddress = stackAddress;
    }

    public String getStackAddress() {
        return this.stackAddress;
    }

    public String getName() {
        if (this.stackAddress != null) {
            return this.stackAddress;
        } else {
            return this.name;
        }
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
