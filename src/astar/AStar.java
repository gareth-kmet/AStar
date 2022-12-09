package astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import astar.AStarQuerrier.Answer;
import astar.AStarQuerrier.Question;

public final class AStar {
	
	private final long id;
	@SuppressWarnings("unused")
	private final int cxsize, cysize, psize, cxoffset, cyoffset;
	private final int xsize, ysize;
	private final AStarQuerrier querrier;
	
	private final AStarLocation[][] grid;
	
	private int phase = -1;

	
	public AStar(AStarQuerrier querry, int cxsize, int cysize, int psize, int cxoffset, int cyoffset) {
		this(0,querry, cxsize,cysize,psize, cxoffset, cyoffset);
	}
	
	public AStar(long id, AStarQuerrier querry, int cxsize, int cysize, int psize, int cxoffset, int cyoffset) {
		this.id = id;
		this.cxsize = cxsize;
		this.cysize = cysize;
		this.cxoffset=cxoffset;
		this.cyoffset=cyoffset;
		this.psize = psize;
		this.querrier=querry;
		
		xsize = cxsize*psize;
		ysize = cysize*psize;
		
		grid = new AStarLocation[xsize][ysize];
		for(int x=0;x<xsize;x++) {
			for(int y=0;y<ysize;y++) {
				AStarLocation.GridLocation gl = new AStarLocation.GridLocation(x,y);
				grid[x][y] = new AStarLocation(gridToChunk(gl), gl);
			}
		}
	}
	
	private AStarLocation.GridLocation chunkToGrid(AStarLocation.ChunkLocation cl) {
		int x = (cl.cx()-cxoffset)*psize+cl.px();
		int y = (cl.cy()-cyoffset)*psize+cl.py();
		return new AStarLocation.GridLocation(x,y);
	}
	
	private AStarLocation.ChunkLocation gridToChunk(AStarLocation.GridLocation gl) {
		int cx = gl.x()/psize;
		int cy = gl.y()/psize;
		int px = gl.x()%psize+cxoffset;
		int py = gl.y()%psize+cyoffset;
		return new AStarLocation.ChunkLocation(cx,cy,px,py);
	}
	
	private AStarLocation getLocation(AStarLocation.ChunkLocation cl) {
		AStarLocation.GridLocation gl = chunkToGrid(cl);
		return grid[gl.x()][gl.y()];
	}
	
	public Result run(AStarLocation.ChunkLocation startLoc, AStarLocation.ChunkLocation targetLoc) {
		AStarLocation start = getLocation(startLoc);
		AStarLocation target = getLocation(targetLoc);

		start.reset(phase, target);
		target.reset(phase, target);
		
		PriorityQueue<AStarLocation> open = new PriorityQueue<AStarLocation>(LOCATION_COMPARATOR); 
		HashSet<AStarLocation> closed = new HashSet<AStarLocation>(); 
		
		open.add(start);
		start.state=AStarLocation.State.OPEN;
		
		boolean success = false;
		
		while(!open.isEmpty()) {
			AStarLocation current = open.poll();
			closed.add(current);
			current.state = AStarLocation.State.CLOSED;
			
			if(current==target) {
				success=true;
				break;
			}
			
			for(AStarLocation neighbour : getNeighbours(current)) {
				if(neighbour.phase!=phase) neighbour.reset(phase, target);
				
				Answer conditions = querrier.querry(new Question(id, current, neighbour));
				
				if(!conditions.walkable() || neighbour.state==AStarLocation.State.CLOSED) continue;
				
				int neighbourNewCost = current.g + conditions.penalty() + AStarLocation.getDistanceBetweenNodes(current.gridLoc, neighbour.gridLoc);
				
				boolean contains = neighbour.state==AStarLocation.State.OPEN;
				
				if(neighbourNewCost<neighbour.g && contains) continue;
				
				if(contains) open.remove(neighbour);
				
				neighbour.g = neighbourNewCost;
				neighbour.state=AStarLocation.State.OPEN;
				neighbour.parent = current;
				
				open.offer(neighbour);
			}
		}
		
		if(success) {
			ArrayList<AStarLocation.ChunkLocation> path = new ArrayList<AStarLocation.ChunkLocation>();
			AStarLocation t = target;
			while(t!=null) {
				//System.out.println("("+t.gridLoc.x()+", "+t.gridLoc.y()+")");
				path.add(0,t.chunkLoc);
				t=t.parent;
			}
			return new Result(new AStarLocation.ChunkLocation[0], path.toArray(new AStarLocation.ChunkLocation[0]));
		}else {
			return null;
		}
		
	}
	
	private final HashSet<AStarLocation> getNeighbours(AStarLocation l){
		HashSet<AStarLocation> set = new HashSet<AStarLocation>();
		
		for(int i=-1; i<=1; i++) for(int j=-1; j<=1; j++) {
			int x = l.gridLoc.x()+i;
			int y = l.gridLoc.y()+j;
			
			if((i==0 && j==0) || (x<0 || x>=xsize) || (y<0 || y>=ysize)) {
				continue;
			}
			
			set.add(grid[x][y]);
		}
		
		return set;
	}
	
	private static final Comparator<AStarLocation> LOCATION_COMPARATOR = new Comparator<AStarLocation>() {
		@Override
		public int compare(AStarLocation o1, AStarLocation o2) {
			return o1.f()-o2.f();
		}
	};
	
	public record Result(AStarLocation.ChunkLocation[] waypoints, AStarLocation.ChunkLocation[] path) {};
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
