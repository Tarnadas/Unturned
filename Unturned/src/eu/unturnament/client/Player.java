package eu.unturnament.client;

public class Player {

	private String name;
	private int votes;
	
	public Player(String name, int votes) {
		this.name = name;
		this.votes = votes;
	}
	
	public String getName() {
		return name;
	}
	
	public int getVotes() {
		return votes;
	}
	
}
