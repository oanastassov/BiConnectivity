//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: June 2, 2021
// Description: Class for: edge 
//-----------------------------------------------------
import java.util.*;
public class Edge{
    private int u;
    private int v;
//------------------------------------- 
// Constructors 
//-------------------------------------
    public Edge(int u, int v){
        this.u = u; this.v = v;
    }
//------------------------------------- 
// Getters 
//-------------------------------------
    public int getVertex1(){
        return u;
    }
    public int getVertex2(){
        return v;
    }
//------------------------------------- 
// Testers
//-------------------------------------
    public boolean equals(Edge edge){
        if(this.u == edge.u && this.v == edge.v){
            return true;
        }
        return false;
    }
}