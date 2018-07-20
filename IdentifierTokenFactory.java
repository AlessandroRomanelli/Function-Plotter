/**
 * A TokenFactory that can recognize identifiers
 * (names of variables or functions in our language).
 */
public final class IdentifierTokenFactory extends RegExTokenFactory {
	
	public IdentifierTokenFactory() {
		// regular expression for an identifier
		super("[A-Za-z_]+[A-Za-z0-9_]*");
	}
	
	@Override
	public Token getToken(){
		return new Token( TokenType.IDENTIFIER, getTokenText(), getTokenStartPosition() );
	}

}
