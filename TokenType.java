/**
 * An enum representing the different kinds of Tokens
 * in our expression language.
 */
public enum TokenType {
	
	IDENTIFIER("identifier"), 
	
	LITERAL("literal"), 
	
	PLUS("plus"), 
	MINUS("minus"), 
	STAR("star"), 
	SLASH("slash"), 
	PERCENT("percent"), 
	OPEN_PAREN("open parenthesis"), 
	CLOSED_PAREN("closed parenthesis"),
	EXP("exponential"),
	SUB("subscript"),
	BLANK("whitespace"),
	
	END_OF_FILE("end of file"); 
	
	
	
	private final String name;
	
	
	private TokenType(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
