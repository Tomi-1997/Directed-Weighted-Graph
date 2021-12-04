package api;

public class Vertex implements NodeData {
    public Point3D pos;
    public int id;
    public double weight;
    public String info;
    // tag can be white = 0,black = 2,grey = 1
    public int tag;

    public Vertex(Point3D pos, int id) {
        this.pos = pos;
        this.id = id;
        this.weight = 0;
        this.info = this.pos.x+"," +this.pos.y+","+this.pos.z+ "," + this.id;
        this.tag = 0;
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        if(this.pos == null) return null;
        return this.pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.pos = (Point3D)(p);
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
