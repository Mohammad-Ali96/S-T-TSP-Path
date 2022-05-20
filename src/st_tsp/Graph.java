package st_tsp;
import java.io.DataInputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Graph {
    private int V;
    private int E;
    ArrayList<ArrayList<Node>> adjList;
    
    Graph(){
        
    }

    Graph(int V, int E) {
        this.V = V;
        this.E = E;
        adjList = new ArrayList<>();
        for(int i=0;i<this.V;i++){
            adjList.add(new ArrayList<>());
        }
    }
    
    public void readGraphFromUser() throws IOException {        
        System.out.println("Please input the Graph like u v w");
        BufferedReader readFromUser = new BufferedReader(new InputStreamReader(System.in));        
        for(int i=0;i<this.E;i++){
            String s = readFromUser.readLine();
            String[] arr = s.split(" ");
            int u = Integer.parseInt(arr[0]);
            int v = Integer.parseInt(arr[1]);
            int w = Integer.parseInt(arr[2]);
            this.adjList.get(u).add(new Node(v, w)); // for complete Graph
            this.adjList.get(v).add(new Node(u, w));
        }
    }
    
    
    public void generateRandomCompleteGraph() {        
        int[][] adjMatrix = new int[this.V][this.V];
        for(int i=0;i<this.V;i++){
            for(int j=0;j<this.V;j++){
                adjMatrix[i][j] = 0;
            }
        }
        
        int cnt = 0;
        Random random = new Random();
        for(int i=0;i<this.V;i++){
            for(int j=0;j<this.V;j++){
                if(i!= j &&  adjMatrix[i][j] == 0 && adjMatrix[j][i] == 0){
                    cnt++;
                    int w = random.nextInt(100);
                    adjMatrix[i][j] = w;
                    adjMatrix[j][i] = w;                    
                }
            }
        }
        
        System.out.println("cnt + " + cnt );
           
        for(int u=0;u<this.V;u++){
            for(int v=0;v<this.V;v++){
                if(u!= v &&  adjMatrix[u][v] > 0){
                    this.adjList.get(u).add(new Node(v, adjMatrix[u][v]));
                                       
                }
            }
        }
        
    }
    
    
    public String printGraph(){
        String msg = "";
        System.out.println("The Graph has " + this.getV() + " vertices and " + this.getE() + " edges");
        msg = "The Graph has " + this.getV() + " vertices and " + this.getE() + " edges\n";
        System.out.println("G=(" + this.getV() + ", " + this.getE() + ")");
        msg += "G=(" + this.getV() + ", " + this.getE() + ")" +"\n";
        for(int u=0;u<this.getV();u++){
            for(int v=0;v<this.adjList.get(u).size();v++){
                System.out.println("("+u+", "+
                        this.adjList.get(u).get(v).getDest() + ", " + this.adjList.get(u).get(v).getWeight()+")");
                msg += "("+u+", "+
                        this.adjList.get(u).get(v).getDest() + ", " + this.adjList.get(u).get(v).getWeight()+")\n";
                
            }
        }
        
        return msg;
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

    public ArrayList<ArrayList<Node>> getAdjList() {
        return adjList;
    }
  
    
    public int weightOfEdge(int u, int v){
        int weight = Integer.MAX_VALUE;
        for(int i=0;i<adjList.get(u).size();i++){
            if(adjList.get(u).get(i).getDest() == v){
                return  adjList.get(u).get(i).getWeight();
            }
        }
        return weight;
    }
    
    
}
