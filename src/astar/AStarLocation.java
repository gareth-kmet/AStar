package astar;

import astar.AStarQuerrier.ChunkLocation;
import astar.AStarQuerrier.GridLocation;

public final class AStarLocation{
	int g=0;
	int h=0;
	int f() {return g+h;}
	State state = State.NONE;
	int phase = -1;
	AStarLocation parent = null;
	
	public final ChunkLocation chunkLoc;
	
	final GridLocation gridLoc;
	
	AStarLocation(ChunkLocation chunkLoc, GridLocation gridLoc){
		this.gridLoc = gridLoc;
		this.chunkLoc=chunkLoc;
	}
	
	final void reset(int phase, AStarLocation target) {
		g=0;
		h=getDistanceBetweenNodes(gridLoc, target.gridLoc);
		state = State.NONE;
		this.phase=phase;
		parent=null;
	}
	
	static int getDistanceBetweenNodes(GridLocation a, GridLocation b) {
		int x = Math.abs(a.x()-b.x());
		int y = Math.abs(a.y()-b.y());
		if(x>y)return 14*y+10*(x-y);
		return 14*x+10*(y-x);
	}
	
	enum State{CLOSED,OPEN,NONE}
}