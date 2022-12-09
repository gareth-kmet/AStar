package main;

import astar.AStar;
import astar.AStarQuerrier;
import astar.AStarQuerrier.ChunkLocation;

public class Main {

	public static void main(String[] args) {
		AStarQuerrier q = new AStarQuerrier() {
			
			@Override
			public Answer querry(Question question) {
				// TODO Auto-generated method stub
				return new Answer(true);
			}
		};
		AStar star = new AStar(q, 5, 5, 10);
		star.run(new ChunkLocation(0,0,0,0), new ChunkLocation(4,4,9,8));

	}


}
