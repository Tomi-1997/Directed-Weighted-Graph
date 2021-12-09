package api;

public class Edge implements EdgeData {

    public Vertex src;
    public double weight;
    public Vertex dst;
    private String info;

    /** tag represents color
     * 0 = white
     * 1 = grey
     * 2 = black **/
    public int tag;

    public Edge(Vertex src, Vertex dst, double weight) {
        this.src = src;
        this.dst = dst;
        this.weight = weight;
        this.info = "\"src\": " + this.src.id + ",\n"
                +  "\"w\": " + this.dst.id  + ",\n" +
                "\"dest\": " + this.weight;
        this.tag = 0;
    }

    public Edge(Vertex src, double weight , Vertex dst) {
        this.src = src;
        this.weight = weight;
        this.dst = dst;

        this.info = "\"src\": " + this.src.id + ",\n"
                +  "\"w\": " + this.dst.id  + ",\n" +
                "\"dest\": " + this.weight;
        this.tag = 0;
    }

    @Override
    public int getSrc() {
        return this.src.id;
    }

    @Override
    public int getDest() {
        return this.dst.id;
    }

    public void setSrc(Vertex src)
    {
        this.src = src;
    }

    public void setDst(Vertex dst)
    {
        this.dst = dst;
    }

    public double getWeight()
    {
        return this.weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public String getInfo()
    {
        return this.info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public int getTag()
    {
        return this.tag;
    }

    public void setTag(int tag)
    {
        this.tag = tag;
    }
}
