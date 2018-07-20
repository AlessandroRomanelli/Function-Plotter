/**
 * A TokenFactory that can recognize double literal values.
 */
public final class DoubleLiteralTokenFactory extends RegExTokenFactory {
	
	public DoubleLiteralTokenFactory() {
		// regular expression for an integer literal
		super("[0-9]+[.]?[0-9]*");
	}
	
	public Token getToken(){
		return new Token( TokenType.LITERAL, getTokenText(), getTokenStartPosition() );
	}

}

