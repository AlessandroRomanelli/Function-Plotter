/**
 * A Token produced by the LexicalAnalyzer.
 */
public final class Token {

    private final TokenType type;
	private final String text;
	private final int startPosition;
	
	
	public Token(final TokenType type, final String text, final int startPosition) {
		this.type = type;
		this.text = text;
		this.startPosition = startPosition;
	}
	
	public final TokenType getType() {
		return type;
	}
	
	public final String getText() {
		return text;
	}
	
	public final int getStartPosition() {
		return startPosition;
	}
	
	public final int getEndPosition() {
		return startPosition+text.length();
	}
    
}
