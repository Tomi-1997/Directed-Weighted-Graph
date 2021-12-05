package api;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
public class DWG implements DirectedWeightedGraph{

    private HashMap<String, Vertex> V;
    private HashMap<String, Edge> E;

    // Initialize
    public DWG()
    {
        V = new HashMap<>();
        E = new HashMap<>();
    }

    @Override
    public NodeData getNode(int key) {
        String k = key + "";
        return V.get(k);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        String e = src + "," + dest;
        return E.get(e);
    }

    @Override
    public void addNode(NodeData n) {
        Vertex v = new Vertex( (Point3D) n.getLocation() , n.getKey());
        V.put(v.id + "", v);
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        boolean illegal_v = !V.containsKey(src+"") || !V.containsKey(dest+"");
        if ( src == dest || w < 0 || illegal_v)
        {
            System.err.println("Invalid insertion");
            return;
        }

        Edge e_new = new Edge(V.get(src+""), V.get(dest+"") , w);
        if (E.containsKey(src+","+dest))
        {
            E.replace(src+","+dest , e_new);
        }
        else
            E.put(src+","+dest, e_new);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
