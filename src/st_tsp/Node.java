package st_tsp;
import java.util.*;
public class Node implements Comparator<Node>{
    private int dest;    
    private int weight;
   

    Node() {
        
    }

    Node(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }

    public int getDest() {
        return dest;
    }

    
    public int getWeight() {
        return weight;
    }

    @Override
    public int compare(Node u, Node v) {
       if (u.getWeight() < v.getWeight()) {
            return -1;
        }
        if (u.getWeight() > v.getWeight()) {
            return 1;
        }
        return 0;
    }
}
