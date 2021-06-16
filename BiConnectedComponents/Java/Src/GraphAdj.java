//-----------------------------------------------------
// Author: Olivia Anastassov     
// Date:   June 2, 2021     
// Description: Graph represented with adjacency lists
//              templates for biconnectivity.
//-----------------------------------------------------
import java.util.*;
public class GraphAdj{
    private ArrayList<Integer>[] adjLists;

//local var - used to help with debugging
    final static boolean DEBUG = false;

//utilities
    public static int toMathId(int n){
        return n+ 1;
    }
    public static void printDebug(String message){
        if (DEBUG){
            System.out.println(message);
        }
    }
    public static void printArrayInt(int [] array){
        for (int i = 0; i< array.length; i++){
            printDebug("" + toMathId(i)+ ": "+ array[i]);
        }
    }

//------------------------------------- 
// Constructors 
//-------------------------------------
    public GraphAdj(int nrVertices){
        adjLists = new ArrayList[nrVertices];//throws a warning 
        for(int i = 0; i<nrVertices; i++){
            adjLists[i] = new ArrayList<Integer>();
        }
    }
    public GraphAdj(ArrayList<Integer>[] adjLists){
        this.adjLists = adjLists;
    }

//------------------------------------- 
// Getters
//-------------------------------------
    public ArrayList<Integer>[] getAdjLists(){
        return adjLists;
    }
    public ArrayList<Integer> getAdjLists(int i){
        return adjLists[i];
    }
    public int getNrVertices(){
        return adjLists.length;
    }
    public int getNrNeighbors(int i){
        return adjLists[i].size();
    }

//------------------------------------- 
// Setters
//-------------------------------------
    public void setAdjLists(ArrayList<Integer>[] adjLists){
        this.adjLists = adjLists;
    }

//------------------------------------- 
// Serialize and print
//-------------------------------------
    public String toString(){
        int nrVertices = this.getNrVertices();
        String graphString = "";

        for(int v = 0; v<nrVertices; v++){
            graphString += toMathId(v) + ": ";
            ArrayList<Integer> adjList = getAdjLists(v);
            for(int i = 0; i < adjList.size(); i++){
                int neighbr = adjList.get(i);
                graphString += "" + toMathId(neighbr) + " ";
            }
            if (v < nrVertices - 1){
                graphString += "\n";
            }
        }
        return graphString;
    }
    public void printGraph(){
        System.out.println(this.toString());
    }
    //debugging helper
    private void printVisited(boolean[] visited){
        printDebug("\nvisited:");
        for(int j = 0; j<visited.length; j++){
            printDebug(toMathId(j)+ ": " + visited[j]);
        }
        printDebug("");
    }

//------------------------------------- 
// Modifiers
//-------------------------------------
    public void addEdge(int u, int v){
        if (u != v){
            adjLists[u].add(v);
            adjLists[v].add(u);
        }
    }

//------------------------------------- 
// Converters
//-------------------------------------
    public GraphEdges toGraphEdges(){
        ArrayList<Edge> Edges = new ArrayList<Edge>();
        for(int i = 0; i<this.getNrVertices(); i++){
            for(int r = 0; r<adjLists[i].size(); r++){
                Edges.add(new Edge(i,adjLists[i].get(r)));
            }
        }
        GraphEdges toGraph = new GraphEdges(this.getNrVertices(),Edges);
        return toGraph;
    }
//---------------------------------------------------- 
// Helper
//----------------------------------------------------
public DataStructures BC(){
    ArrayList<Integer> temp = new ArrayList<Integer>();
    DataStructures Data = new DataStructures(this.getNrVertices());
    Data.discTime = 1;
    for(int i = 0; i<Data.discoveryTime.length; i++){
        Data.parent[i] = -1;
        Data.discoveryTime[i] = -1;
    }
    for(int i = 0; i<Data.discoveryTime.length; i++){
        if (Data.discoveryTime[i] == -1){
                Data = biConnectivity(i, Data);
        }
        while (Data.stack.size() > 0) {
            temp.add(Data.stack.getLast().getVertex1());
            temp.add(Data.stack.getLast().getVertex2());
            Data.stack.removeLast();
        }
    }
    temp = new ArrayList <> (new LinkedHashSet<>(temp));//to get rid of duplicates
    Data.ExtractBC.add(temp);
    return Data;
}
//---------------------------------------------------- 
// Biconnected components 
//----------------------------------------------------
public DataStructures biConnectivity(int i, DataStructures Data){
    ArrayList<Integer> temp = new ArrayList<Integer>();
    Data.low[i] = Data.discTime;
    Data.discoveryTime[i] = Data.low[i];
    Data.discTime = Data.discTime + 1;
    int children = 0;
    for(int child : adjLists[i]){
        if(Data.discoveryTime[child] == -1){
            children++;
            Data.parent[child] = i;
            Data.stack.add(new Edge(i, child));
            Data = biConnectivity(child, Data);
            Data.low[i] = Math.min(Data.low[i], Data.low[child]);
            if((Data.discoveryTime[i] == 1 && children >1) || (Data.discoveryTime[i]>1 && Data.low[child]>= Data.discoveryTime[i])){
            while(Data.stack.getLast().getVertex1() != i || Data.stack.getLast().getVertex2() != child){
                temp.add(Data.stack.getLast().getVertex1());
                temp.add(Data.stack.getLast().getVertex2());
                Data.stack.removeLast();
            }
            temp.add(Data.stack.getLast().getVertex1());
            temp.add(Data.stack.getLast().getVertex2());
            Data.stack.removeLast();
            }
        }
        else if(child != Data.parent[i] && Data.discoveryTime[child] < Data.discoveryTime[i]){
            Data.low[i] = Math.min(Data.low[i], Data.discoveryTime[child]);
            Data.stack.add(new Edge(i, child));
        }   
    }
    if(temp.size() != 0){
    temp = new ArrayList <> (new LinkedHashSet<>(temp));//to get rid of duplicates
    Data.ExtractBC.add(temp);
    }
    return Data;
}

}