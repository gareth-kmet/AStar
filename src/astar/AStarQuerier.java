package astar;

public interface AStarQuerier {
	
	public Answer query(Question q);
	
	public record Question(long id, MapLocation to, MapLocation from) {}
	public record Answer(boolean walkable, float penalty) {}

}
