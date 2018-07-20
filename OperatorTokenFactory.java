/**
 * A TokenFactory useful for recognizing operators (such as +).
 */
public final class OperatorTokenFactory extends StringTokenFactory {
	
	private final TokenType tokenType;
	
	
	public OperatorTokenFactory(final String operator, final TokenType tokenType) {
		super(operator);
		this.tokenType = tokenType;
	}
	
	public Token getToken() {
	    return new Token(tokenType, getTokenText(), getTokenStartPosition());
	}
	
}
