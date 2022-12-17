package astar;

/**
 * Interface for the heuristic function used by the algorithm
 * <p>
 * Also contains static subclasses which are default types of heuristics that can be used
 * @author Gareth Kmet
 *
 */
public interface AStarHeuristic {
	/**
	 * Returns the heuristic for this node
	 * @param a - the node
	 * @return the float heuristic
	 */
	public float h(AStarNode a);
	
	
	/**
	 * Initiates this instance for the current run of the AStar algorithm
	 * @param id - the id of the algorithm
	 * @param start - the starting node
	 * @param target - the target node
	 * @param querier - the querier being used by the algorithm
	 */
	public void setState(long id, AStarNode start, AStarNode target, AStarQuerier querier);
	
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
		public void setState(long id, AStarNode start, AStarNode target, AStarQuerier querier) {}
		
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
		public void setState(long id, AStarNode start, AStarNode target, AStarQuerier querier) {
			N = querier.heuristic(id, start.location, target.location);
			N_1=1f/N;
			
		}
		
	}
}
