package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        for (EdgeData e : this.G.get_E().values())
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

    /***
     *
     * @return a directed weighted graph with every edge's source and destination flipped.
     */
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
            EdgeData e = this.G.getEdge(l.get(i).getKey(),l.get(i +1).getKey());
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

        // weightSet is just to keep relevant nodes, to save time by not iterating over the same
        // nodes each time we look for the next minimal node.
        HashMap<Integer , Double> weightSet = new HashMap<>();
        double[] weight = new double[node_size];

        // Memorise each node's parent.
        int[] parents = new int[node_size];
        Arrays.fill(parents, -1);

        for (NodeData n : this.G.get_V().values())
        {
            weight[n.getKey()] = Double.MAX_VALUE;
            weightSet.put(n.getKey() , 0.0);
        }
        weight[src] = 0;


        // Dijkstra
        int current = src;
        Iterator<EdgeData> src_iterator = this.G.edgeIter(current);

        // While there are connected nodes unvisited.
        while (src_iterator.hasNext())
        {

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

            weightSet.remove(current);
            current = getMin(weight , weightSet);
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
        return reverse(list);
    }

    private List<NodeData> reverse(ArrayList<NodeData> list)
    {
        ArrayList<NodeData> temp = new ArrayList<>();
        for (int i = list.size()-1; i >= 0; i--)
        {
            temp.add(list.get(i));
        }
        return temp;
    }

    private int getMin(double[] a , HashMap<Integer, Double> set)
    {
        double ans = Double.MAX_VALUE;
        int indx = -1;

        Iterator<Integer> setIter = set.keySet().iterator();
        while (setIter.hasNext())
        {
            int current = setIter.next();
            if (a[current] < ans)
            {
                indx = current;
                ans = a[current];
            }
        }
        return indx;
    }

    @Override
    public NodeData center()
    {
        if (this.getGraph().nodeSize() == 0) return null;
        if (this.getGraph().nodeSize() == 1)
            return this.getGraph().nodeIter().next();

        // For each node key - put the maximum distance it can travel.
        HashMap<Integer, Double> node_and_max_dist = new HashMap<>();
        for (NodeData n : this.G.get_V().values())
        {
            node_and_max_dist.put(n.getKey(), max_dist(n.getKey()));
        }

        // Return the node with the minimum value.
        return min_value(node_and_max_dist);
    }

    /***
     *
     * @param - node_and_max_dist(Hash map of integer key and a value)
     * @return the node which has the minimal value.
     */
    private NodeData min_value(HashMap<Integer, Double> node_and_max_dist) {
        NodeData min = this.G.nodeIter().next();
        double min_val = Double.MAX_VALUE;
        for (Integer i : node_and_max_dist.keySet())
        {
            if (node_and_max_dist.get(i) < min_val)
            {
                min = G.getNode(i);
                min_val = node_and_max_dist.get(i);
            }
        }
        return min;
    }

    /***
     *
     * @param key - node id
     * @return the maximum travel distance this node needs to go to reach every other node.
     */
    private Double max_dist(int key) {
        double ans = 0;
        for (Vertex v: this.G.get_V().values())
        {
            double current = shortestPathDist(key , v.getKey());
            if (current != -1 && current > ans)
                ans = current;
        }
        return ans;
    }

    /***
     * Using the greedy method, get the shortest path from every adjacent pair
     * of elements and merge to one list.
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities)
    {
        ArrayList<NodeData> ans = new ArrayList<>();
        for (int i = 0; i < cities.size()-1; i++) {
            ans.addAll(shortestPath
                    (cities.get(i).getKey(),cities.get(i+1).getKey()));

            if (i != cities.size() - 2)
                ans.remove(ans.size()-1);
        }
        return ans;
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
    public boolean load(String file)
    {
        String input = "";
        String s="";
        try {
            FileReader f = new FileReader(file);
            BufferedReader readFile = new BufferedReader(f);
            s = readFile.readLine();
            while (s != null)
            {
                input = input + s;
                s = readFile.readLine();
            }
            f.close();
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Gson gson = new Gson();
        MyJsonFile mjf = gson.fromJson(input , MyJsonFile.class);
        DWG newG = new DWG();
        for (NodeJson n : mjf.getNodes())
        {
            String[] pos = n.getPos().split(",");
            Point3D p = new Point3D(Double.parseDouble(pos[0]) ,Double.parseDouble(pos[1]),Double.parseDouble(pos[2]));
            Vertex v = new Vertex(p , n.getId());
            newG.addNode(v);

//            System.out.println(v.getInfo());
//            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        for (EdgeJson e : mjf.getEdges())
        {
            Vertex u = (Vertex) newG.getNode(e.getSrc());
            Vertex v = (Vertex) newG.getNode(e.getDst());

            Edge ed = new Edge( u ,  e.getWeight() , v);
//            System.out.println(ed.getSrc() + ","+ ed.getWeight() + "," + ed.getDest());
//            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            newG.connect(ed);
        }

        init(newG);
        return true;
    }
}
