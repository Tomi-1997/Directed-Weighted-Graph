package api;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph_GUI extends Canvas {

    private DWG g;
    //private DWG_algo g_algo;

    private int height;
    private int width;

    private JFrame jf;
    private JPanel graph_panel;
    private JPanel buttons_panel;


    private JMenu menu;
    private JMenuItem save, load;
    private ButtonGroup buttons;

    public Graph_GUI(DWG g, int h, int w)
    {
        this.g = g;
        this.height = h;
        this.width = w;

        // Frame
        init_frame();

        // Graph panel


        // Buttons panel


        // Menu for saving / loading
    }

    private void init_frame()
    {
        this.jf = new JFrame();
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = new Dimension();
        d.setSize(this.width , this.height);
        this.jf.setPreferredSize(d);
        jf.setBounds(0 , 0 , this.width , this.height );
        jf.setLayout(null);
        jf.setVisible(true);
    }

    // For testing - will be deleted.
    public static void main(String[]args)
    {
        Graph_GUI gui = new Graph_GUI(null , 400 , 400);
    }

}
