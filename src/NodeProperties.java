public class NodeProperties {
    public String text = null;
    public String Ltext = null;
    public String Rtext = null;
    public String leftNode = null;
    public String operator = null;
    public String mostRecentTempRegister = null;
    public String IRcode = null;

    public NodeProperties() {
    }

    public NodeProperties(String text) {
        this.text = text;
    }

    public void setLtext(String text) {
        this.Ltext = text;
    }
  
    public void setRtext(String text) {
        this.Rtext = text;
    }
  
    @Override public String toString() {
        return new String(text + " " 
                + operator + " " + leftNode
                + Ltext + " " + Rtext);
    }
}
