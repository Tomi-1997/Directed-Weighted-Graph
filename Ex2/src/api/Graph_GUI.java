package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class Graph_GUI {

    private DirectedWeightedGraph g;
    private DirectedWeightedGraphAlgorithms ga;

    private final int height;
    private final int width;

    private JFrame frame;
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

        file.add(save);
        file.add(load);
        file.add(newGraph);
        algo.add(isConnected);

        ActionListener myListener = new myListener(this);

        file.addActionListener(myListener);
        algo.addActionListener(myListener);
        newGraph.addActionListener(myListener);

        myMenu.add(file);
        myMenu.add(algo);
        frame.setVisible(true);
    }

    public void setGraph(DirectedWeightedGraph newG)
    {
        this.g = newG;
        ga.init(newG);
        this.graphDraw.g = newG;
        this.frame.repaint();
    }


    class myListener implements ActionListener
    {
        Graph_GUI myGUI;

        public myListener(Graph_GUI graph_gui) {
            this.myGUI = graph_gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("Re-roll Graph"))
            {
                System.out.println("New graph rolled");
                this.myGUI.setGraph(GraphBuilder.getGraph((int)(Math.random()*20) + 10));
            }
        }
    }

    class GraphDrawer extends JComponent {
        public DirectedWeightedGraph g;

        public GraphDrawer(DirectedWeightedGraph g) {
            this.g = g;
        }

        public void paint(Graphics g) {
            g.setColor(Color.BLACK);
            Dimension myDim = this.getSize();

            // scales[0] = min X, scales[1] = min Y, scales[2] = max X , scales[3] = max Y
            double[] scales = new double[4];
            init(this.g.nodeIter(), scales);

            // DRAW VERTICES
            Iterator<NodeData> iterator_node = this.g.nodeIter();
            while (iterator_node.hasNext()) {

                // Scale node position according to minimal x,y / maximum x,y and screen size.
                NodeData v = iterator_node.next();
                int x = (int) ((myDim.width / scales[2]) * v.getLocation().x() + scales[0]);
                int y = (int) ((myDim.height / scales[3]) * v.getLocation().y() + scales[1]);

                // Scale dot size to screen
                double width = Math.max(5, myDim.width / 100);
                double height = Math.max(5, myDim.height / 100);

                // So the dots won't appear squished
                if (height > width)
                    width = height;
                else
                    height = width;

                // Node
                g.fillOval(x, y, (int) width, (int) height);

                // Edges
                Iterator<EdgeData> iterator_edge = this.g.edgeIter(v.getKey());
                while (iterator_edge.hasNext()) {
                    EdgeData e = iterator_edge.next();
                    int dst_x = (int) ((myDim.width / scales[2]) * this.g.getNode(e.getDest()).getLocation().x() + scales[0]);
                    int dst_y = (int) ((myDim.width / scales[3]) * this.g.getNode(e.getDest()).getLocation().y() + scales[1]);

                    g.drawLine(x, y, dst_x, dst_y);

                }
            }
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
