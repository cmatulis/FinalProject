//// BUGS/IMPROVEMENTS NEEDED:
// JAVADOC/COMMENT
// CHANGE TO EXTEND ADJMATGRAPH (NOT ADJMATGRAPHPLUS BECAUSE ADDITIONAL METHODS
// IN PLUS CLASS ARE NOT USED HERE)

import javafoundations.*;
import java.util.*;
import java.io.*;
//import java.util.Stack;

public class TPGraph<T> extends AdjMatGraphPlus<T>{
  
  public TPGraph (String tgf_file_name){
    super(tgf_file_name); 
  }
  
   /**
   * Determines the vertices located a given distance from the vertex of interest
   * 
   * @param vertex the vertex whose neighbors will be found
   * @param distance the distance from the vertex of interest where the neighbors will be located
   * @return a linked list of the vertices located the specified distance from the vertex of interest
   */
  public LinkedList<T> getNeighbors(T vertex, int distance){
    LinkedList<T> listOne = new LinkedList<T>();
    LinkedList<T> listTwo = new LinkedList<T>();
    
    listOne = getSuccessors(vertex);
    if (distance == 1){
      return listOne;
    }
    int i = 0;
    while (i < distance-1){
      if (i != 0){
        listOne.addAll(listTwo);
      }
      listTwo.clear();
      for(T object : listOne){
        for (T item : getSuccessors(object)){
          if (!listOne.contains(item) && !listTwo.contains(item)){
            listTwo.add(item);
          }
        }
      }
      i++;
    }
    listTwo.remove(vertex);
    return listTwo;
  }
  
  public static void main(String[] args){
    TPGraph<String> gamegraph = new TPGraph<String>("tpgraphtgf.tgf");
    System.out.println(gamegraph);
    System.out.println(gamegraph.getNeighbors("32", 3));
    System.out.println(gamegraph.getNeighbors("30", 3));
    System.out.println(gamegraph.getNeighbors("00", 1));
    
    
  }
  
  
  
  
  
}