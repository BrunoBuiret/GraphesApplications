package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                this.edges.get(i).remove(nodeIndex);
            
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
     * @return <code>true</code> if the nodes exists, <code>false</code> otherwise.
     */
    public boolean nodeExists(int nodeIndex)
    {
        return this.nodes.contains(nodeIndex);
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
            return this.nodeNames.get(nodeIndex);
        else
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
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
            this.nodeNames.put(nodeIndex, nodeName);
        else
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
    }
    
    /**
     * Adds an edge between two nodes of the graph.
     * 
     * @param nodeIndex1 Node 1's index.
     * @param nodeIndex2 Node 2's index.
     * @throws java.lang.Exception Thrown if one of the nodes doesn't exist or if the
     * edge already exists.
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
                }
                else
                {
                    throw new Exception(
                        "Edge already exists between nodes #" +
                        nodeIndex1 + " and #" + nodeIndex2 + "."
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
     * @throws java.lang.Exception Thrown if one of the nodes doesn't exist or if the
     * edge doesn't exist.
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
                        "Edge doesn't exist between nodes #" +
                        nodeIndex1 + " and #" + nodeIndex2 + "."
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
     * @return <code>true</code> if the edge exists, <code>false</code> otherwise.
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
     * Gets a node's degree.
     * 
     * @param nodeIndex Node's index.
     * @return Node's degree
     * @throws java.lang.Exception Thrown if the node doesn't exist.
     */
    public int getNodeDegree(int nodeIndex) throws Exception
    {
        if(this.nodeExists(nodeIndex))
            return this.edges.get(nodeIndex).size();
        else
            throw new Exception("Node #" + nodeIndex + " doesn't exist.");
    }
    
    /**
     * Performs a breadth first search on the graph.
     * 
     * @param startNodeIndex Start node's index.
     */
    public void breadthFirstSearch(int startNodeIndex)
    {
        throw new UnsupportedOperationException("Method graphs.Graph.breadthFirstSearch(int) isn't implemented yet.");
    }
    
    /**
     * Performs a depth first search on the graph.
     * 
     * @param startNodeIndex Start node's index.
     */
    public void depthFirstSearch(int startNodeIndex)
    {
        throw new UnsupportedOperationException("Method graphs.Graph.depthFirstSearch(int) isn't implemented yet.");
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
     * Gets a string representation of the graph that can be used
     * with GraphViz.
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
            {
                if(nodeIndex1 <= nodeIndex2)
                    edgesBuilder.append(nodeIndex2).append("; ");
            }
            
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
        throw new UnsupportedOperationException("Method graphs.Graph.load(String) isn't implemented yet.");
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
            Graph g = new Graph("Test de graphe");
            
            g.addNode(0, "Noeud 0");
            g.addNode(1);
            g.addNode(2, "Noeud 2");
            
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 2);
            
            System.out.println(g.getRepresentation());
        }
        catch(Exception e)
        {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
