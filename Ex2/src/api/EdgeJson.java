package api;

public class EdgeJson {
    private int src;
    private double w;
    private int dest;

    public int getSrc() {
        return src;
    }

    public double getWeight() {
        return w;
    }

    public int getDst() {
        return dest;
    }

    public EdgeJson(int src, double w, int dest) {
        this.src = src;
        this.w = w;
        this.dest = dest;
    }
}
