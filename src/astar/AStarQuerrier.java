package astar;

public interface AStarQuerrier {
	
	public Answer querry(Question question);
	
	public record Question(long id, AStarLocation from, AStarLocation to) {}
	
	public record Answer(boolean walkable, int penalty) {
		public Answer(boolean walkable) {
			this(walkable, 0);
		}
	}
}
