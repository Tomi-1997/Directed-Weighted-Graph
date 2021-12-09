package api;

public class NodeJson {
    private String pos;
    private int id;

    public String getPos() {
        return pos;
    }

    public int getId() {
        return id;
    }

    public NodeJson(String pos, int id) {
        this.pos = pos;
        this.id = id;
    }
}
