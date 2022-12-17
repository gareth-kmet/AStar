package astar.grid;

import astar.AStarLocation;

/**
 * Represents an AStar Algorithm querier which is based off of a grid with boundary points (inclusive)
 * @author Gareth Kmet
 *
 */
public abstract class AStarGridFiniteQuerier extends AStarGridQuerier {
	
	/**
	 * The <u>t</u>op-<u>l</u>eft boundary point and the <u>b</u>ottom-<u>r</u>ight boundary point (inclusive)
	 */
	public final MapPosition tl, br;
	
	/**
	 * The constructor given a top left and bottom right boundary points (inclusive) as {@link MapPosition}
	 * @param tl - the top left boundary point
	 * @param br - the bottom right boundary point
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 */
	public AStarGridFiniteQuerier(MapPosition tl, MapPosition br, int pxsize, int pysize) {
		super(pxsize, pysize);
		this.tl=tl;
		this.br=br;
	}
	
	/**
	 * The constructor given a top left and bottom right boundary points (inclusive) as {@link GridPosition}
	 * @param tl - the top left boundary point
	 * @param br - the bottom right boundary point
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 */
	public AStarGridFiniteQuerier(GridPosition tl, GridPosition br, int pxsize, int pysize) {
		this(gridToMap(tl, pxsize, pysize), gridToMap(br, pxsize, pysize), pxsize, pysize);
	}
	
	/**
	 * The constructor given a number of chunks
	 * <p>
	 * The boundary points are put at the top left corner and the bottom right corner
	 * @param cxsize - the number of chunks along the x-axis
	 * @param cysize - the number of chunks along the y-axis
	 * @param pxsize - the pixel width of a chunk
	 * @param pysize - the pixel height of a chunk
	 */
	public AStarGridFiniteQuerier(int cxsize, int cysize, int pxsize, int pysize) {
		this(new GridPosition(0, 0, 0, 0), new GridPosition(cxsize-1, cysize-1, pxsize-1, pysize-1), pxsize, pysize);
	}
	
	@Override
	public NeighbourAnswer neighbours(long id, AStarLocation from) {
		NeighbourAnswer a = new NeighbourAnswer();
		MapPosition p = astarToMap(from);
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				if(i==j)continue;
				
				int x = p.x()+i;
				int y = p.y()+j;
				
				if(tl.x()<=x && x<=br.x() && tl.y()<=y && y<=br.y()) {
					MapPosition pp = new MapPosition(x,y);
					a.neighbours().put(mapToAStar(pp), distance(p,pp));
				}
				
				
			}
		}
		return a;
	}
	
}
