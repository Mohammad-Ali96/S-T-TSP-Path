package st_tsp;

import java.util.*;

public class MST_Prims {

    private Graph graph;
    private int[] parent;
    private int[] distance;
    private boolean[] isVisited;
    PriorityQueue<Node> PQ;

    public MST_Prims(Graph graph) {
        this.graph = graph;
    }

    private void initMST() {
        parent = new int[graph.getV()];
        distance = new int[graph.getV()];
        isVisited = new boolean[graph.getV()];
        PQ = new PriorityQueue<Node>(graph.getV(), new Node());
        parent[0] = 0;
        distance[0] = 0;
        for (int i = 1; i < graph.getV(); i++) {
            parent[i] = -5;
            distance[i] = Integer.MAX_VALUE;
            isVisited[i] = false;
        }
    }

    public void solve() {
        initMST();
        PQ.add(new Node(0, 0));
        while (!PQ.isEmpty()) {
            Node node = PQ.poll(); // extract min
            isVisited[node.getDest()] = true;
//            System.out.println(node.getDest() + "   " + node.getWeight());
            for (int i = 0; i < graph.adjList.get(node.getDest()).size(); i++) {
                Node child = graph.adjList.get(node.getDest()).get(i);
                if (parent[child.getDest()] == -5) {
                    parent[child.getDest()] = node.getDest();
                    distance[child.getDest()] = child.getWeight();
                    PQ.add(new Node(child.getDest(), child.getWeight()));
                } else if (!isVisited[child.getDest()] && child.getWeight() < distance[child.getDest()]) {
                    parent[child.getDest()] = node.getDest();
                    distance[child.getDest()] = child.getWeight();
                    PQ.add(new Node(child.getDest(), child.getWeight()));
                }
            }
        }
    }

    public void printMST() {
        System.out.println("The MST is: ");
        int costMST = 0;
        for (int i = 0; i < graph.getV(); i++) {
            System.out.println(i + "-->" + parent[i] + ", " + distance[i]);
            costMST += distance[i];
        }
        System.out.println("The Weight of MST is " + costMST);
    }
    
    
    public int computeMSTCost(){
        int costMST = 0;
        for (int i = 0; i < graph.getV(); i++) {
            costMST += distance[i];
        }
        return costMST;
    }
    

    public int[] getParent() {
        return parent;
    }

    public int[] getDistance() {
        return distance;
    }
   
}
