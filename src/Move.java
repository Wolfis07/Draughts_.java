public class Move {
    private Point to;
    private Point jump = null;

    public Move(Point to, Point jump) {
        this.to = to;
        this.jump = jump;
    }

    public Move(Point to) {
        this.to = to;
    }
    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getJump() {
        return jump;
    }

    public void setJump(Point jump) {
        this.jump = jump;
    }

    @Override
    public String toString() {
        return "Move{" +
                "to=" + to +
                ", jump=" + jump +
                '}';
    }
}
