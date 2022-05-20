package st_tsp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

public class Christofides_Algorithm_Program extends JFrame {

    private JLabel VLabel;
    private JLabel ELabel;
    private JLabel SLabel;
    private JLabel TLabel;
    private JLabel CostSTLabel;

    private JTextField VtextField;
    private JTextField EtextField;
    private JTextField StextField;
    private JTextField TtextField;

    private JButton randomBtn;
    private JButton fileBtn;
    private JButton completeBtn;
    private JButton trangleBtn;
    private JButton algoBtn;
    private JButton exitBtn;
    private JButton clearBtn;

    boolean isTrangle = false;
    boolean isComplete = false;

    private JTextArea area;
    private JTextArea graphArea;

    Graph graph;
    Christofides_Algorithm algorithm;

    public Christofides_Algorithm_Program() {
        this.setVisible(true);
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Solve S T TSP Problem Using  Christofides Algorithm");
        this.setResizable(false);
        this.setLayout(null);

        // Create Components
        // Labels
        VLabel = new JLabel("#N (number or vertices)");
        VLabel.setFont(new Font("Helvetica", Font.BOLD, 14));

        ELabel = new JLabel("#E (number or edges)");
        ELabel.setFont(new Font("Helvetica", Font.BOLD, 14));

        SLabel = new JLabel("S (Source node)");
        SLabel.setFont(new Font("Helvetica", Font.BOLD, 14));

        TLabel = new JLabel("T (Target node)");
        TLabel.setFont(new Font("Helvetica", Font.BOLD, 14));

        CostSTLabel = new JLabel("The Cost of S T TSP Path is: ");
        CostSTLabel.setFont(new Font("Helvetica", Font.BOLD, 15));

        // Text Fields
        VtextField = new JTextField();
        EtextField = new JTextField();
        StextField = new JTextField();
        TtextField = new JTextField();

        // Buttons
        randomBtn = new JButton("Random Graph");
        randomBtn.setToolTipText("Generate Random Graph");
        randomBtn.setFont(new Font("Helvetica", Font.PLAIN, 15));
        randomBtn.setBackground(Color.LIGHT_GRAY);

        fileBtn = new JButton("Graph From File");
        fileBtn.setToolTipText("Generate Graph from Specific File");
        fileBtn.setFont(new Font("Helvetica", Font.PLAIN, 15));
        fileBtn.setBackground(Color.LIGHT_GRAY);

        
        completeBtn = new JButton("Check if Graph is complete");
        completeBtn.setToolTipText("Check if Graph is complete");
        completeBtn.setFont(new Font("Helvetica", Font.PLAIN, 15));
        completeBtn.setBackground(Color.LIGHT_GRAY);

        trangleBtn = new JButton("Check if Graph Achieve Trangle equation");
        trangleBtn.setToolTipText("Check if Graph Achieve Trangle equation");
        trangleBtn.setFont(new Font("Helvetica", Font.PLAIN, 15));
        trangleBtn.setBackground(Color.LIGHT_GRAY);

        algoBtn = new JButton("Run Christofides Algorithm");
        algoBtn.setToolTipText("Run Christofides Algorithm");
        algoBtn.setFont(new Font("Helvetica", Font.PLAIN, 15));
        algoBtn.setBackground(Color.LIGHT_GRAY);

        exitBtn = new JButton("exit");
        exitBtn.setToolTipText("Close The Program");
        exitBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        exitBtn.setBackground(Color.LIGHT_GRAY);

        clearBtn = new JButton("clear");
        clearBtn.setToolTipText("Clear Data");
        clearBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        clearBtn.setBackground(Color.LIGHT_GRAY);

        area = new JTextArea(10, 10);
        graphArea = new JTextArea(10, 10);

        this.add(VLabel);
        this.add(ELabel);
        this.add(SLabel);
        this.add(TLabel);

        this.add(VtextField);
        this.add(EtextField);
        this.add(StextField);
        this.add(TtextField);

        this.add(randomBtn);
        this.add(fileBtn);
        this.add(algoBtn);
        this.add(completeBtn);
        this.add(trangleBtn);
        this.add(clearBtn);
        this.add(exitBtn);
        this.add(graphArea);

        this.add(area);
        this.add(CostSTLabel);

        VLabel.setBounds(10, 10, 200, 25);
        ELabel.setBounds(10, 40, 200, 25);
        SLabel.setBounds(10, 70, 200, 25);
        TLabel.setBounds(10, 100, 200, 25);

        VtextField.setBounds(210, 10, 120, 25);
        EtextField.setBounds(210, 40, 120, 25);
        StextField.setBounds(210, 70, 120, 25);
        TtextField.setBounds(210, 100, 120, 25);

        randomBtn.setBounds(420, 15, 220, 25);
        fileBtn.setBounds(420, 55, 220, 25);

        completeBtn.setBounds(700, 15, 240, 25);
        trangleBtn.setBounds(700, 55, 240, 25);
        algoBtn.setBounds(700, 95, 240, 25);

        clearBtn.setBounds(970, 50, 80, 24);
        exitBtn.setBounds(1070, 50, 80, 24);

        graphArea.setBounds(20, 160, 300, 420);
        area.setBounds(520, 160, 380, 420);
        CostSTLabel.setBounds(20, 600, 250, 40);

        randomBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String v = VtextField.getText();
                int V = Integer.parseInt(v);

                String m = EtextField.getText();
                int E = Integer.parseInt(m);

                String s = StextField.getText();
                int S = Integer.parseInt(s);

                String t = TtextField.getText();
                int T = Integer.parseInt(t);

                graph = new Graph(V, E);
                graph.generateRandomCompleteGraph();

                graphArea.setText(graph.printGraph());

                algorithm = new Christofides_Algorithm(graph, S, T);

            }
        });

        algoBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (algorithm != null) {

                    if (isTrangle && isComplete) { 
                        algorithm.solve();
                        String txt = algorithm.computePath();
                        int mstCost = algorithm.costMST;
                        String s1 = "The Cost of MST is : " + mstCost + "\n";
                        int pathCost = algorithm.computeS_T_Path_Cost();
                        String s2 = "The Cost of S-T TSP Path is : " + pathCost;
                        CostSTLabel.setText(s2);
                        area.setText(s1 + txt + "\n" + s2);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please check Graph first");

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please input graph first");
                }
            }
        });

        trangleBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (algorithm != null) {
                    isTrangle = algorithm.checkIfTrangleHold();

                    if (isTrangle) {
                        JOptionPane.showMessageDialog(null, "The Graph hold Traangle Rule");
                    } else {
                        JOptionPane.showMessageDialog(null, "The Graph does not hold Traangle Rule");
                        algorithm = null;

                        area.setText("");
                        graphArea.setText("");

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please input graph first");
                }
            }
        });

        completeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (algorithm != null) {
                    isComplete = algorithm.checkIfGraphIsComplete();

                    if (isComplete) {
                        JOptionPane.showMessageDialog(null, "This Graph is Complete");
                    } else {
                        JOptionPane.showMessageDialog(null, "This Graph is not Complete");
                        algorithm = null;
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please input graph first");
                }
            }
        });

        exitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        clearBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                algorithm = null;

                VtextField.setText("");
                EtextField.setText("");
                StextField.setText("");
                TtextField.setText("");
                area.setText("");
                graphArea.setText("");
                CostSTLabel.setText("The Cost of S T TSP Path is: ");

            }
        });

        fileBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String path = JOptionPane.showInputDialog(null, "input the file path");
                try {
                    FileReader file = new FileReader(path);
                    BufferedReader br = new BufferedReader(file);
                    String line;
                    line = br.readLine();
                    String[] ele = line.split(" ");

                    int V = Integer.parseInt(ele[0]);
                    VtextField.setText(ele[0]);

                    int E = Integer.parseInt(ele[1]);
                    EtextField.setText(ele[1]);

                    int S = Integer.parseInt(ele[2]);
                    StextField.setText(ele[2]);

                    int T = Integer.parseInt(ele[3]);
                    TtextField.setText(ele[3]);
                    
                    
                    Graph graph = new Graph(V, E);
                   

                    while ((line = br.readLine()) != null) {
                        ele = line.split(" ");
                        int u = Integer.parseInt(ele[0]);
                        int v = Integer.parseInt(ele[1]);
                        int w = Integer.parseInt(ele[2]);
                        graph.adjList.get(u).add(new Node(v, w));
                        graph.adjList.get(v).add(new Node(u, w));
                    }
                    graphArea.setText(graph.printGraph());
                    
                     MST_Prims m = new MST_Prims(graph);
                    m.solve();
                    m.printMST();
                    
                    algorithm = new Christofides_Algorithm(graph, S, T);
                    

                } catch (Exception ee) {
                    System.out.println(ee.toString());
                }

            }
        });

    }

}
