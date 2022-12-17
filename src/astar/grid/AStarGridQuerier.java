package astar.grid;

import astar.AStarLocation;
import astar.AStarQuerier;
import util.Util;

/**
 * Represents an AStar algorithm running on an infinite grid
 * <p>
 * The supports the usage of a chunk/pixel system wherein chunks are given a coordinate position and 
 * each chunk contains the same array size of pixels
 * <p>
 * Each neighbour is defined by the 8 coordinates surrounding a given location.
 * The default heuristic is given by the distance between two points
 * @author Gareth Kmet
 *
 */
public abstract class AStarGridQuerier implements AStarQuerier{
	
	protected static final float SQRT2 = (float)Math.sqrt(2);
	
	/**
	 * The amount of pixels that make up the width (x) and height (y) of a chunk
	 */
	protected final int pxsize, pysize;
	
	/**
	 * The constructor for this querier
	 * @param pxsize - the number of pixels that make up the width of a chunk
	 * @param pysize - the number of pixels that make up the height of a chunk
	 */
	public AStarGridQuerier(int pxsize, int pysize) {
		this.pxsize = pxsize;
		this.pysize = pysize;
	}
	

	@Override
	public final float heuristic(long id, AStarLocation loc, AStarLocation end) {
		return distance(loc,end);
	}

	@Override
	public NeighbourAnswer neighbours(long id, AStarLocation from) {
		NeighbourAnswer a = new NeighbourAnswer();
		MapPosition p = astarToMap(from);
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				if(i==j)continue;
				MapPosition pp = new MapPosition(p.x+i, p.y+j);
				a.neighbours().put(mapToAStar(pp), distance(p,pp));
			}
		}
		return a;
	}
	
	/**
	 * The distance between two points given as {@link GridPosition}
	 * @param a - the first point
	 * @param b - the second point
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 * @return the float distance between the two points
	 */
	public static final float distance(GridPosition a, GridPosition b, int pxsize, int pysize) {
		return distance(gridToMap(a, pxsize, pysize), gridToMap(b, pxsize, pysize));
		
	}
	
	/**
	 * The distance between two points given as {@link AStarLocation}
	 * @param a - the first point
	 * @param b - the second point
	 * @return the float distance between the two points
	 */
	public static final float distance(AStarLocation a, AStarLocation b) {
		return distance(astarToMap(a), astarToMap(b));
	}
	
	/**
	 * The distance between two points given as {@link MapPosition}
	 * @param a - the first point
	 * @param b - the second point
	 * @return the float distance between the two points
	 */
	public static final float distance(MapPosition a, MapPosition b) {
		int x = Math.abs(a.x-b.x);
		int y = Math.abs(a.y-b.y);
		if(x>y)return SQRT2*y+(x-y);
		return SQRT2*x+(y-x);
	}
	
	/**
	 * Converts a {@link MapPosition} to an {@link AStarLocation}
	 * @param p - the map position
	 * @return the AStar position
	 */
	public static final AStarLocation mapToAStar(MapPosition p) {
		return new AStarLocation(Util.pointToSpiral(p.x, p.y));
	}
	
	/**
	 * Converts an {@link AStarLocation} to a {@link MapPosition}
	 * @param a - the AStar position
	 * @return the map position
	 */
	public static final MapPosition astarToMap(AStarLocation a) {
		int[] p = Util.spiralToPoint(a.id());
		return new MapPosition(p[0], p[1]);
	}
	
	/**
	 * Converts a {@link MapPosition} to a {@link GridPosition}
	 * @param p - the map position
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 * @return the grid position
	 */
	public static final GridPosition mapToGrid(MapPosition p, int pxsize, int pysize) {
		int cx = p.x/pxsize, px = p.x%pxsize;
		int cy = p.y/pysize, py = p.y%pysize;
		return new GridPosition(cx, cy, px, py);
	}
	
	/**
	 * Converts a {@link GridPosition} to a {@link MapPosition}
	 * @param g - the grid position
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 * @return the map position
	 */
	public static final MapPosition gridToMap(GridPosition g, int pxsize, int pysize) {
		int x = pxsize*g.cx+g.px;
		int y = pysize*g.cy+g.py;
		return new MapPosition(x, y);
	}
	
	/**
	 * Converts a {@link GridPosition} to an {@link AStarLocation}
	 * @param g - the grid position
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 * @return the AStar position
	 */
	public static final AStarLocation gridToAStar(GridPosition g, int pxsize, int pysize) {
		return mapToAStar(gridToMap(g, pxsize, pysize));
	}
	
	/**
	 * Converts a {@link AStarLocation} to a {@link GridPosition}
	 * @param a - the AStar position
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 * @return the grid position
	 */
	public static final GridPosition astarToGrid(AStarLocation a, int pxsize, int pysize) {
		return mapToGrid(astarToMap(a), pxsize, pysize);
	}
	
	/**
	 * Represents a position as a unique global <code>x,y</code> coordinate 
	 * @author Gareth Kmet
	 */
	public record MapPosition(int x, int y) {}
	
	/**
	 * Represents a position as a <code>px,py</code> location within a chunk at position <code>cx,cy</code>
	 * @author Gareth Kmet
	 *
	 */
	public record GridPosition(int cx, int cy, int px, int py) {}
	
}
