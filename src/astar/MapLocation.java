package astar;

public class MapLocation {
	
	public final Loc loc;
	
	public final int cx,cy,px,py;
	
	MapLocation(Loc loc){
		this.loc=loc;
		cx=0;cy=0;px=0;py=0;
	}
	
	public MapLocation(int x, int y) {
		this.loc = new Loc(x,y);
		cx=0;cy=0;px=0;py=0;
	}
	
	
	public static record Loc(int x, int y) {};

}
