package main;

import astar.AStar;
import astar.AStarLocation;
import astar.AStarQuerrier;

public class Main {

	public static void main(String[] args) {
		AStarQuerrier q = new AStarQuerrier() {@Override public Answer querry(Question question) {return new Answer(true);}};
		AStar star = new AStar(q, 5, 5, 10, 0, 0);
		star.run(new AStarLocation.ChunkLocation(0,0,0,0), new AStarLocation.ChunkLocation(4,4,9,8));

	}


}
