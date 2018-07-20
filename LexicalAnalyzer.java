/**
 * A LexicalAnalyzer breaks a String into Tokens.
 * This class supports looking ahead by one Token
 * (that Token can be put back with putBackToken()):
 * 
 * <pre>
 * lexer.fetchNextToken();
 * Token t1 = lexer.getCurrentToken();
 * // look ahead at t2, then put it back: 
 * lexer.fetchNextToken();
 * Token t2 = lexer.getCurrentToken();
 * lexer.putBackToken();
 * </pre> 
 */
public final class LexicalAnalyzer {

    private Token token;
    private Token previousToken;
    private boolean lookingAhead;
    private String text;
    private int position;
    private final TokenFactory[] tokenFactories;

    public LexicalAnalyzer() {
        this("");
    }

    public LexicalAnalyzer(final String expression) {
        tokenFactories = new TokenFactory[] {
            new IdentifierTokenFactory(),
            new DoubleLiteralTokenFactory(),
            new OperatorTokenFactory("+", TokenType.PLUS),
            new OperatorTokenFactory("-", TokenType.MINUS),
            new OperatorTokenFactory("*", TokenType.STAR),
            new OperatorTokenFactory("/", TokenType.SLASH),
            new OperatorTokenFactory("%", TokenType.PERCENT),
            new OperatorTokenFactory("(", TokenType.OPEN_PAREN),
            new OperatorTokenFactory(")", TokenType.CLOSED_PAREN),
            new OperatorTokenFactory("^", TokenType.EXP),
            new OperatorTokenFactory(" ", TokenType.BLANK),
        };
        setText(expression);
    }

    public void setText(final String expression) {
        token = null;
        previousToken = null;
        lookingAhead = false;
        text = expression;
        position = 0;
        for (final TokenFactory factory : tokenFactories) {
            factory.setText(expression);
        }
    }

    public void fetchNextToken() {
        if (lookingAhead) {     
            lookingAhead = false;
            //System.out.println("-- (recovered from putback) current="+token.getLongDescription());
            return;
        } else {
            previousToken = token;
            token = scanToken();
            //System.out.println("-- (fetched new) current="+token.getLongDescription());
            if (token.getType() == TokenType.BLANK) {
                fetchNextToken();
            }
        }
    }

    private Token scanToken() {
        if (position==text.length()) {
            return new Token(TokenType.END_OF_FILE, "", position);
        } else {
            int maxLength = 0;
            TokenFactory factoryWithLongestMatch = null;
            for (final TokenFactory factory : tokenFactories) {
                if (factory.find(position)) {
                    if (factory.getTokenLength()>maxLength) {
                        maxLength = factory.getTokenLength();
                        factoryWithLongestMatch = factory;
                    }
                }
            }
            if (factoryWithLongestMatch == null) {
                // ERROR: found no token!
                return null;
            } else {
                position+=factoryWithLongestMatch.getTokenLength();
                return factoryWithLongestMatch.getToken();
            }
        }
    }

    public void putBackToken() {
        if (lookingAhead) {
            // ERROR: cannot put back twice
        } else if (previousToken==null) {
            // ERROR: no token to put back
        }
        //System.out.println("lexer.putBack() --- current="+previousToken.getLongDescription());
        lookingAhead = true;
    }

    public Token getCurrentToken() {
        if (lookingAhead) {
            return previousToken;
        } else {
            return token;
        }
    }

}
