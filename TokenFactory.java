/**
 * A TokenFactory can produce a certain kind of Tokens.
 * The LexicalAnalyzer uses an entire set of TokenFactories.
 */
public interface TokenFactory {

    /**
     * Set the text in which this factory should find tokens.
     */
    public void setText(final String text);
    
    /**
     * Determine whether there is a token that starts at startFrom in the text.
     */
    public boolean find(int startFrom);
    
    /**
     * If find() was successful, return the length of the token we found.
     */
    public int getTokenLength();
    
    /**
     * If find() was successful, produce and return a Token object representing the token we found.
     */
    public Token getToken();
    
}
