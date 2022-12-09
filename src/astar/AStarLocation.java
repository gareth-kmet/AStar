package astar;

public final class AStarLocation{
	int g=0;
	int h=0;
	int f() {return g+h;}
	State state = State.NONE;
	int phase = -1;
	AStarLocation parent = null;
	
	public final AStarLocation.ChunkLocation chunkLoc;
	
	final AStarLocation.GridLocation gridLoc;
	
	AStarLocation(AStarLocation.ChunkLocation chunkLoc, AStarLocation.GridLocation gridLoc){
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
	
	static int getDistanceBetweenNodes(AStarLocation.GridLocation a, AStarLocation.GridLocation b) {
		int x = Math.abs(a.x()-b.x());
		int y = Math.abs(a.y()-b.y());
		if(x>y)return 14*y+10*(x-y);
		return 14*x+10*(y-x);
	}
	
	enum State{CLOSED,OPEN,NONE}

	public static record GridLocation(int x, int y) {}

	public static record ChunkLocation(int cx, int cy, int px, int py) {}
}