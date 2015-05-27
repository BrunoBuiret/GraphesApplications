package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Thomas Arnaud, Bruno Buiret
 */
public class Graph
{
    /**
     * Graph's name.
     */
    protected String graphName;

    /**
     * Graph's nodes list.
     */
    protected Set<Integer> nodes;

    /**
     * Graph's edges list.
     */
    protected Map<Integer, Set<Integer>> edges;
    
    /**
     * Graph's edges number.
     */
    protected long edgesNumber;

    /**
     * Nodes' name list.
     */
    protected Map<Integer, String> nodeNames;

    /**
     * Creates a new graph without a name.
     *
     * @see graphs.Graph(String)
     */
    public Graph()
    {
        this(null);
    }

    /**
     * Creates a new graph with a name.
     *
     * @param graphName Graph's name.
     */
    public Graph(String graphName)
    {
        this.graphName = graphName;
        this.nodes = new HashSet<Integer>();
        this.edges = new HashMap<Integer, Set<Integer>>();
        this.edgesNumber = 0;
        this.nodeNames = new HashMap<Integer, String>();
    }

    /**
     * Gets the graph's name.
     *
     * @return Graph's name.
     */
    public String getGraphName()
    {
        return this.graphName;
    }

    /**
     * Sets the graph's name.
     *
     * @param graphName Graph's name.
     */
    public void setGraphName(String graphName)
    {
        this.graphName = graphName;
    }

    /**
     * Adds a node without a name to the graph.
     *
     * @param nodeIndex Node's index.
     * @throws java.lang.Exception Thrown if the node's index already exists.
     * @see graphs.Graph.addNode(int, String)
     */
    public void addNode(int nodeIndex) throws Exception
    {
        this.addNode(nodeIndex, null);
    }

    /**
     * Adds a node with a name to the graph.
     *
     * @param nodeIndex Node's index.
     * @param nodeName Node's name.
     * @throws java.lang.Exception Thrown if the node's index already exists.
     */
    public void addNode(int nodeIndex, String nodeName) throws Exception
    {
        if(this.nodes.add(nodeIndex))
        {
            // Memorize the node's name
            this.nodeNames.put(nodeIndex, nodeName);
            // Create the list of edges associated to the node
            this.edges.put(nodeIndex, new HashSet<Integer>());
        }
        else
        {
            throw new Exception("Node #" + nodeIndex + " already exists.");
        }
    }

    /**
     * Removes a node from the graph.
     *
     * @param nodeIndex Node's index.
     * @throws java.lang.Exception Thrown if the node's index doesn't exist.
     */
    public void removeNode(int nodeIndex) throws Exception
    {
        if(this.nodes.remove(nodeIndex))
        {
            // Erase the node's name
            this.nodeNames.remove(nodeIndex);

            // Erase the node's associated edges
            for(int i : this.edges.get(nodeIndex))
            {
                this.edges.get(i).remove(nodeIndex);
            }

            this.edges.remove(nodeIndex);
        }
        else
        {
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
        }
    }

    /**
     * Tests if the a nodes exists inside the graph.
     *
     * @param nodeIndex Node's index.
     * @return <code>true</code> if the nodes exists, <code>false</code>
     * otherwise.
     */
    public boolean nodeExists(int nodeIndex)
    {
        return this.nodes.contains(nodeIndex);
    }
    
    /**
     * 
     * @return 
     */
    public int getNodesNumber()
    {
        return this.nodes.size();
    }

    /**
     * Gets a node's name.
     *
     * @param nodeIndex Node's index.
     * @return Node's name.
     * @throws java.lang.Exception Thrown if the node's index doesn't exist.
     */
    public String getNodeName(int nodeIndex) throws Exception
    {
        if(this.nodeExists(nodeIndex))
        {
            return this.nodeNames.get(nodeIndex);
        }
        else
        {
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
        }
    }

    /**
     * Sets a node's name.
     *
     * @param nodeIndex Node's index.
     * @param nodeName Node's name.
     * @throws java.lang.Exception Thrown if the node's index doesn't exist.
     */
    public void setNodeName(int nodeIndex, String nodeName) throws Exception
    {
        if(this.nodeExists(nodeIndex))
        {
            this.nodeNames.put(nodeIndex, nodeName);
        }
        else
        {
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
        }
    }
    
    /**
     * 
     * @return 
     */
    public Map<Integer, String> getNodeNames()
    {
        return this.nodeNames;
    }

    /**
     * Adds an edge between two nodes of the graph.
     *
     * @param nodeIndex1 Node 1's index.
     * @param nodeIndex2 Node 2's index.
     * @throws java.lang.Exception Thrown if one of the nodes doesn't exist or
     * if the edge already exists.
     */
    public void addEdge(int nodeIndex1, int nodeIndex2) throws Exception
    {
        if(this.nodeExists(nodeIndex1))
        {
            if(this.nodeExists(nodeIndex2))
            {
                if(!this.edgeExists(nodeIndex1, nodeIndex2))
                {
                    // From node one to node two
                    this.edges.get(nodeIndex1).add(nodeIndex2);
                    // From node two to node one
                    this.edges.get(nodeIndex2).add(nodeIndex1);
                    // Increase edges number
                    this.edgesNumber++;
                }
                else
                {
                    throw new Exception(
                            "Edge already exists between nodes #"
                            + nodeIndex1 + " and #" + nodeIndex2 + "."
                    );
                }
            }
            else
            {
                throw new Exception("Node #" + nodeIndex1 + " doesn't exist.");
            }
        }
        else
        {
            throw new Exception("Node #" + nodeIndex1 + " doesn't exist.");
        }
    }

    /**
     * Removes an edge between two nodes of the graph.
     *
     * @param nodeIndex1 Node 1's index.
     * @param nodeIndex2 Node 2's index.
     * @throws java.lang.Exception Thrown if one of the nodes doesn't exist or
     * if the edge doesn't exist.
     */
    public void removeEdge(int nodeIndex1, int nodeIndex2) throws Exception
    {
        if(this.nodeExists(nodeIndex1))
        {
            if(this.nodeExists(nodeIndex2))
            {
                if(this.edgeExists(nodeIndex1, nodeIndex2))
                {
                    // Remove from the first node
                    this.edges.get(nodeIndex1).remove(nodeIndex2);

                    // Remove from the second node
                    this.edges.get(nodeIndex2).remove(nodeIndex1);
                }
                else
                {
                    throw new Exception(
                            "Edge doesn't exist between nodes #"
                            + nodeIndex1 + " and #" + nodeIndex2 + "."
                    );
                }
            }
            else
            {
                throw new Exception("Node #" + nodeIndex1 + " doesn't exist.");
            }
        }
        else
        {
            throw new Exception("Node #" + nodeIndex1 + " doesn't exist.");
        }
    }

    /**
     * Tests if an edge exists between two nodes of the graph.
     *
     * @param nodeIndex1 Node 1's index.
     * @param nodeIndex2 Node 2's index.
     * @return <code>true</code> if the edge exists, <code>false</code>
     * otherwise.
     * @throws java.lang.Exception Thrown if one of the nodes doesn't exist.
     */
    public boolean edgeExists(int nodeIndex1, int nodeIndex2) throws Exception
    {
        if(this.nodeExists(nodeIndex1))
        {
            if(this.nodeExists(nodeIndex2))
            {
                return this.edges.get(nodeIndex1).contains(nodeIndex2);
            }
            else
            {
                throw new Exception("Node #" + nodeIndex1 + " doesn't exist.");
            }
        }
        else
        {
            throw new Exception("Node #" + nodeIndex1 + " doesn't exist.");
        }
    }

    /**
     * 
     * @return 
     */
    public long getEdgesNumber()
    {
        return this.edgesNumber;
    }
    
    /**
     * Gets a node's degree.
     *
     * @param nodeIndex Node's index.
     * @return Node's degree
     * @throws java.lang.Exception Thrown if the node doesn't exist.
     */
    public int getNodeDegree(int nodeIndex) throws Exception
    {
        if(this.nodeExists(nodeIndex))
        {
            return this.edges.get(nodeIndex).size();
        }
        else
        {
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
        }
    }

    /**
     * Performs a breadth first search on the graph.
     *
     * @param startNodeIndex Start node's index.
     * @return
     * @throws java.lang.Exception Thrown if the start node doesn't exist.
     */
    public Map<Integer, Integer> breadthFirstSearch(int startNodeIndex) throws Exception
    {
        if(this.nodes.contains(startNodeIndex))
        {
            Queue<Integer> queue = new LinkedList<Integer>();
            Map<Integer, Boolean> markedNodes = new HashMap<Integer, Boolean>();
            Map<Integer, Integer> processOrder = new HashMap<Integer, Integer>();
            int processNumber = 1;

            // Initialization
            for(int nodeIndex : this.nodes)
            {
                markedNodes.put(nodeIndex, false);
                processOrder.put(nodeIndex, 0);
            }

            markedNodes.put(startNodeIndex, true);
            queue.add(startNodeIndex);

            // Go through the graph
            while(!queue.isEmpty())
            {
                int nodeIndex1 = queue.remove();

                for(int nodeIndex2 : this.edges.get(nodeIndex1))
                {
                    if(!markedNodes.get(nodeIndex2))
                    {
                        markedNodes.put(nodeIndex2, true);
                        queue.add(nodeIndex2);
                    }
                }

                processOrder.put(nodeIndex1, processNumber);
                processNumber++;
            }

            return processOrder;
        }
        else
        {
            throw new Exception("Node #" + startNodeIndex + " doesn't exist.");
        }
    }

    /**
     * Performs a depth first search on the graph.
     *
     * @param startNodeIndex Start node's index.
     * @return 
     * @throws java.lang.Exception Thrown if the start node doesn't exist.
     */
    public Map<Integer, Integer> depthFirstSearch(int startNodeIndex) throws Exception
    {
        if(this.nodes.contains(startNodeIndex))
        {
            Stack<Integer> stack = new Stack<Integer>();
            Map<Integer, Boolean> markedNodes = new HashMap<Integer, Boolean>();
            Map<Integer, Integer> processOrder = new HashMap<Integer, Integer>();
            int processNumber = 1;

            // Initialization
            for(int nodeIndex : this.nodes)
            {
                markedNodes.put(nodeIndex, false);
                processOrder.put(nodeIndex, 0);
            }

            markedNodes.put(startNodeIndex, true);
            stack.push(startNodeIndex);

            // Go through the graph
            while(!stack.isEmpty())
            {
                int nodeIndex1 = stack.pop();

                for(int nodeIndex2 : this.edges.get(nodeIndex1))
                {
                    if(!markedNodes.get(nodeIndex2))
                    {
                        markedNodes.put(nodeIndex2, true);
                        stack.push(nodeIndex2);
                    }
                }

                processOrder.put(nodeIndex1, processNumber);
                processNumber++;
            }

            return processOrder;
        }
        else
        {
            throw new Exception("Node #" + startNodeIndex + " doesn't exist.");
        }
    }

    /**
     * Performs the Dijkstra algorithm on the graph.
     *
     * @param startNodeIndex Start node's index.
     */
    public void dijkstra(int startNodeIndex)
    {
        throw new UnsupportedOperationException("Method graphs.Graph.depthFirstSearch(int) isn't implemented yet.");
    }

    /**
     * Gets a string representation of the graph that can be used with GraphViz.
     *
     * @return Graph's representation.
     */
    public String getRepresentation()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("graph {\n");

        // Print the graph's name
        if(this.graphName != null)
            builder.append("\tlabel=\"").append(this.graphName).append("\";\n\n");

        // Print the list of nodes
        for(int nodeIndex : this.nodes)
        {
            builder.append("\t").append(nodeIndex);

            if(this.nodeNames.get(nodeIndex) != null)
                builder.append(" [label=\"").append(this.nodeNames.get(nodeIndex)).append("\"]");

            builder.append(";\n");
        }

        builder.append("\n");

        // Print the list of edges
        for(int nodeIndex1 : this.edges.keySet())
        {
            StringBuilder edgesBuilder = new StringBuilder();

            for(int nodeIndex2 : this.edges.get(nodeIndex1))
                if(nodeIndex1 <= nodeIndex2)
                    edgesBuilder.append(nodeIndex2).append("; ");

            if(edgesBuilder.length() > 0)
            {
                builder.append("\t").append(nodeIndex1).append(" -- {");
                edgesBuilder.delete(edgesBuilder.length() - 2, edgesBuilder.length());
                builder.append(edgesBuilder).append("};\n");
            }
        }

        builder.append("}");

        return builder.toString();
    }

    /**
     *
     * @param fileName
     */
    public void save(String fileName)
    {
        throw new UnsupportedOperationException("Method graphs.Graph.save(String) isn't implemented yet.");
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static Graph load(String fileName)
    {
        try 
        {
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            Graph graph = new Graph();
            String line;
            int nodeIndex = 1;
            
            while((line = buffer.readLine()) != null)
            {
                graph.addNode(nodeIndex, line);
                
                for(Map.Entry<Integer, String> entry : graph.getNodeNames().entrySet())
                {
                    if(nodeIndex != entry.getKey())
                    {
                        if(Graph.levenshtein(line, entry.getValue()) == 1)
                        {
                            graph.addEdge(nodeIndex, entry.getKey());       
                        }
                    }
                }
                nodeIndex ++;
            }
            
            return graph;
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) 
        {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * 
     * @param s0
     * @param s1
     * @return 
     * @see http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
     */
    public static int levenshtein(String s0, String s1) 
    {                          
        int len0 = s0.length() + 1;                                                     
        int len1 = s1.length() + 1;                                                     

        // the array of distances                                                       
        int[] cost = new int[len0];                                                     
        int[] newcost = new int[len0];                                                  

        // initial cost of skipping prefix in String s0                                 
        for (int i = 0; i < len0; i++) cost[i] = i;                                     

        // dynamically computing the array of distances                                  

        // transformation cost for each letter in s1                                    
        for (int j = 1; j < len1; j++) {                                                
            // initial cost of skipping prefix in String s1                             
            newcost[0] = j;                                                             

            // transformation cost for each letter in s0                                
            for(int i = 1; i < len0; i++) {                                             
                // matching current letters in both strings                             
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;             

                // computing cost for each transformation                               
                int cost_replace = cost[i - 1] + match;                                 
                int cost_insert  = cost[i] + 1;                                         
                int cost_delete  = newcost[i - 1] + 1;                                  

                // keep minimum cost                                                    
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }                                                                           

            // swap cost/newcost arrays                                                 
            int[] swap = cost; cost = newcost; newcost = swap;                          
        }                                                                               

        // the distance is the cost for transforming all letters in both strings        
        return cost[len0 - 1];                                                          
    }

    /**
     * Main entry point of the program.
     *
     * @param args Command line's arguments.
     */
    public static void main(String[] args)
    {
        try
        {
            Graph g = Graph.load("motsdelongueur6.txt");
            System.out.println("Le nombre de sommets est de : " + g.getNodesNumber());
            // Le nombre de sommets est de : 17035
            System.out.println("Le nombre d'arêtes est de : " + g.getEdgesNumber());
            // Le nombre d'arêtes est de : 39720
        }
        catch(Exception e)
        {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
