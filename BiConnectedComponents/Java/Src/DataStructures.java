//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: June 2, 2021
// Class:
// Description: data structures biconnectivity
//-----------------------------------------------------
import java.util.*;
import java.io.*;
public class DataStructures{
    private int nrV;
    private int nrE;

    public int discTime; 
    public int finTime; 
    public int time; 

    public String[] vertexColor;
    public int[] discoveryTime;
    public int[] finishingTime;
    public int [] low;
    public int[] parent;
    public ArrayList<ArrayList<Integer>> ExtractBC;
    public LinkedList<Edge> stack;

//Local var
    final static boolean DEBUG = false;

//------------------------------------- 
// Constructors 
//-------------------------------------
    public DataStructures(){
        this.discTime = 0; 
        this.finTime = 0;
        this.time = 0;
        ExtractBC = new ArrayList<ArrayList<Integer>>();
    }
    public DataStructures(int nrV){
        this.nrV = nrV;
        this.vertexColor = new String[nrV];
        this.discoveryTime = new int[nrV];
        this.finishingTime = new int[nrV];
        this.parent = new int[nrV];
        this.discTime = 0; 
        this.finTime = 0;
        this.time = 0;
        this.low = new int[nrV];
        ExtractBC = new ArrayList<ArrayList<Integer>>();
        stack = new LinkedList<Edge>();
    }
//-------------------------------------
//Serialization and Print
//-------------------------------------
    public String bcToString(){
        String output = "";
        output = output + "{";
        for(int i= 0; i<ExtractBC.size() ; i++){
            output = output + "{";
            for(int j = 0; j<ExtractBC.get(i).size();j++){
                output = output + (ExtractBC.get(i).get(j) + 1) + ", ";
            }
            output = output + "}, ";
        }
        output = output + "}\n";
        return output.replaceAll(", }", "}");
    }
    public void printBC(){
        System.out.print(bcToString());
    }
//-------------------------------------
// WriteToFile
//------------------------------------- 
    public void writeToFile(String filename){
    try {
        FileWriter myWriter = new FileWriter(filename); 
        myWriter.write(bcToString());
        myWriter.close();
    } 
    catch (IOException e) { 
        System.out.println("An error occurred."); 
        e.printStackTrace();
    } 
    }
}