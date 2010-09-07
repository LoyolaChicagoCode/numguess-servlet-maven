package num.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import num.NumberGuessBean;

/**
 * Command-line interface to the game logic. It is structured in the same
 * state-based form as the servlet.
 * 
 * @see num.NumberGuessBean
 * @see num.NumberGuessServlet
 */

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().play();
	}

	private final static int NEW = 888;
	private final static int ERROR = 889;
	private final static int WRONG = 890;
	private final static int RIGHT = 891;

	public void play() throws IOException {
		NumberGuessBean model = new NumberGuessBean();
		String guess = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		PrintStream out = System.out;

		do {

			// Compute resulting state and response information
			int state = ERROR;
			int response = 0;
			int guesses = 0;

			if (guess.length() == 0) {
				// If guess is null, then this is a new game
				state = NEW;
				model.reset();
			} else {
				// Convert guess parameter to int and send to model
				// Check for format and range
				int intGuess = -1;
				try {
					intGuess = Integer.parseInt(guess.trim());
					if (0 < intGuess && intGuess <= NumberGuessBean.MAX) {
						state = WRONG;
					}
				} catch (NumberFormatException e) {
				}
				// Make guess and obtain response info from model bean
				// Count as a guess attempt even if invalid
				model.makeGuess(intGuess);
				guesses = model.getGuesses();
				if (state != ERROR) {
					response = model.getResponse();
					if (response == NumberGuessBean.SUCCESS) {
						state = RIGHT;
					}
				}
			}

			switch (state) {

			case ERROR:

				out.println("Error: please enter a number between 1 and "
						+ NumberGuessBean.MAX + ".");
				out.println("You have made " + guesses + " guesses.");
				out.print("What's your guess? ");

				break;

			case RIGHT:

				out.println("Congratulations!  You got it.  And after just "
						+ guesses + " tries.");
				out.print("Press return to play again> ");

				break;

			case NEW:

				out.println("Welcome to the Number Guess game.");
				out.println("I'm thinking of a number between 1 and "
						+ NumberGuessBean.MAX + ".");
				out.print("What's your guess? ");

				break;

			case WRONG:
			default:

				String hint = response == NumberGuessBean.TOO_LOW ? "higher"
						: "lower";

				out.println("Good guess, but nope.  Try " + hint
						+ ".  You have made " + guesses + " guesses.");
				out.println("I'm thinking of a number between 1 and 100.");
				out.print("What's your guess? ");
			}

			guess = input.readLine().trim();

		} while (guess != null);
	}
}
