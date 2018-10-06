import java.io.*;
import java.util.*;

public class Score {
	private List<ScoreObject> list;
	private static String file = "files/HighScores.txt";

	// creates a Score
	public Score(Reader r) throws IOException {
		list = new LinkedList<ScoreObject>();
		if (r == null) {
			throw new IllegalArgumentException();
		}
		try {
			BufferedReader br = new BufferedReader(r);
			String score = "";
			while ((score = br.readLine()) != null) {
				int scoreInt = Integer.parseInt(score);
				String name = br.readLine();
				list.add(new ScoreObject(scoreInt, name));
			}
			r.close();
		} catch (IOException e) {
			throw new IOException();
		}
	}

	// creates a new file to read from
	public static Score make(String filename) throws IOException {
		if (filename == null || filename.equals("")) {
			throw new IllegalArgumentException();
		}
		file = filename;
		Reader r = new FileReader(filename);
		Score s = new Score(r);
		r.close();
		return s;
	}

	// writes score to the given file
	public void write(int newScore, String name) throws IOException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file , true));
			bw.write("" + newScore);
			bw.newLine();
			bw.write("" + name);
			bw.newLine();
			list.add(new ScoreObject(newScore, name));
		} catch (IOException e) {
			throw new IOException();
		} finally {
			bw.close();
		}
	}

	// returns the top 5 scores
	public String getHighScores() {
		Collections.sort(list);
		Collections.reverse(list);
		String str = "";
		int count = 1;
		for (ScoreObject i : list) {
			if (count < 4) {
				str += count + ". " + i.getName() + " - " + i.getScore() + "         ";
				count++;
			}
		}
		return str;
	}
}
