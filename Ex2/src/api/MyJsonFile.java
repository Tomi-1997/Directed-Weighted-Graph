package api;

import java.util.ArrayList;

public class MyJsonFile {
    private ArrayList<EdgeJson> Edges;
    private ArrayList<NodeJson> Nodes;

    public ArrayList<EdgeJson> getEdges() {
        return Edges;
    }

    public ArrayList<NodeJson> getNodes() {
        return Nodes;
    }


    public MyJsonFile(ArrayList<EdgeJson> e, ArrayList<NodeJson> n) {
        this.Edges = e;
        this.Nodes = n;
    }
}
