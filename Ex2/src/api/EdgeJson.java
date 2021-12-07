package api;

public class EdgeJson {
    private int src;
    private double weight;
    private int dst;

    public EdgeJson(int src, double weight, int dst) {
        this.src = src;
        this.weight = weight;
        this.dst = dst;
    }
}
