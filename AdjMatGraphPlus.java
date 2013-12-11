/********************************************************************
  * AdjMatGraphPlus.java  Sample Solutions of Exam3 
  * Implementation of the GraphPlus.java interface using adjacency matrix
  * of booleans. 
  * KNOWN FEATURES/BUGS: 
  * It handles unweighted graphs only, but it can be extended.
  * It does not handle operations involving non-existing vertices
  ********************************************************************/
import javafoundations.*;
import java.util.*;
import java.io.*;
import java.util.Stack;

public class AdjMatGraphPlus<T> implements GraphPlus<T>
{
  private final int NOT_FOUND = -1;
  private final int DEFAULT_CAPACITY = 1; // Small so that we can test expand
  
  private int n;   // number of vertices in the graph
  private boolean[][] arcs;   // adjacency matrix of arcs
  private T[] vertices;   // values of vertices
  
  /******************************************************************
    Constructor. Creates an empty graph.
    ******************************************************************/
  public AdjMatGraphPlus()
  {
    n = 0;
    this.arcs = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
  }
  
  
  /******************************************************************
    * Second constructor:
    * Creates a new graph using the data found in a .tgf file.
    If the file does not exist, a message is printed. 
    *****************************************************************/
  public AdjMatGraphPlus(String tgf_file_name) {
    //reset current graph
    vertices = (T[]) (new Object[DEFAULT_CAPACITY]); 
    arcs = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    n = 0;
    
    try{
      
      Scanner fileReader = new Scanner(new File(tgf_file_name));
      while (!fileReader.next().equals("#")){
        T line = (T) fileReader.next();
        addVertex(line);
      }
      
      while (fileReader.hasNext()){
        int arcVertex1 = fileReader.nextInt();
        int arcVertex2 = fileReader.nextInt();
        //System.out.println ("Arc Vertex 1: " + arcVertex1);
        //System.out.println ("Arc Vertex 2: " + arcVertex2);
        addArc(vertices[arcVertex1 -1], vertices[arcVertex2 -1]);
      }
      
    } catch (IOException ex) {
      System.out.println(" ***(T)ERROR*** The file was not found: " + ex);
    }
  }
  
  /******************************************************************
    Returns true if the graph is empty and false otherwise. 
    ******************************************************************/
  public boolean isEmpty()
  {
    return (n == 0);
  }
  
  /******************************************************************
    Returns the number of vertices in the graph.
    ******************************************************************/
  public int n()
  { return n; }
  
  /******************************************************************
    Returns the number of arcs in the graph by counting them.
    ******************************************************************/
  public int m()
  {
    int total = 0;
    
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
      if (arcs[i][j]) total++; 
    return total; 
  }
  
  /******************************************************************
    Returns true iff a directed edge exists from v1 to v2.
    ******************************************************************/
  public boolean isArc (T vertex1, T vertex2)
  { return arcs[getIndex(vertex1)][getIndex(vertex2)]; }
  
  
  /******************************************************************
    Helper. Returns true iff an arc exists between two given indices 
    ******************************************************************/
  private boolean isArc (int index1, int index2)
  {
    if (indexIsValid(index1) && indexIsValid(index2))
      return arcs[index1][index2] == true;
    else return false;
  }
  
  
  /******************************************************************
    Returns true iff an edge exists between two given vertices
    which means that two corresponding arcs exist in the graph
    ******************************************************************/
  public boolean isEdge (T vertex1, T vertex2){
    return (isArc(vertex1, vertex2) && isArc(vertex2, vertex1)); }
  
  
  /******************************************************************
    Returns true IFF the graph is undirected, that is, for every 
    pair of nodes i,j for which there is an arc, the opposite arc
    is also present in the graph.  
    ******************************************************************/
  public boolean isUndirected(){
    for (int i = 0; i < n(); i++)
      for (int j = 0; j < n(); j++)
      if (arcs[i][j])
      if (!arcs[j][i]) 
      return false;
    return true;
  };
  
  
  /******************************************************************
    Adds a vertex to the graph, expanding the capacity of the graph
    if necessary.  If the vertex already exists, it does not add it.
    ******************************************************************/
  public void addVertex (T vertex)
  {  if (getIndex(vertex) == NOT_FOUND) {
    if (n == vertices.length)
      expandCapacity();
    
    vertices[n] = vertex;
    for (int i = 0; i <= n; i++)
    {
      arcs[n][i] = false;
      arcs[i][n] = false;
    }      
    n++;
  }
  }
  
  /******************************************************************
    Helper. Creates new arrays to store the contents of the graph 
    withtwice the capacity.
    ******************************************************************/
  private void expandCapacity()
  {
    T[] largerVertices = (T[])(new Object[vertices.length*2]);
    boolean[][] largerAdjMatrix = 
      new boolean[vertices.length*2][vertices.length*2];
    
    for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
      {
        largerAdjMatrix[i][j] = arcs[i][j];
      }
      largerVertices[i] = vertices[i];
    }
    
    vertices = largerVertices;
    arcs = largerAdjMatrix;
  }
  
  
  /******************************************************************
    Removes a single vertex with the given value from the graph.  
    Uses equals() for testing equality
    ******************************************************************/
  public void removeVertex (T vertex)
  {
    for (int i = 0; i < n; i++)
      if (vertex.equals(vertices[i]))
      removeVertex(i);
  }
  
  /******************************************************************
    Helper. Removes a vertex at the given index from the graph.   
    Note that this may affect the index values of other vertices.
    ******************************************************************/
  private void removeVertex (int index)
  {
    if (indexIsValid(index))
    {
      n--;
      
      for (int i = index; i < n; i++)
        vertices[i] = vertices[i+1];
      
      for (int i = index; i < n; i++)
        for (int j = 0; j <= n; j++)
        arcs[i][j] = arcs[i+1][j];
      
      for (int i = index; i < n; i++)
        for (int j = 0; j < n; j++)
        arcs[j][i] = arcs[j][i+1];
    }
  }
  
  /******************************************************************
    Inserts an edge between two vertices of the graph.
    If one or both vertices do not exist, ignores the addition.
    ******************************************************************/
  public void addEdge (T vertex1, T vertex2)
  {
    // getIndex will return NOT_FOUND if a vertex does not exist,
    // and the addArc calls will not insert it
    addArc (getIndex(vertex1), getIndex(vertex2));
    addArc (getIndex(vertex2), getIndex(vertex1));
  }
  
  /******************************************************************
    Inserts an arc from vertex1 to vertex2.
    If the vertices exist, else does not change the graph. 
    ******************************************************************/
  public void addArc (T vertex1, T vertex2){
    addArc (getIndex(vertex1), getIndex(vertex2));
  }
  
  /******************************************************************
    Helper. Inserts an edge between two vertices of the graph.
    ******************************************************************/
  private void addArc (int index1, int index2)
  {
    if (indexIsValid(index1) && indexIsValid(index2))
      arcs[index1][index2] = true;
  }
  
  
  /******************************************************************
    Removes an edge between two vertices of the graph.
    If one or both vertices do not exist, ignores the removal.
    ******************************************************************/
  public void removeEdge (T vertex1, T vertex2)
  {
    removeArc (getIndex(vertex1), getIndex(vertex2));
    removeArc (getIndex(vertex2), getIndex(vertex1));
  }
  
  
  /******************************************************************
    Removes an arc from vertex v1 to vertex v2,
    if the vertices exist, else does not change the graph. 
    ******************************************************************/
  public void removeArc (T vertex1, T vertex2){
    removeArc (getIndex(vertex1), getIndex(vertex2)); }
  
  /******************************************************************
    Helper. Removes an arc from index v1 to index v2.
    ******************************************************************/
  private void removeArc (int index1, int index2)
  {
    if (indexIsValid(index1) && indexIsValid(index2))
      arcs[index1][index2] = false;
  }
  
  
  
  /******************************************************************
    Returns the index value of the first occurrence of the vertex.
    Returns NOT_FOUND if the key is not found.
    ******************************************************************/
  private int getIndex(T vertex)
  {
    for (int i = 0; i < n; i++)
      if (vertices[i].equals(vertex))
      return i;
    return NOT_FOUND;
  }
  
  /******************************************************************
    Returns the vertex object that is at a certain index
    ******************************************************************/
  private T getVertex(int v)
  {   return vertices[v]; 
  }
  
  /******************************************************************
    Returns true if the given index is valid. 
    ******************************************************************/
  private boolean indexIsValid(int index)
  {  return ((index < n) && (index >= 0));  
  }
  
  /******************************************************************
    Retrieve from a graph the vertices x pointing to vertex v (x->v)
    and returns them onto a linked list
    ******************************************************************/
  public LinkedList<T> getPredecessors(T vertex){
    LinkedList<T> neighbors = new LinkedList<T>();
    
    int v = getIndex(vertex); 
    
    for (int i = 0; i < n; i++)
    {
      if (arcs[i][v])  
        neighbors.add(getVertex(i)); // if T then add i to linked list
    }    
    return neighbors;    
  }
  
  /******************************************************************
    * Retrieve from a graph the vertices x following vertex v (v->x)
    and returns them onto a linked list
    ******************************************************************/
  public LinkedList<T> getSuccessors(T vertex){
    LinkedList<T> neighbors = new LinkedList<T>();
    
    int v = getIndex(vertex); 
    
    for (int i = 0; i < n; i++)
    {
      if (arcs[v][i])  
        neighbors.add(getVertex(i)); // if T then add i to linked list
    }    
    return neighbors;    
  }
  
  /******************************************************************
    Returns a string representation of the graph. 
    ******************************************************************/
  public String toString()
  {
    if (n == 0)
      return "Graph is empty";
    
    String result = new String("");
    
    result += "Arcs\n";
    result += "-----\n";
    result += "i ";
    
    for (int i = 0; i < n; i++) 
    {
      result += "" + getVertex(i);
      if (i < 10)
        result += " ";
    }
    result += "\n";
    
    for (int i = 0; i < n; i++)
    {
      result += "" + getVertex(i) + " ";
      
      for (int j = 0; j < n; j++)
      {
        if (arcs[i][j])
          result += "1 ";
        else
          result += "- "; //just empty space
      }
      result += "\n";
    }
    
    return result;
  }
  
  
  /******************************************************************
    * Saves the current graph into a .tgf file.
    * If it cannot save the file, a message is printed. 
    *****************************************************************/
  public void saveTGF(String tgf_file_name) {
    try {
      PrintWriter writer = new PrintWriter(new File(tgf_file_name));
      
      //prints vertices by iterating through array "vertices"
      for (int i = 0; i < n(); i++){
        if (vertices[i] == null){
          break;
        }
        else{
          writer.print((i+1) + " " + vertices[i]);
          writer.println("");
        }
      }
      writer.print("#"); // Prepare to print the edges
      writer.println("");
      
      //prints arcs by iterating through 2D array
      for (int i = 0; i < n(); i++){
        for (int j = 0; j < n(); j++){
          if (arcs[i][j] == true){
            writer.print((i+1) + " " + (j+1));
            writer.println("");
          }
        }
      }
      writer.close();
    } catch (IOException ex) {
      System.out.println("***(T)ERROR*** The file could nt be written: " + ex);
    }
  }
  
  /******************************************************************
    * Clones the graph by saving the current one on the disk 
    * as TEMP.tgf using saveTGF() and creating a new one using the 
    * second constructor.
    * @return the new graph.
    * FEATURE: It does not try to delete the file from the disk
    *****************************************************************/
  public GraphPlus<T> clone () 
  {
    saveTGF("TEMP.tgf");
    AdjMatGraphPlus<T> clone = new AdjMatGraphPlus<T>("TEMP.tgf");
    return clone;
    
    
  }
  
  
  
  
  /******************************************************************
    * Checks if a vertex is a sink, (points to no other vertex)
    * @return true if the vertex is a sink, false if it is not.
    ******************************************************************/
  public boolean isSink (T vertex)
  {
    boolean s = true;
    
    int numSuccessors = getSuccessors(vertex).size();
    if (numSuccessors != 0){
      s = false;
    }
    
    return s;
  }
  
  /******************************************************************
    * Retrieves the vertices that are sinks and 
    * @return all the sinks in a linked list
    ******************************************************************/
  public LinkedList<T> allSinks()
  {
    LinkedList<T> sinks = new LinkedList<T>();
    for (int i = 0; i < n; i++){
      if (this.isSink(getVertex(i))){
        sinks.add(getVertex(i));
      }
    }
    
    return sinks;  
  }
  
  
  /******************************************************************
    * Checks if a vertex is a source, (no vertex points to it)
    * @return true if the vertex is a source, false if it is not
    ******************************************************************/
  public boolean isSource (T vertex)
  {
    boolean s = true;
    
    int numPredecessors = getPredecessors(vertex).size();
    if (numPredecessors != 0){
      s = false;
    }
    
    return s;
  }
  
  /******************************************************************
    * Retrieves the vertices that are sources and 
    * @return all the sources in a linked list
    ******************************************************************/
  public LinkedList<T> allSources()
  {
    LinkedList<T> sources = new LinkedList<T>();
    
    for (int i = 0; i < n; i++){
      if (isSource(getVertex(i))){
        sources.add(getVertex(i));
      }
    }
    
    return sources; 
  }
  
  /******************************************************************
    * Checks if a vertex is a isolated, b/c it's source and sink
    * @return true if the vertex is isolated, false if it is not
    ******************************************************************/
  public boolean isIsolated (T vertex)
  {
    
    return (isSource(vertex) && isSink(vertex));
    
  }
  
  
  /******************************************************************
    * Topologically sort vertices of a directed acyclic graph
    * using one of the two algorithms presented in class. 
    * PREREQUISITE: The input graph must be a DAG, i.e., have NO CYCLES.
    * KNOWN BUG: It will get into an infinite loop if the graph has a cycle
    * @return the topologically sorted vertices in a linked list
    ******************************************************************/
  public LinkedList<T> topologicalSort()
  {
    LinkedList<T> sorted = new LinkedList<T>();
    
    // create a clone of the current graph so you don't permanantly lose any vertices from the real graph
    GraphPlus<T> clonedGraph = clone();
    
    while(!clonedGraph.isEmpty()){
      // if the cloned graph is not empty but contains no sources and no sinks, it is cyclic.  Return an empty
      // linked list.
      if (clonedGraph.allSources().size() == 0 && clonedGraph.allSinks().size() == 0){
        System.out.println("Graph is cyclic.");
        return new LinkedList<T>();
      }
      
      // find each vertex that has no successor and add it to the sorted linked list
        T nextElement = clonedGraph.allSources().pop();
        sorted.add(nextElement);
        // remove each sorted vertex from the cloned graph and add to the end of a list of vertices
        clonedGraph.removeVertex(nextElement);
        
        // repeat until each element has been sorted
    }
    return sorted;
    
  }
  
  /******************************************************************
    * Returns a LinkedList contining a DEPTH first search traversal 
    * starting at the given index. If the index is not valid, 
    * it returns an empty list
    * @return a linked list with the vertices in depth-first order
    *****************************************************************/
  public LinkedList<T> DFS(T vertex){
    int currentVertex;
    LinkedList<T> resultList = new LinkedList<T>();
    Stack<Integer> traversalStack = new Stack<Integer>();
    boolean[] visited = new boolean[n];
    boolean found;
    
    if (getIndex(vertex) == -1){
      return resultList;
    }  
    
    for (int m = 0; m < n; m++){
      visited[m] = false;
    }
    
    traversalStack.push(getIndex(vertex));
    resultList.add(vertices[getIndex(vertex)]);
    visited[getIndex(vertex)] = true;
    
    while(!traversalStack.isEmpty()){
      currentVertex = traversalStack.peek();
      found = false;
      for (int m = 0; m < n && !found; m++)
        if (arcs[currentVertex][m] && !visited[m]){
        traversalStack.push(m);
        resultList.add(vertices[m]);
        visited[m] = true;
        found = true;
      }
      
      if (!found && !traversalStack.isEmpty()){
        traversalStack.pop();
      }
    }
    
    return resultList;
  }
  
  /******************************************************************
    * Returns a LinkedList contining a BREADTH first search traversal 
    * starting at the given index. If the index is not valid, 
    * it returns an empty list
    * @return a linked list with the vertices in breadth-first order
    *****************************************************************/
  public LinkedList<T> BFS(T vertex)
  {
    LinkedList<T> resultList = new LinkedList<T>();
    
    int currentVertex;
    LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
    
    if (getIndex(vertex) == -1){
      return resultList;
    } 
    
    boolean[] visited = new boolean[n];
    for (int m = 0; m < n; m++){
      visited[m] = false;
    }
    
    traversalQueue.enqueue(getIndex(vertex));
    visited[getIndex(vertex)] = true;
    
    while (!traversalQueue.isEmpty()){
      currentVertex = traversalQueue.dequeue();
      resultList.add(vertices[currentVertex]);
      
      for (int m = 0; m < n; m++){
        if (arcs[currentVertex][m] && !visited[m]){
          traversalQueue.enqueue(m);
          visited[m] = true;
        }
      }
    }
    
    
    
    return resultList;
  }
  
  
  /******************************************************************
    Testing program. 
    ******************************************************************/
  
  public static void main (String args[]){

   
  }
}