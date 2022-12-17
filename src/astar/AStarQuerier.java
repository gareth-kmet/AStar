package astar;

import java.util.Collection;
import java.util.HashMap;
/**
 * Interface for methods which can be called by the AStar algorithm
 * @author Gareth Kmet
 *
 */
public interface AStarQuerier {
	
	/**
	 * Queries a location, <code>to</code>, given a second location, <code>from</code>
	 * @param id - the id of the algorithm
	 * @param to - the location which the algorithm needs information for
	 * @param from - the location which the algorithm is currently on
	 * @return a {@link QueryAnswer} containing the penalty and walkability of the <code>to</code> location when approaching from the <code>from</code> location
	 */
	public QueryAnswer query(long id, AStarLocation to, AStarLocation from);
	
	/**
	 * The default heurisitc of a location
	 * @param id - the id of the algorithm
	 * @param loc - the location of the queried location
	 * @param end - the target location of the algorithm
	 * @return the default heuristic float for the queried location
	 */
	public float heuristic(long id, AStarLocation loc, AStarLocation end);
	
	/**
	 * Returns the neighbours for a given location
	 * @param id - the id of the algorithm
	 * @param from - the location being queried
	 * @return a {@link NeighbourAnswer} containing the neighbours of this location along 
	 * with the default heuristic (the distance) between the neighbouring locations and the queried location.
	 * <br>This should be the same as the {@link #heuristic(long, AStarLocation, AStarLocation)} 
	 * function if given the arguments <code>id, from, neighbour</code>
	 */
	public NeighbourAnswer neighbours(long id, AStarLocation from);
	
	/**
	 * The neighbours of a given location
	 * <p>
	 * Represented by a dictionary containing a default heuristic distance for each neighbouring location
	 * <br> This distance should be the same value as calling {@link AStarQuerier#heuristic(long, AStarLocation, AStarLocation)}
	 * using the arguments <code>id, originalLocation, neighbourLocation</code>
	 * <p>
	 * Can also be called by giving a set of all neighbouring locations wherein each location will be given a distance of <code>1</code>
	 * <br>Also can be created using the default constructor wherein a black map is initiated
	 * @author Gareth Kmet
	 *
	 */
	public record NeighbourAnswer(HashMap<AStarLocation, Float> neighbours) {
		public NeighbourAnswer(Collection<AStarLocation> locations) {
			this();
			for(AStarLocation l : locations) {
				neighbours.put(l, 1f);
			}
		}
		public NeighbourAnswer() {
			this(new HashMap<AStarLocation, Float>());
		}
	}
	
	/**
	 * Represents the walkability and penalty returned when inquiring about a location 
	 * @author Gareth Kmet
	 *
	 */
	public record QueryAnswer(boolean walkable, float penalty) {}

}
