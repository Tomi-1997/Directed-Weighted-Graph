package api;

import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Graph_GUI {

    private DirectedWeightedGraph g;
    private DirectedWeightedGraphAlgorithms ga;

    private  int height;
    private  int width;

    public JFrame frame;
    private GraphDrawer graphDraw;
    private String imagePath = "C:\\Users\\tomto\\IdeaProjects\\Ex2_OOP\\Ex2\\src\\api\\DesignIdeas\\happy.png";


    public Graph_GUI(DirectedWeightedGraphAlgorithms ga, int h, int w)
    {
        this.ga = ga;
        this.g = ga.getGraph();
        this.height = h;
        this.width = w;

        init_frame();

    }

    private void init_frame()
    {
        this.graphDraw = new GraphDrawer(this.g);

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.frame = new JFrame("DWG");
        frame.setSize(this.width, this.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Drawer
        frame.add(this.graphDraw);

        // Menu
        JMenuBar myMenu = new JMenuBar();
        frame.setJMenuBar(myMenu);
        JMenu file = new JMenu("File");
        JMenu algo = new JMenu("Algo");

        // FILE
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem newGraph = new JMenuItem("Re-roll Graph");

        // ALGO
        JMenuItem isConnected = new JMenuItem("Connected");
        JMenuItem Center = new JMenuItem("Center");
        JMenuItem add = new JMenuItem("Add");
        JMenuItem tsp = new JMenuItem("Tsp");
        JMenuItem spd = new JMenuItem("ShortestPathDist");
        JMenuItem sp = new JMenuItem("ShortestPath");

        // ADD ALL
        file.add(save);
        file.add(load);
        file.add(newGraph);
        algo.add(isConnected);
        algo.add(Center);
        algo.add(add);
        algo.add(tsp);
        algo.add(spd);
        algo.add(sp);

        ActionListener myListener = new myListener(this, this.graphDraw);
        file.addActionListener(myListener);
        algo.addActionListener(myListener);
        newGraph.addActionListener(myListener);
        save.addActionListener(myListener);
        load.addActionListener(myListener);
        isConnected.addActionListener(myListener);
        Center.addActionListener(myListener);
        add.addActionListener(myListener);
        tsp.addActionListener(myListener);
        spd.addActionListener(myListener);
        sp.addActionListener(myListener);

        myMenu.add(file);
        myMenu.add(algo);

        ImageIcon icon = new ImageIcon(this.imagePath);
        this.frame.setIconImage(icon.getImage());
        frame.setVisible(true);
    }

    public Graph_GUI (DirectedWeightedGraphAlgorithms ga)
    {
        this.ga = ga;
        this.g = ga.getGraph();
        this.height = 500;
        this.width = 500;

        init_frame();
    }

    public void setGraph(DirectedWeightedGraph newG)
    {
        this.g = newG;
        ga.init(newG);
        this.graphDraw.g = newG;

        this.graphDraw.center = null;
        this.graphDraw.tsp = null;
        this.graphDraw.shortestPath = null;
        this.frame.repaint();
    }

    class myListener implements ActionListener
    {
        Graph_GUI myGUI;
        GraphDrawer myDraw;

        public myListener(Graph_GUI graph_gui , GraphDrawer myDraw)
        {
            this.myGUI = graph_gui;
            this.myDraw = myDraw;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String action = e.getActionCommand();
            switch (action)
            {
                case "Re-roll Graph":
                {
                    String input = JOptionPane.showInputDialog("How many vertices?");
                    int v = (int) (Math.random()*20 + 5);
                    try
                    {
                        v = Integer.parseInt(input);
                        v--;
                    }
                    catch (NumberFormatException err)
                    {
                        err.printStackTrace();
                        System.out.println("Input is:" + input);
                    }
                    if (v < 1 || v > 1000) v = (int) (Math.random() * 20 + 5);
                    this.myGUI.setGraph(GraphBuilder.getGraph(v));
                    break;
                }
                case "Save":
                {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");

                    int userSelection = fileChooser.showSaveDialog(this.myGUI.frame);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        this.myGUI.ga.save(fileToSave.getAbsolutePath()+".json");
                    }
                    break;
                }
                case "Load":
                {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter( "Json files",
                            "json");
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(this.myGUI.frame);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                                this.myGUI.ga.load(chooser.getSelectedFile().getAbsolutePath());
                                this.myDraw.g = ga.getGraph();
                                this.myDraw.repaint();
                    }
                    break;
                }
                case "Connected":
                {
                    this.myDraw.isConnected = this.myGUI.ga.isConnected();
                    JOptionPane.showMessageDialog(null , this.myDraw.isConnected);
                    break;
                }
                case "Center":
                {
                    if (this.myGUI.g.nodeSize() < 100)
                    {
                        JOptionPane.showMessageDialog(null , "Now calculating center and highlighting in orange");
                        this.myDraw.center = this.myGUI.ga.center();
                    }
                    else
                        JOptionPane.showMessageDialog(null , "Graph too big");

                    break;
                }
                case "Add":
                {
                    String input = JOptionPane.showInputDialog("Type new vertex location: three numbers x,y,z separated by a comma " +
                            "\n for example 20,40,0" +
                            "\n current pos for one of the vertexes:" +
                            this.myGUI.g.getNode(0).getInfo());
                    String[] input_split = input.split(",");
                    double x = 0 , y = 0, z = 0;
                    try
                    {
                        x = Double.parseDouble(input_split[0]);
                        y = Double.parseDouble(input_split[1]);
                    }
                    catch (Exception err)
                    {
                        err.printStackTrace();
                        break;
                    }
                    int key = this.myGUI.g.nodeSize();
                    Vertex v = new Vertex(new Point3D(x,y,z),key);

                    DirectedWeightedGraph copy_graph = this.myGUI.ga.copy();
                    copy_graph.addNode(v);
                    this.myGUI.setGraph(copy_graph);
                    break;

                }
                case "Remove":
                {
                    String input = JOptionPane.showInputDialog("Insert key to remove:");
                    int key = -1;
                }
                case "Tsp":
                {
                    String input = JOptionPane.showInputDialog("Insert several key nodes to travel separated by comma:" +
                            "\n Current node size is:" + this.myGUI.g.nodeSize());
                    String[] input_split = input.split(",");
                    try
                    {
                        ArrayList<NodeData> tsp = new ArrayList<>();
                        for (String s : input_split)
                        {
                            tsp.add(this.myGUI.g.getNode(Integer.parseInt(s)));
                        }

                        this.myGUI.graphDraw.tsp = (ArrayList<NodeData>) this.myGUI.ga.tsp(tsp);
                    }
                    catch (Exception err)
                    {
                        err.printStackTrace();
                        JOptionPane.showMessageDialog(null , "Invalid input, or no such path");
                        this.myGUI.graphDraw.tsp = null;
                        break;
                    }
                    JOptionPane.showMessageDialog(null , "Highlighting in blue a path which passes through input");
                    break;
                }
                case "ShortestPathDist":
                {
                    String input = JOptionPane.showInputDialog("Insert source key and destination key, separated by a comma.");
                    int source = -1;
                    int dest = -1;
                    String[] str = input.split(",");
                    try
                    {
                        source = Integer.parseInt(str[0]);
                        dest = Integer.parseInt(str[1]);

                        double cost = this.myGUI.ga.shortestPathDist(source , dest);
                        JOptionPane.showMessageDialog(null , "The shortest distance is:" + cost);
                    }
                    catch (NumberFormatException err)
                    {
                        err.printStackTrace();
                        break;
                    }
                    break;
                }
                case "ShortestPath":
                {
                    String input = JOptionPane.showInputDialog("Insert source key and destination key, separated by a comma.");
                    int source = -1;
                    int dest = -1;
                    String[] str = input.split(",");
                    try
                    {
                        source = Integer.parseInt(str[0]);
                        dest = Integer.parseInt(str[1]);

                        JOptionPane.showMessageDialog(null , "Now highlighting path in green");
                        ArrayList<NodeData> list = (ArrayList<NodeData>) this.myGUI.ga.shortestPath(source , dest);
                        this.myGUI.graphDraw.shortestPath = list;
                    }
                    catch (NumberFormatException err)
                    {
                        err.printStackTrace();
                        break;
                    }
                    break;
                }
            }
        }
    }

    class GraphDrawer extends JComponent
    {
        public DirectedWeightedGraph g;
        NodeData center;
        boolean isConnected;
        ArrayList<NodeData> tsp;
        ArrayList<NodeData> shortestPath;

        public GraphDrawer(DirectedWeightedGraph g)
        {
            this.g = g;
        }

        public void paint(Graphics g) {

            Dimension myDim = this.getSize();

            // scales[0] = min X, scales[1] = min Y, scales[2] = max X , scales[3] = max Y
            double[] scales = new double[4];
            init(this.g.nodeIter(), scales);


//            System.out.println(Arrays.toString(scales));
//            System.out.println("X Difference: " + (scales[2] - scales[0]));
//            System.out.println("Y Difference: " + (scales[3] - scales[1]));

            // DRAW EDGES
            Iterator<EdgeData> iterator_edge = this.g.edgeIter();
            while (iterator_edge.hasNext())
            {
                EdgeData e = iterator_edge.next();
                drawEdge(e , myDim , scales , g , Color.gray );
            }

            // DRAW VERTICES
            Iterator<NodeData> iterator_node = this.g.nodeIter();
            while (iterator_node.hasNext())
            {
                NodeData node = iterator_node.next();
                drawNode(node , myDim , scales , g , Color.BLACK);
            }

            // DRAW CENTER only if center was calculated
            if (this.center != null)
            {
                drawNode(this.center , myDim , scales , g , Color.ORANGE);
                Iterator<EdgeData> cen_iterator = this.g.edgeIter(this.center.getKey());
                // DRAW CENTER'S EDGES
                while (cen_iterator.hasNext())
                {
                    EdgeData cen_edge = cen_iterator.next();
                    drawEdge(cen_edge , myDim , scales , g , Color.ORANGE );
                }
            }

            // DRAW TSP
            if (this.tsp != null)
            {
                for (int i = 0; i < tsp.size() - 1; i++) {
                    drawNode(tsp.get(i) , myDim , scales , g , Color.BLUE);
                    EdgeData e = this.g.getEdge(tsp.get(i).getKey(), tsp.get(i+1).getKey());
                    if (e != null)
                        drawEdge(e , myDim , scales , g , Color.BLUE);
                }
                drawNode(tsp.get(tsp.size()-1) , myDim , scales , g , Color.BLUE);

            }

            // DRAW THE SHORTEST PATH
            if (this.shortestPath != null)
            {
                for (int i = 0; i < this.shortestPath.size() - 1; i++)
                {
                    drawNode(shortestPath.get(i), myDim , scales , g , Color.GREEN);
                    EdgeData e = this.g.getEdge(this.shortestPath.get(i).getKey(), this.shortestPath.get(i+1).getKey());
                    if (e != null)
                        drawEdge(e , myDim , scales , g , Color.GREEN);
                }
                drawNode(shortestPath.get(shortestPath.size()-1), myDim , scales , g , Color.GREEN);

            }

        }

        private void drawEdge(EdgeData e , Dimension myDim , double[] scales , Graphics g,  Color cl)
        {

            NodeData src = this.g.getNode(e.getSrc());
            NodeData dest = this.g.getNode(e.getDest());

            int x = scale(src.getLocation().x() , scales[2] , scales[0] , myDim.getWidth());
            int y = scale(src.getLocation().y() , scales[3] , scales[1] , myDim.getHeight());

            int dst_x = scale(dest.getLocation().x() , scales[2] , scales[0] , myDim.getWidth());
            int dst_y = scale(dest.getLocation().y() , scales[3] , scales[1] , myDim.getHeight());

            g.setColor(cl);
            g.drawLine(x, y, dst_x, dst_y);
        }

        private void drawNode(NodeData v , Dimension myDim ,double[] scales , Graphics g, Color cl)
        {

            int x = scale(v.getLocation().x() , scales[2] , scales[0] , myDim.getWidth());
            int y = scale(v.getLocation().y() , scales[3] , scales[1] , myDim.getHeight());

//            System.out.println(x);
//            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//            System.out.println(y);

            // Scale dot size to screen
            double width = Math.max(5, myDim.width / 50);
            double height = Math.max(5, myDim.height / 50);

            // So the dots won't appear squished
            if (height > width)
                width = height;
            else
                height = width;

            x = (int) ( x - width/2);
            y = (int) ( y - height/2);

            // Node
            g.setColor(cl);
            g.fillOval(x, y, (int) width, (int) height);
            g.setColor(Color.RED);

            for (int i = 0; i < 3; i++)
                g.drawString(v.getKey()+"" , (int)(x + width/4),(int)(y + 5*height/6));
        }

        /***
         *
         * @param x
         * @param max
         * @param min
         * @param ratio
         * @return x with relation to current window size
         */
        public int scale(double x , double max, double min , double ratio)
        {
            return (int) (( (ratio * 0.93)  * (x - min) / (max - min)) + ratio*0.02);
        }
    }

    /***
     *
     * @param nodeIter --> Iterator of nodes / vertices
     * @param a ---------> Array of 4 elements, changes its values to: [ minX , minY , maxX , maxY ]
     */
    private void init(Iterator<NodeData> nodeIter, double[] a) {

        double min_x = Double.MAX_VALUE;
        double min_y = Double.MAX_VALUE;
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;

        while (nodeIter.hasNext()) {
            NodeData n = nodeIter.next();
            double x = n.getLocation().x();
            double y = n.getLocation().y();

            if (x < min_x)
                min_x = x;

            if (x > max_x)
                max_x = x;

            if (y < min_y)
                min_y = y;

            if (y > max_y)
                max_y = y;
        }
        a[0] = min_x;
        a[1] = min_y;
        a[2] = max_x;
        a[3] = max_y;
    }

    }
