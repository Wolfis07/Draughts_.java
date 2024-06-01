public class Stone {
    private String display = " ";

    private boolean color;
    private boolean isFull;




    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setFull(boolean full){
        isFull = full;
    }
    public boolean isFull(){
        return  isFull;
    }

    public Stone(String display, boolean color) {
        this.display = display;

        this.color = color;
        this.isFull = true;
    }
    public static Stone White(){
        return new Stone("(W)",true);
    }
    public static Stone Black(){
        return new Stone("(B)",false);
    }
    public static Stone Empty(){
        Stone k = new Stone(" ",false);
        k.setFull(false);
        return k;
    }
}
