
public class ScoreObject implements Comparable<ScoreObject> {
	private int score;
	private String name;
	
	public ScoreObject(int score, String name) {
		this.name = name;
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ScoreObject c = (ScoreObject) obj;
        return score == c.score;
    }
	
	@Override
	public int compareTo(ScoreObject c) {
		if (score - c.score != 0) {
			return score - c.score;
		} else {
			return name.compareTo(c.name);
		}
    }
}
