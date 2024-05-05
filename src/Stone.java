public class Stone {
    private String display = " ";
    private boolean isKing;
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

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
    public void setFull(boolean full){
        isFull = full;
    }
    public boolean isFull(){
        return  isFull;
    }

    public Stone(String display, boolean isKing, boolean color) {
        this.display = display;
        this.isKing = isKing;
        this.color = color;
        this.isFull = true;
    }
    public static Stone White(){
        return new Stone("W",false,true);
    }
    public static Stone Black(){
        return new Stone("B",false,false);
    }
    public static Stone Empty(){
        Stone k = new Stone(" ",false,false);
        k.setFull(false);
        return k;
    }
}
