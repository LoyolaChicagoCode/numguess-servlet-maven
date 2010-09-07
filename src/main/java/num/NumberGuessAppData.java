package num;

/**
 * @author laufer
 * 
 */
public class NumberGuessAppData {

	private int bestScore = Integer.MAX_VALUE;

	public synchronized int getBestScore() {
		return bestScore;
	}

	public synchronized boolean updateBestScore(int score) {
		if (score < bestScore) {
			bestScore = score;
			return true;
		}
		return false;
	}
}
