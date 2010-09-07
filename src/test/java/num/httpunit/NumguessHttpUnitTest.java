package num.httpunit;

import junit.framework.TestCase;
import num.NumberGuessBean;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

/**
 * HttpUnit examples for NumguessServlet. The servlet container should already
 * be running with NumguessServlet deployed.
 */
public class NumguessHttpUnitTest extends TestCase {

	public final static String BASE_URL = "http://localhost:8080/numguess-servlet";

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLowGuess() throws Exception {
		WebConversation wc = new WebConversation();
		WebResponse index = wc
				.getResponse(BASE_URL);
		WebLink link = index.getLinkWith("game");
		link.click();
		WebResponse game = wc.getCurrentPage();
		WebForm form = game.getForms()[0];
		form.setParameter("guess", Integer
				.toString(NumberGuessBean.DEFAULT_ANSWER - 1));
		WebResponse response = form.submit();
		assertTrue(response.getText().indexOf("higher") >= 0);
	}

	public void testHighGuess() throws Exception {
		WebConversation wc = new WebConversation();
		WebResponse index = wc
				.getResponse(BASE_URL);
		WebLink link = index.getLinkWith("game");
		link.click();
		WebResponse game = wc.getCurrentPage();
		WebForm form = game.getForms()[0];
		form.setParameter("guess", Integer
				.toString(NumberGuessBean.DEFAULT_ANSWER + 1));
		WebResponse response = form.submit();
		assertTrue(response.getText().indexOf("lower") >= 0);
	}

	public void testCorrectGuess() throws Exception {
		WebConversation wc = new WebConversation();
		WebResponse index = wc
				.getResponse(BASE_URL);
		WebLink link = index.getLinkWith("game");
		link.click();
		WebResponse game = wc.getCurrentPage();
		WebForm form = game.getForms()[0];
		form.setParameter("guess", Integer
				.toString(NumberGuessBean.DEFAULT_ANSWER));
		WebResponse response = form.submit();
		assertTrue(response.getText().indexOf("Congratulations") >= 0);
	}
}