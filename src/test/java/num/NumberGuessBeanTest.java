package num;

/*
 * Created on Jan 5, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import junit.framework.TestCase;

/**
 * @author laufer
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class NumberGuessBeanTest extends TestCase {

	private NumberGuessBean subject;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		subject = new NumberGuessBean();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		subject = null;
		super.tearDown();
	}

	/**
	 * Constructor for NumberGuessBeanTest.
	 * 
	 * @param arg0
	 */
	public NumberGuessBeanTest(String arg0) {
		super(arg0);
	}

	public void testMakeGuessRight() {
		final int guess = 77;
		subject.resetTo(guess);
		subject.makeGuess(guess);
		assertEquals(NumberGuessBean.SUCCESS, subject.getResponse());
	}

	public void testMakeGuessLow() {
		final int guess = 77;
		subject.resetTo(guess);
		subject.makeGuess(guess - 1);
		assertEquals(NumberGuessBean.TOO_LOW, subject.getResponse());
	}

	public void testMakeGuessHigh() {
		final int guess = 77;
		subject.resetTo(guess);
		subject.makeGuess(guess + 1);
		assertEquals(NumberGuessBean.TOO_HIGH, subject.getResponse());
	}

	public void testGetGuessesZero() {
		final int guess = 77;
		subject.resetTo(guess);
		assertEquals(0, subject.getGuesses());
	}

	public void testGetGuessesWrong() {
		final int guess = 77;
		subject.resetTo(guess);
		subject.makeGuess(guess + 1);
		subject.makeGuess(guess + 1);
		subject.makeGuess(guess + 1);
		assertEquals(3, subject.getGuesses());
	}

	public void testGetGuessesRight() {
		final int guess = 77;
		subject.resetTo(guess);
		subject.makeGuess(guess + 1);
		subject.makeGuess(guess + 1);
		subject.makeGuess(guess + 1);
		subject.makeGuess(guess);
		assertEquals(4, subject.getGuesses());
	}
}
