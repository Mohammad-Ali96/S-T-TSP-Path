package st_tsp;

import java.util.*;
import javax.swing.JOptionPane;

public class Christofides_Algorithm {
    private Graph graph;
    private int S;
    private int T;
    private int[][] adjMatrix;
    String euler_path = "";
    String s_t_path = "";
    ArrayList<Integer> path;
    ArrayList<Integer> finalPath;
    int costPath = 0;
    ArrayList<Integer> listOfOddVertex;
    
    ArrayList<ArrayList<Node>> adjListAfterMST;
    boolean[] visited;
    boolean[] visitedPath;
    
    int costMST = 0;

    Christofides_Algorithm(Graph graph, int S, int T) {
        this.graph = graph;
        this.S = S;
        this.T = T;
        adjListAfterMST = new ArrayList<>();
        visitedPath = new boolean[this.graph.getV()];
        for (int i = 0; i < graph.getV(); i++) {
            adjListAfterMST.add(new ArrayList<>());
            visitedPath[i] = false;
        }
        path = new ArrayList<>();
        finalPath = new ArrayList<>();
    }
    
    private void clearVisitedArray() {
        visited = new boolean[this.graph.getV()];
        for (int i = 0; i < this.graph.getV(); i++) {
            visited[i] = false;
        }
    }

    public void computeMST() {
        MST_Prims algo = new MST_Prims(graph);
        algo.solve();
        costMST = algo.computeMSTCost();
        // not start from 0 because it source
        for (int i = 1; i < graph.getV(); i++) {
            if (i != algo.getParent()[i]) {
                adjListAfterMST.get(i).add(new Node(algo.getParent()[i], algo.getDistance()[i]));
                adjListAfterMST.get(algo.getParent()[i]).add(new Node(i, algo.getDistance()[i]));
            }

        }
        
    }

    
    private ArrayList<Integer> computeOddVertex() {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < graph.getV(); i++) {
            if (adjListAfterMST.get(i).size() % 2 != 0) {
                tmp.add(new Integer(i));
            }
        }
        return tmp;
    }

    int DFSCount(int node) {
        // Mark the current node as visited
        visited[node] = true;
        int cnt = 1;

        // Recur for all vertices adjacent to this vertex
        for (int i = 0; i < this.adjListAfterMST.get(node).size(); i++) {
            if (!visited[this.adjListAfterMST.get(node).get(i).getDest()]) {
                cnt += DFSCount(this.adjListAfterMST.get(node).get(i).getDest());
            }
        }

        return cnt;
    }

    boolean isValidNextEdge(int u, int v) {

        if (this.adjListAfterMST.get(u).size() == 1) {
            return true;
        }

        clearVisitedArray();
        int cnt1 = DFSCount(u);

        // 2.b) Remove edge (u, v) and after removing the edge, count
        // vertices reachable from u
        removeEdge(u, v);
        clearVisitedArray();
        int cnt2 = DFSCount(u);

        // 2.c) Add the edge back to the graph
        addEdge(u, v);

        // 2.d) If count1 is greater, then edge (u, v) is a bridge
        return (cnt1 > cnt2) ? false : true;
    }

    void printEulerUtil(int u) {

        for (int i = 0; i < this.adjListAfterMST.get(u).size(); i++) {
            int v = this.adjListAfterMST.get(u).get(i).getDest();

            if (isValidNextEdge(u, v)) {
                path.add(u);
                path.add(v);
                removeEdge(u, v);
                printEulerUtil(v);
            }
        }
    }

    public void convertListToMatrix() {
        adjMatrix = new int[this.graph.getV()][this.graph.getV()];

        for (int i = 0; i < this.graph.getAdjList().size(); i++) {
            for (int j = 0; j < this.graph.getAdjList().get(i).size(); j++) {
                adjMatrix[i][this.graph.getAdjList().get(i).get(j).getDest()]
                        = this.graph.getAdjList().get(i).get(j).getWeight();

                adjMatrix[this.graph.getAdjList().get(i).get(j).getDest()][i]
                        = this.graph.getAdjList().get(i).get(j).getWeight();

            }
        }

    }

    public boolean checkIfTrangleHold() {
        convertListToMatrix();
        for (int i = 0; i < this.graph.getV(); i++) {
            for (int j = 0; j < this.graph.getV(); j++) {
                for (int k = 0; k < this.graph.getV(); k++) {
                    if (adjMatrix[i][j] + adjMatrix[i][k] < adjMatrix[j][k]
                            || adjMatrix[i][k] + adjMatrix[k][j] < adjMatrix[i][j]
                            || adjMatrix[j][i] + adjMatrix[j][k] < adjMatrix[i][k]
                            && adjMatrix[i][j] > 0 && adjMatrix[i][k] > 0
                            && adjMatrix[j][k] > 0) {
                        JOptionPane.showMessageDialog(null, "The Nodes i = " + i + "and node j = "
                                + j + " and node k = " + k + " is not achieve a Triangle Rule");
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    public boolean checkIfGraphIsComplete() {
        boolean t = true;

        for (int i = 0; i < this.graph.getV(); i++) {
            if (this.graph.adjList.get(i).size() != this.graph.getV() - 1) {
                t = false;
                break;
            }
        }

        return t;
    }
    
    private boolean checkGraphIfConnected() {
        clearVisitedArray();
        DFS(0);
        for (int i = 1; i < this.graph.getV(); i++) {
            if (visited[i] == false && adjListAfterMST.get(i).size() > 0) {
                return false;
            }
        }
        return true;
    }
    
    public void removeFromOddList(int ele) {
        for (int i = 0; i < listOfOddVertex.size(); i++) {
            if (listOfOddVertex.get(i).equals(ele)) {
                listOfOddVertex.remove(i);
            }
        }
    }

    public void DFS(int node) {
        visited[node] = true;
        for (int i = 0; i < adjListAfterMST.get(node).size(); i++) {
            if (!visited[adjListAfterMST.get(node).get(i).getDest()]) {
                DFS(adjListAfterMST.get(node).get(i).getDest());
            }
        }
    }

    public int isEulerian() {
        // Check if all non-zero degree vertices are connected
        if (checkGraphIfConnected() == false) {
            return 0;
        }

        // Count vertices with odd degree
        int odd = 0;
        for (int i = 0; i < this.graph.getV(); i++) {
            if (adjListAfterMST.get(i).size() % 2 != 0) {
                odd++;
            }
        }
        System.out.println("the number of odd vertex " + odd);
        
        if (odd > 2) {
            return -1;
        }

        if (odd == 2) {
            return 1;
        }
        if (odd == 0) {
            return 1;
        }
        return -1;
        
    }

    public int testEuler() {
        int res = isEulerian();
        //cout<<"res        " <<res<<endl;
        if (res == 0) {
            System.out.println("graph is not Eulerian");
            return 0;
        } else if (res == 1) {
            System.out.println("graph has a Euler path");
            return 1;
        } else {
            System.out.println("graph has a Euler cycle");
            return 2;
        }
    }
    
    public void solve() {
        if (checkIfGraphIsComplete()) {
            computeMST();

            listOfOddVertex = computeOddVertex();

            for (int i = 0; i < listOfOddVertex.size(); i++) {
                System.out.print(listOfOddVertex.get(i) + " -- ");
            }
            System.out.println("");

            if (adjListAfterMST.get(S).size() % 2 == 0 && adjListAfterMST.get(T).size() % 2 == 0) {
                listOfOddVertex.add(S);
                listOfOddVertex.add(T);
            } else if (adjListAfterMST.get(S).size() % 2 != 0 && adjListAfterMST.get(T).size() % 2 == 0) {
                removeFromOddList(S);
                listOfOddVertex.add(T);
            } else if (adjListAfterMST.get(S).size() % 2 == 0 && adjListAfterMST.get(T).size() % 2 != 0) {
                removeFromOddList(T);
                listOfOddVertex.add(S);
            } else {
                System.out.println(adjListAfterMST.get(S).size());
                System.out.println(adjListAfterMST.get(T).size());
                removeFromOddList(S);
                removeFromOddList(T);

            }

            for (int i = 0; i < listOfOddVertex.size(); i += 2) {
                int u = listOfOddVertex.get(i);
                int v = listOfOddVertex.get(i + 1);
                int w = graph.weightOfEdge(u, v);
                System.out.println("add edge " + u + ", " + v);
                adjListAfterMST.get(u).add(new Node(v, w));
                adjListAfterMST.get(v).add(new Node(u, w));

            }

            // here we get two nodes with odd degree and reset of  nodes with even degree 
            // here we should find the euler path by dfs
            if (testEuler() == 1) {
                printEulerUtil(S);

            } else {
                System.out.println("Cann't find euler path");
            }

        } else {
            System.out.println("Graph Not Complete");
        }

    }
 
    
    
    public String computePath() {
        s_t_path = "S-T TSP Path \n";
        euler_path = "Euler Path \n";
        for (int i = 0; i < this.path.size(); i++) {
            euler_path += this.path.get(i) + " --> ";
            
            if (!visitedPath[this.path.get(i)]) {
                finalPath.add(this.path.get(i));
                visitedPath[this.path.get(i)] = true;
            }
        }

        for (int i = 0; i < this.finalPath.size() - 1; i++) {
            if (finalPath.get(i).equals(T)) {
                finalPath.remove(i);

            }
        }
        if (!finalPath.get(finalPath.size() - 1).equals(T)) {
            finalPath.add(T);
        }
        
        for (int i = 0; i < this.finalPath.size(); i++) {
            s_t_path += finalPath.get(i) + " --> ";
        }
           
        return  euler_path + "\n______________________\n" + s_t_path;
    }
    
    private void removeEdge(int u, int v) {

        for (int i = 0; i < this.adjListAfterMST.get(u).size(); i++) {
            int tmp = this.adjListAfterMST.get(u).get(i).getDest();
            if (tmp == v) {
                this.adjListAfterMST.get(u).remove(i);
                break;
            }
        }

        for (int i = 0; i < this.adjListAfterMST.get(v).size(); i++) {
            int tmp = this.adjListAfterMST.get(v).get(i).getDest();
            if (tmp == u) {
                this.adjListAfterMST.get(v).remove(i);
                break;
            }
        }

    }

    private void addEdge(int u, int v) {
        int w = this.graph.weightOfEdge(u, v);
        this.adjListAfterMST.get(u).add(new Node(v, w));
        this.adjListAfterMST.get(v).add(new Node(u, w));
    }
    
    
    public int computeS_T_Path_Cost(){
        int cost = 0;
        
        for(int i=0;i<finalPath.size() -1 ;i++){
            
            cost += this.adjMatrix[finalPath.get(i)][this.finalPath.get(i+1)];
            
        }
        
        return cost;
    }
    
    
}
