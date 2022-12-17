package astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import astar.AStarNode.State;
import astar.AStarQuerier.NeighbourAnswer;
import astar.AStarQuerier.QueryAnswer;

public final class AStar {
	
	/**
	 * Represents the id of this instance.
	 * This will allow for an the calling program to know which 
	 * astar algorithm is querying if multiple exist
	 */
	protected final long id;
	
	/**
	 * The querier for this algorithm
	 */
	protected final AStarQuerier querier;
	
	/**
	 * The heuristic function for this algorithm
	 */
	protected final AStarHeuristic heuristic;
	
	/**
	 * The comparator to order the priority queue
	 */
	protected final Comparator<AStarNode> priorityQueueCompare;
	
	/**
	 * A hashmap containing the node for each grid coordinate 
	 */
	protected final HashMap<AStarLocation, AStarNode> map = new HashMap<AStarLocation, AStarNode>();
	
	/**
	 * The constructor of this algorithm with a heuristic and id
	 * @param id - the id
	 * @param querier - the querier
	 * @param heuristic - the heuristic
	 */
	public AStar(long id, AStarQuerier querier, AStarHeuristic heuristic) {
		this.id = id;
		this.querier = querier;
		this.heuristic = heuristic;
		this.priorityQueueCompare = new Comparator<AStarNode>() {
			@Override
			public int compare(AStarNode o1, AStarNode o2) {
				float f = o1.g()+heuristic.h(o1)-o2.g()-heuristic.h(o2);
				return (int)(f*10) + (int)Math.signum(f);
			}
		};
	} 
	
	/**
	 * The constructor of this algorithm with an id
	 * @param id - the id
	 * @param querier - the querier
	 */
	public AStar(long id, AStarQuerier querier) {
		this(id,querier,new AStarHeuristic.Admissable());
	}
	
	/**
	 * The constructor of this algorithm with a heuristic
	 * @param querier - the querier
	 * @param heuristic - the heuristic
	 */
	public AStar(AStarQuerier querier, AStarHeuristic heuristic) {
		this(0,querier,heuristic);
	}
	
	/**
	 * The constructor of this algorithm
	 * @param querier - the querier
	 */
	public AStar(AStarQuerier querier) {
		this(0,querier);
	}
	
	/**
	 * Runs the algorithm
	 * 
	 * @param startLoc
	 * @param targetLoc
	 * @return a {@link Result} of the algorithm
	 */
	public final Result run(AStarLocation startLoc, AStarLocation targetLoc) {
		map.clear();
		AStarNode target = new AStarNode(targetLoc, 0);
		AStarNode start = new AStarNode(startLoc, querier.heuristic(id, startLoc, targetLoc));
		map.put(start.location, start);
		map.put(target.location, target);
		
		PriorityQueue<AStarNode> open = new PriorityQueue<AStarNode>(priorityQueueCompare);
		HashSet<AStarLocation> closed = new HashSet<AStarLocation>();
		
		open.offer(start);
		start.state=State.OPEN;
		
		heuristic.setState(id, start, target, querier);
		
		boolean success=false;
		
		while(!open.isEmpty()) {
			AStarNode current = open.poll();
			closed.add(current.location);
			current.state=State.CLOSED;
			
			if(current==target) {
				success=true;
				break;
			}
			NeighbourAnswer neighbours = querier.neighbours(id, current.location);
			for(AStarLocation location : neighbours.neighbours().keySet()) {//getNeighbours(current.location.loc)
				
				if(closed.contains(location)) {
					continue;
				}
				
				QueryAnswer conditions = querier.query(id, current.location, location);
				
				if(!conditions.walkable()) continue;
				
				AStarNode neighbour = map.get(location);
				if(neighbour==null) {
					neighbour = new AStarNode(location, querier.heuristic(id, location, targetLoc));
					map.put(location, neighbour);
				}
				
				float neighbourNewCost = current.gCost + conditions.penalty() 
						+ neighbours.neighbours().get(location);
				
				if(neighbour.state==State.OPEN && neighbourNewCost > neighbour.gCost) continue;
				
				if(neighbour.state==State.OPEN) open.remove(neighbour);
				
				neighbour.gCost = neighbourNewCost;
				neighbour.state = State.OPEN;
				neighbour.parent = current;
				neighbour.depth = neighbour.parent.depth + 1;
				
				open.offer(neighbour);
			}
		}
		
		if(success) {
			ArrayList<AStarLocation> path = new ArrayList<AStarLocation>();
			AStarNode t = target;
			while(t!=null) {
				path.add(0,t.location);
				t=t.parent;
			}
			return new Result(new AStarLocation[0], path.toArray(new AStarLocation[0]));
		}else {
			return new Result(null, null);
		}
	}
	
	
	/**
	 * The result of the algorithm
	 * Contains <b>waypoints</b> and the full <b>path</b>
	 * <p>
	 * The <code>path</code> array will contain each location that is within the path. 
	 * <p>
	 * The <code>waypoints</code> array will contain a simplified version of the path 
	 * with only corners included
	 * @author Gareth Kmet
	 *
	 */
	public record Result(AStarLocation[] waypoints, AStarLocation[] path) {};
	

}
