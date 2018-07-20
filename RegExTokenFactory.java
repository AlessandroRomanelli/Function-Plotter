import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A TokenFactory that can recognize Tokens
 * based on regular expressions.
 */
public abstract class RegExTokenFactory implements TokenFactory {
	
	private final Matcher matcher;

	
	public RegExTokenFactory(final String regEx) {
		final Pattern pattern = Pattern.compile(regEx);
		matcher = pattern.matcher("");
	}
	
	public final void setText(String text) {
		matcher.reset(text);
	}
	
	public final boolean find(int startFrom) {
		final boolean found = matcher.find(startFrom);
		return found && startFrom==matcher.start();
	}
	
	public final int getTokenLength() {
		return matcher.end()-matcher.start();
	}
	
	protected final int getTokenStartPosition() {
		return matcher.start();
	}
	
	protected final String getTokenText() {
		return matcher.group();
	}
	
}
