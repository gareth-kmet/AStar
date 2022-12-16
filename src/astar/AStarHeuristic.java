package astar;

public interface AStarHeuristic {
	/**
	 * Returns the heuristic for this node
	 * @param a - the node
	 * @return the float heuristic
	 */
	public float h(AStarNode a);
	
	
	/**
	 * Initiates this instance for the current run of the AStar algorithm
	 * @param start - the start node
	 * @param target - the target node
	 */
	public void setState(AStarNode start, AStarNode target);
	
	/**
	 * Returns the most optimal solution
	 * <p>
	 * Guarantees an optimal solution path, but also means that every equally meritorious paths must be examined
	 * <p> 
	 * Has the heuristic that is equal to the distance to the end target
	 * @author Gareth Kmet
	 */
	public final class Admissable implements AStarHeuristic{

		@Override
		public float h(AStarNode a) {
			return a.h();
		}

		@Override
		public void setState(AStarNode start, AStarNode target) {}
		
	}
	
	/**
	 * Returns a less optimal path using the depth of the current search
	 * <p>
	 * This algorithm will use a weighted heuristic dependent on the current depth in the search
	 * <br> This will reutnr a less optimal path but will be quicker and less intensive on time
	 * @author Gareth Kmet
	 *
	 */
	public final class DynamicWeighting implements AStarHeuristic{
		/**
		 * A constant greater than 1
		 */
		private final float e;
		
		/**
		 * The expected depth of the algorithm
		 */
		private float N;
		/**
		 * 1/{@link #N}
		 */
		private float N_1;
		
		/**
		 * @param epsilon - a constant float greater than 1
		 */
		public DynamicWeighting(float epsilon) {
			this.e=epsilon;
		}

		@Override
		public float h(AStarNode a) {
			int d = a.d();
			float w;
			if(d>N) {
				w=0;
			}else {
				w=1-d*N_1;
			}
			
			return (1+e*w)*a.h();
			
		}

		@Override
		public void setState(AStarNode start, AStarNode target) {
			N = AStarNode.getNodesBetweenNodes(start.location, target.location);
			N_1=1f/N;
			
		}
		
	}
}
