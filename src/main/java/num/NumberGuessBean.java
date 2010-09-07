package num;

public class NumberGuessBean {

	public static int MAX = 100;
	public static int SUCCESS = 777;
	public static int TOO_LOW = 778;
	public static int TOO_HIGH = 779;

	public static int DEFAULT_ANSWER = 17;

	private int answer;
	private int guess;
	private int guesses;

	public NumberGuessBean() {
		reset();
	}

	public void makeGuess(int value) {
		guess = value;
		guesses++;
	}

	public int getResponse() {
		if (guess == answer) {
			return SUCCESS;
		} else if (guess < answer) {
			return TOO_LOW;
		} else {
			return TOO_HIGH;
		}
	}

	public int getGuesses() {
		return guesses;
	}

	public void resetTo(int value) {
		answer = value;
		guesses = 0;
	}

	public void reset() {
		// reset(Math.abs(new Random().nextInt() % MAX) + 1);
		resetTo(DEFAULT_ANSWER);
	}
}
