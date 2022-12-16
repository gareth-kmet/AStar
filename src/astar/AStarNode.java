package astar;

public class AStarNode {
	
	private static final float SQRT2 = (float)Math.sqrt(2);
	
	/**
	 * The location of this node within the map
	 */
	public final MapLocation location;
	
	/**
	 * The distance from this node to the target
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
	 * @param end - the target node: <code>null</code> if this is the target
	 */
	AStarNode(MapLocation loc, AStarNode end){
		location = loc;
		if(end !=null) {
			distanceToEnd = getDistanceBetweenNodes(location, end.location);
		}else {
			distanceToEnd=0;
		}
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
	 * Returns an distance between to nodes
	 * @param a - the location of the first node
	 * @param b - the location of the second node
	 * @return the integer distance
	 */
	static float getDistanceBetweenNodes(MapLocation a, MapLocation b) {
		int x = Math.abs(a.loc.x()-b.loc.x());
		int y = Math.abs(a.loc.y()-b.loc.y());
		if(x>y)return SQRT2*y+(x-y);
		return SQRT2*x+(y-x);
	}
	
	/**
	 * Returns an integer representing the expected number of nodes inbetween the two
	 * @param a - the location of the first node
	 * @param b - the location of the second node
	 * @return the integer distance
	 */
	static int getNodesBetweenNodes(MapLocation a, MapLocation b) {
		int x = Math.abs(a.loc.x()-b.loc.x());
		int y = Math.abs(a.loc.y()-b.loc.y());
		if(x>y)return x;
		return y;
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
