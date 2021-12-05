package api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public class DWG implements DirectedWeightedGraph{

    // Hash map to store vertex data by id
    private HashMap<String, Vertex> V;
    // Hash map to store edge data by src,dest for example: "0,1" is an edge from node 0 to node 1
    // and is different from "1,0"
    private HashMap<String, Edge> E;

    private static int changes;
    private boolean iter_general;
    private boolean iter_edges;

    // Initialize
    public DWG()
    {
        V = new HashMap<>();
        E = new HashMap<>();

        changes = 0;
        iter_edges = false;
        iter_general = false;
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
        if (iter_general || iter_edges)
        {
            throw new RuntimeException();
        }
        Vertex v = new Vertex( (Point3D) n.getLocation() , n.getKey());
        V.put(v.id + "", v);
        changes++;
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        if (iter_edges)
        {
            throw new RuntimeException();
        }
        boolean illegal_v = !V.containsKey(src+"") || !V.containsKey(dest+"");
        if ( src == dest || w < 0 || illegal_v)
        {
            System.err.println("Invalid insertion, tried to insert:" + src + "," + dest);
            return;
        }
        Edge e_new = new Edge(V.get(src+""), V.get(dest+"") , w);
        if (E.containsKey(src+","+dest))
        {
            E.replace(src+","+dest , e_new);
        }
        else
            E.put(src+","+dest, e_new);

        changes++;
    }

    public void connect(Edge e)
    {
        connect(e.src.id, e.dst.id, e.weight);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator v = this.V.entrySet().iterator();
        this.iter_general = true;
        return v;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator e = this.E.entrySet().iterator();
        this.iter_edges = true;
        return e;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        ArrayList<String> list = new ArrayList<>();
        Set s = this.E.entrySet();
        Iterator set_iter = s.iterator();
        while (set_iter.hasNext())
        {
            if (set_iter.next().toString().split(",")[0].compareTo(node_id+"") != 0)
                set_iter.remove();
        }
        this.iter_edges = true;
        return s.iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        Iterator<EdgeData> t = this.edgeIter(key);
        while (t.hasNext())
        {
            this.E.remove(t+"");
            t.next();
        }
        return this.V.remove(key+"");
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return this.E.remove(src+","+dest);
    }

    @Override
    public int nodeSize() {
        return this.V.size();
    }

    @Override
    public int edgeSize() {
        return this.E.size();
    }

    @Override
    public int getMC() {
        return this.changes;
    }
}
