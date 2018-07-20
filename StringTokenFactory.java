/**
 * A TokenFactory that can recognize Tokens based on Strings.
 */
public abstract class StringTokenFactory implements TokenFactory {
	
	private final String tokenText;
	private String text;
	private int startFrom;
	
	
	public StringTokenFactory(final String tokenText) {
		this.tokenText = tokenText;
	}
	
	public void setText(final String text) {
	    this.text = text;
	}

	public int getTokenLength() {
	    return tokenText.length();
	}
	
	public final boolean find(final int startFrom) {
		this.startFrom = startFrom;
		return text.regionMatches(startFrom, tokenText, 0, tokenText.length());
	}
	
	protected final int getTokenStartPosition() {
		return startFrom;
	}
	
	public final String getTokenText() {
		return tokenText;
	}
	
}
