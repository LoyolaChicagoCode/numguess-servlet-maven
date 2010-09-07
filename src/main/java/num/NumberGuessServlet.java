package num;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NumberGuessServlet extends HttpServlet {

	private final static long serialVersionUID = 20060329;

	public final static String ATTRIBUTE_GUESS = "guess";
	public final static String ATTRIBUTE_APPDATA = "appdata";

	private final static String PARAMETER_GUESS = "guess";

	private final static int NEW = 888;
	private final static int ERROR = 889;
	private final static int WRONG = 890;
	private final static int RIGHT = 891;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// Obtain the request parameter
		final String guess = req.getParameter(PARAMETER_GUESS);
		// Obtain/create the session object
		final HttpSession session = req.getSession(true);
		// Obtain the bean containing the model and store in session
		// object
		final NumberGuessBean model = (NumberGuessBean) session
				.getAttribute(ATTRIBUTE_GUESS);
		// Obtain the bean containing the shared application-level data
		final NumberGuessAppData appData = (NumberGuessAppData) session
				.getServletContext().getAttribute(ATTRIBUTE_APPDATA);

		// Variables for state and response information
		int state = ERROR;
		int response = 0;
		int guesses = 0;
		boolean newBestScore = false;

		// Lock the model object while accessing or modifying its state
		// (external locking could be avoided by restructuring the model
		// interface)
		synchronized (model) {

			// Compute state and response information
			if (guess == null) {
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
						newBestScore = appData.updateBestScore(model
								.getGuesses());
						model.reset();
					}
				}
			}
		} // Unlock model object here

		// Present response based on the UI state
		res.setContentType("text/html");
		ServletOutputStream out = res.getOutputStream();
		out.println("<h1>Number Guessing Game (Servlet Version)</h1>");

		switch (state) {

		case ERROR:

			out.println("<p><b>Error:</b> please enter a number between 1 and "
					+ NumberGuessBean.MAX + ".</p>");
			out.println("You have made " + guesses + " guesses.</p>");
			out.println("<form method=get>");
			out.println("What's your guess? <input type=text name=guess>");
			out.println("<input type=submit value=\"Submit\">");
			out.println("</form>");

			break;

		case RIGHT:

			out.println("<p>Congratulations!  You got it.");
			out.println("And after just " + guesses + " tries.");
			if (newBestScore) {
				out.println("That's the best score so far.");
			}
			out.println("</p><p>Care to <a href=\"" + req.getRequestURI()
					+ "\">try again</a>?</p>");

			break;

		case NEW:

			out.println("<p>Welcome to the Number Guess game.</p>");
			out.println("<p>I'm thinking of a number between 1 and "
					+ NumberGuessBean.MAX + ".</p>");
			out.println("<form method=get>");
			out.println("What's your guess? <input type=text name=guess>");
			out.println("<input type=submit value=\"Submit\">");
			out.println("</form>");

			break;

		case WRONG:
		default:

			String hint = response == NumberGuessBean.TOO_LOW ? "higher"
					: "lower";

			out.println("<p>Good guess, but nope.  Try <b>" + hint + "</b>.");
			out.println("You have made " + guesses + " guesses.</p>");
			out.println("<p>I'm thinking of a number between 1 and 100.</p>");
			out.println("<form method=get>");
			out.println("What's your guess? <input type=text name=guess>");
			out.println("<input type=submit value=\"Submit\">");
			out.println("</form>");
		}

		out.close();
	}
}
