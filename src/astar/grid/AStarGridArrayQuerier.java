package astar.grid;

import astar.AStarLocation;

/**
 * Represents an AStar algorithm querier given a finite pre-generated set of immutable values
 * @author Gareth Kmet
 *
 */
public class AStarGridArrayQuerier extends AStarGridFiniteQuerier{
	
	/**
	 * The grid of values
	 */
	protected final QueryAnswer[][][][] grid;
	
	/**
	 * The constructor
	 * @param grid - the grid of values where each value can be found at 
	 * <br><code>grid[chunk-x-pos][chunk-y-pos][pixel-x-pos][pixel-y-pos]</code>
	 */
	public AStarGridArrayQuerier(QueryAnswer[][][][] grid) {
		super(grid.length,grid[0].length,grid[0][0].length,grid[0][0][0].length);
		this.grid=grid;
	}
	
	@Override
	public QueryAnswer query(long id, AStarLocation to, AStarLocation from) {
		GridPosition p = astarToGrid(to, pxsize, pysize);
		return grid[p.cx()][p.cy()][p.px()][p.py()];
	}

}
