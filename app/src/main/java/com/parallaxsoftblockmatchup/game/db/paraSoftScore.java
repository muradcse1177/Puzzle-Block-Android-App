package com.parallaxsoftblockmatchup.game.db;

public class paraSoftScore {
	private long id;
	private long score;
	private String paraSoftplayerName;
	
	public paraSoftScore() {
		
	}

	  public long getId() {
	    return id;
	  }
	
	  public void setId(long id) {
	    this.id = id;
	  }
	
	  public long getScore() {
	    return score;
	  }
	
	  public String paraSoftgetScoreString() {
	    return String.valueOf(score);
	  }
	
	  public void setScore(long comment) {
	    this.score = comment;
	  }
	
	  public String getName() {
	    return paraSoftplayerName;
	  }
	
	  public void setName(String comment) {
	    this.paraSoftplayerName = comment;
	  }
	  @Override
	  public String toString() {
	    return  String.valueOf(score) + "@" + paraSoftplayerName;
	  }
}
