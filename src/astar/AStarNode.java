package astar;

/**
 * The Node class for the AStar algorithm
 * <p>
 * Stores the location, gCost, default hCost, parent, and depth of a given location
 * @author Gareth Kmet
 *
 */
public class AStarNode {
	
	/**
	 * The location of this node within the map
	 */
	final AStarLocation location;
	
	/**
	 * The default heuristic of this node
	 */
	final float distanceToEnd;
	
	/**
	 * The state of this node within the algorithm
	 */
	State state = State.NONE;
	
	/**
	 * The current gCost of this node
	 * <p>
	 * Represents the distance traveled to reach this node so far
	 */
	float gCost = 0;
	
	
	/**
	 * The previous node in the path 
	 */
	AStarNode parent = null;
	
	/**
	 * The number of nodes in the path up to this node (inclusive)
	 */
	int depth = 0;
	
	/**
	 * Constructor for AStarNode
	 * @param loc - the map location of this node
	 * @param distanceToEnd - the default heuristic float for this node
	 */
	AStarNode(AStarLocation loc, float distanceToEnd){
		location = loc;
		this.distanceToEnd=distanceToEnd;
	}
	
	/**
	 * The location of this node within the map
	 * @return the location
	 */
	public AStarLocation location() {
		return this.location;
	}
	
	
	/**
	 * The number of nodes in the path up to this node (inclusive)
	 * @return the depth of this node
	 */
	public int d() {
		return this.depth;
	}
	
	/**
	 * The current gCost of this node
	 * <p>
	 * Represents the distance traveled to reach this node so far
	 * @return the gCost of this node
	 */
	public float g() {
		return this.gCost;
	}
	
	
	/**
	 * The default heuristic function of this node
	 * <p>
	 * Equivalent to the distance between this node and the target
	 */
	public float h() {
		return this.distanceToEnd;
	}
	
	/**
	 * Sets the parent and the depth of this node
	 * @param parent
	 */
	void setParent(AStarNode parent) {
		this.parent=parent;
		this.depth=parent.depth;
	}
	
	/**
	 * The state of the node within the current algorithm
	 * @author Gareth Kmet
	 *
	 */
	enum State{
		/**
		 * Contained by the closed set
		 */
		CLOSED,
		/**
		 * Contained by the open set
		 */
		OPEN,
		/**
		 * Contained by neither the open nor the closed set
		 */
		NONE
	}


}
