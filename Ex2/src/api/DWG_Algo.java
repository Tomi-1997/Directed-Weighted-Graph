package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DWG_Algo implements DirectedWeightedGraphAlgorithms{

    DWG G;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.G = (DWG) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.G;
    }

    @Override
    public DirectedWeightedGraph copy()
    {

        DWG newG = new DWG();

        for (Vertex v : this.G.get_V().values())
        {
            newG.addNode(v);
        }
        for (Edge e : this.G.get_E().values())
        {
            newG.connect(e);
        }
        return newG;
    }

    @Override
    public boolean isConnected()
    {
        if(this.G.nodeSize() == 1 || this.G.nodeSize() == 0) return true;
        if(shortestPath(0,1).size() == 0) return false;
        this.G = getTranspose();
        boolean transpose = true;
        if(shortestPath(0,1).size() == 0) transpose = false;
        this.G = getTranspose();
        return transpose;
    }

    public DWG getTranspose()
    {
       DWG g_t = new DWG();

       for (Vertex v : this.G.get_V().values())
       {
            g_t.addNode(v);
       }
        for (Edge e : this.G.get_E().values())
        {
            Edge e_t = new Edge(e.dst, e.src, e.getWeight());
            g_t.connect(e_t);
        }
        return g_t;

    }

    @Override
    public double shortestPathDist(int src, int dest)
    {
        double sum = 0;
        List<NodeData> l = shortestPath(src, dest);

        if (l.size() == 0) return -1;
        for (int i = 0; i<l.size()-1; i++)
        {
            EdgeData e = this.G.getEdge(i, i +1);
            sum += e.getWeight();
        }
        return sum;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest)
    {
        // Empty list if source equals destination
        if (src == dest) return new ArrayList<>();


        int node_size = this.G.nodeSize();
        // Reset all nodes to "infinity" and source to 0
        // Assuming all node keys are integers 0 , 1 , .. , V.size -1
        double[] weight = new double[node_size];

        // Memorise each node's parent.
        int[] parents = new int[node_size];
        Arrays.fill(parents, -1);

        for (NodeData n : this.G.get_V().values())
        {
            weight[n.getKey()] = Double.MAX_VALUE;
        }
        weight[src] = 0;

        // Dijkstra
        int current = src;
        Iterator<EdgeData> src_iterator = this.G.edgeIter(current);

        // While there are connected nodes unvisited.
        while (src_iterator.hasNext())
        {
            // Set tag to visited
            this.G.getNode(current).setTag(1);

            // If current weight is less than previous -> replace it and the parent.
            while (src_iterator.hasNext())
            {
                EdgeData e = src_iterator.next();
                if (weight[current] + e.getWeight() < weight[e.getDest()])
                {
                    weight[e.getDest()] = weight[current] + e.getWeight();
                    parents[e.getDest()] = e.getSrc();
                }
            }
            current = getMin(weight);
            // If no other nodes to travel before reaching destination
            if (current == -1)
                break;
            src_iterator = this.G.edgeIter(current);
        }

        // if dest unreachable return empty list
        if (parents[dest] == -1)
            return new ArrayList<>();

        // Put destination and his ancestry to a list and then reverse it
        ArrayList<NodeData> list = new ArrayList<>();
        current = dest;
        while (current != src)
        {
            list.add(this.G.getNode(current));
            current = parents[current];
        }
        list.add(this.G.getNode(src));

        // Reset nodes to unvisited.
        for (NodeData n : this.G.get_V().values())
            n.setTag(0);

        return reverse(list);
    }

    private List<NodeData> reverse(ArrayList<NodeData> list) {
        ArrayList<NodeData> temp = new ArrayList<>();
        for (int i = list.size()-1; i >= 0; i--)
        {
            temp.add(list.get(i));
        }
        return temp;
    }

    private int getMin(double[] a)
    {
        if (a.length == 0)
            return -1;

        if (a.length == 1)
            return 0;

        double ans = Double.MAX_VALUE;
        int indx = -1;
        for (int i = 0; i < a.length; i++)
        {
            if (a[i] < ans && this.G.getNode(i).getTag() == 0)
            {
                ans = a[i];
                indx = i;
            }
        }

        return indx;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArrayList<EdgeJson> Edges = new ArrayList<>();
        ArrayList<NodeJson> Nodes = new ArrayList<>();

        int i = 0;
        for (EdgeData e : this.G.get_E().values())
        {
            EdgeJson data = new EdgeJson(e.getSrc(), e.getWeight(), e.getDest());
            Edges.add(data);
        }
        for (NodeData n: this.G.get_V().values())
        {
            NodeJson ndata = new NodeJson(n.getInfo() , n.getKey() );
            Nodes.add(ndata);
        }

        MyJsonFile j = new MyJsonFile(Edges, Nodes);

        try
        {
            FileWriter fileSave = new FileWriter(file);
            fileSave.write(gson.toJson(j));
            fileSave.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
