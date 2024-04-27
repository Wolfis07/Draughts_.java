public class Jump {
    private Point to;
    private Point remove;

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getRemove() {
        return remove;
    }

    public void setRemove(Point remove) {
        this.remove = remove;
    }

    public Jump(Point to, Point remove) {
        this.to = to;
        this.remove = remove;
    }
}

