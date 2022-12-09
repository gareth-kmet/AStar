package astar;

public interface AStarQuerrier {
	
	public Answer querry(Question question);
	
	public record ChunkLocation(int cx, int cy, int px, int py) {};
	public record GridLocation(int x, int y) {}
	public record Question(long id, AStarLocation from, AStarLocation to) {}
	
	public record Answer(boolean walkable, int penalty) {
		public Answer(boolean walkable) {
			this(walkable, 0);
		}
	}
}
