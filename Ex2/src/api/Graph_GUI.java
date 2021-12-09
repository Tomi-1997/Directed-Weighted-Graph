package api;

import org.w3c.dom.Node;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph_GUI {

    private DirectedWeightedGraph g;
    private DirectedWeightedGraphAlgorithms ga;

    private  int height;
    private  int width;

    public JFrame frame;
    private GraphDrawer graphDraw;

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

        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem newGraph = new JMenuItem("Re-roll Graph");
        JMenuItem isConnected = new JMenuItem("Connected");
        JMenuItem Center = new JMenuItem("Center");


        file.add(save);
        file.add(load);
        file.add(newGraph);
        algo.add(isConnected);
        algo.add(Center);

        ActionListener myListener = new myListener(this, this.graphDraw);

        file.addActionListener(myListener);
        algo.addActionListener(myListener);
        newGraph.addActionListener(myListener);
        save.addActionListener(myListener);
        load.addActionListener(myListener);
        isConnected.addActionListener(myListener);
        Center.addActionListener(myListener);

        myMenu.add(file);
        myMenu.add(algo);

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
                    this.myGUI.setGraph(GraphBuilder.getGraph((int)(Math.random()*20) + 5));
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
                    if (this.myGUI.g.nodeSize() < 40)
                    this.myDraw.center = this.myGUI.ga.center();
                    break;
                }

            }
        }
    }

    class GraphDrawer extends JComponent {
        public DirectedWeightedGraph g;
        NodeData center;
        boolean isConnected;
        ArrayList<NodeData> tsp;

        public GraphDrawer(DirectedWeightedGraph g) {
            this.g = g;
        }

        public void paint(Graphics g) {
            Dimension myDim = this.getSize();

            // scales[0] = min X, scales[1] = min Y, scales[2] = max X , scales[3] = max Y
            double[] scales = new double[4];
            init(this.g.nodeIter(), scales);

            // DRAW VERTICES AND EDGES
            Iterator<NodeData> iterator_node = this.g.nodeIter();
            while (iterator_node.hasNext()) {

                NodeData node = iterator_node.next();

                // Node
                drawNode(node , myDim , scales , g , Color.BLACK);

                // Edges
                Iterator<EdgeData> iterator_edge = this.g.edgeIter();
                while (iterator_edge.hasNext())
                {
                    EdgeData e = iterator_edge.next();
                    drawEdge(e , myDim , scales , g , Color.BLACK , this.g.getNode(e.getSrc()));
                }

                if (this.center != null)
                {
                    drawNode(this.center , myDim , scales , g , Color.RED);
                }
            }
        }

        private void drawEdge(EdgeData e , Dimension myDim , double[] scales , Graphics g,  Color cl , NodeData v)
        {

            int x = (int) ((myDim.width / scales[2]) * v.getLocation().x() + scales[0]);
            int y = (int) ((myDim.height / scales[3]) * v.getLocation().y() + scales[1]);

            int dst_x = (int) ((myDim.width / scales[2]) * this.g.getNode(e.getDest()).getLocation().x() + scales[0]);
            int dst_y = (int) ((myDim.width / scales[3]) * this.g.getNode(e.getDest()).getLocation().y() + scales[1]);

            g.setColor(cl);
            g.drawLine(x, y, dst_x, dst_y);
        }

        private void drawNode(NodeData v , Dimension myDim ,double[] scales , Graphics g, Color cl)
        {

            // Scale node position according to minimal x,y / maximum x,y and screen size.
            int x = (int) ((myDim.width / scales[2]) * v.getLocation().x() );
            int y = (int) ((myDim.height / scales[3]) * v.getLocation().y() );

//            System.out.println(x);
//            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//            System.out.println(y);

            // Scale dot size to screen
            double width = Math.max(5, myDim.width / 100);
            double height = Math.max(5, myDim.height / 100);

            // So the dots won't appear squished
            if (height > width)
                width = height;
            else
                height = width;

            // Node
            g.setColor(cl);
            g.fillOval(x, y, (int) width, (int) height);
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
