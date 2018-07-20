/**
 * A Parser for a simple expression language.
 * 
 * <code>
 * EXPRESSION   ::= [ "+" | "-" ] TERM { [ "+" | "-" ] TERM }
 * TERM         ::= FACTOR { [ "*" | "/" | "%" | "^" ] FACTOR }
 * FACTOR       ::= LITERAL | 
 *                  IDENTIFIER | 
 *                  IDENTIFIER"("EXPRESSION")" | 
 *                  "(" EXPRESSION ")"
 * </code>
 */
public final class Parser {

    private final LexicalAnalyzer lexer;

    public Parser(final LexicalAnalyzer lexer) {
        this.lexer = lexer;
    }

    public Node parse() {
        // fetch first token
        lexer.fetchNextToken();
        // parse the expression
        final Node expression = parseExpression();
        // if there are tokens left, throw exception
        if (lexer.getCurrentToken().getType() != TokenType.END_OF_FILE) {
            // ERROR
        }
        return expression;
    }

    /**
     * EBNF: EXPRESSION ::= [ "+" | "-" ] TERM { [ "+" | "-" ] TERM }
     * @returns a Node representing the expression
     */
    private Node parseExpression() {
        final Token startToken = lexer.getCurrentToken();
        Node expression = null;
        // process the first (optional, unary) operator and the first (mandatory) term
        if (startToken.getType() == TokenType.MINUS) {
            // consume the '-'
            lexer.fetchNextToken();
            final Node term = parseTerm();
            // expression = new NegationNode(startToken.getStartPosition(), lexer.getCurrentToken().getStartPosition(), term);
            expression = new Negation(term);
        } else if (startToken.getType() == TokenType.PLUS) {
            // consume (throw away) the '+'
            lexer.fetchNextToken();
            expression = parseTerm();
        } else {
            expression = parseTerm();           
        }
        // process next operators and terms
        while (lexer.getCurrentToken().getType()==TokenType.PLUS || lexer.getCurrentToken().getType()==TokenType.MINUS) {
            if (lexer.getCurrentToken().getType()==TokenType.PLUS) {
                lexer.fetchNextToken();
                final Node term = parseTerm();
                //expression = new AdditionNode(startToken.getStartPosition(), lexer.getCurrentToken().getStartPosition(), expression, term);
                expression = new Addition(expression, term);
            } else {
                lexer.fetchNextToken();
                final Node term = parseTerm();
                //expression = new SubtractionNode(startToken.getStartPosition(), lexer.getCurrentToken().getStartPosition(), expression, term);
                expression = new Subtraction(expression, term);
            }
        }
        return expression;
    }

    /**
     * EBNF: TERM ::= FACTOR { [ "*" | "/" | "%" | "^" ] FACTOR }
     * @returns a Node representing the term
     */
    private Node parseTerm() {
        final Token startToken = lexer.getCurrentToken();
        // process first factor
        Node term = parseFactor();
        // process next operators and factors
        while (lexer.getCurrentToken().getType() == TokenType.STAR ||
        lexer.getCurrentToken().getType() == TokenType.SLASH ||
        lexer.getCurrentToken().getType() == TokenType.PERCENT || 
        lexer.getCurrentToken().getType() == TokenType.EXP) {

            if (lexer.getCurrentToken().getType()==TokenType.STAR) {
                // consume operator
                lexer.fetchNextToken();
                // process next factor
                final Node factor = parseFactor();
                term = new Multiplication(term, factor);
            } else if (lexer.getCurrentToken().getType()==TokenType.SLASH) {
                // consume operator
                lexer.fetchNextToken();
                // process next factor
                final Node factor = parseFactor();
                term = new Division(term, factor);
            } else if (lexer.getCurrentToken().getType()==TokenType.EXP) {
                lexer.fetchNextToken();
                final Node factor = parseFactor();
                term = new Power(term, factor);
            } else {
                // consume operator
                lexer.fetchNextToken();
                // process next factor
                final Node factor = parseFactor();
                term = new Remainder(term, factor);
            }
        }

        return term;
    }

    /**
     * EBNF: FACTOR ::=  
     *          LITERAL | 
     *          IDENTIFIER | 
     *          IDENTIFIER"(" EXPRESSION ")" |
     *          "(" EXPRESSION ")"
     * @returns a Node representing the factor
     */
    private Node parseFactor() {
        //System.out.println("parseFactor");
        final Token startToken = lexer.getCurrentToken();
        switch (startToken.getType()) {
            case LITERAL: {
                //final Node factor = new LiteralNode(startToken.getStartPosition(), startToken.getEndPosition(), Integer.parseInt(startToken.getText()));
                final Node factor = new Literal( Double.parseDouble(startToken.getText()) );
                // consume number
                lexer.fetchNextToken();
                return factor;
            }
            case IDENTIFIER: {
                int operationId = -1;
                switch (startToken.getText()) {
                    case "sin": operationId = 0; break;
                    case "cos": operationId = 1; break;
                    case "ln": operationId = 2; break;
                    case "sqrt": operationId = 3; break;
                    case "abs" : operationId = 4; break;
                    case "tan" : operationId = 5; break;
                }
                lexer.fetchNextToken();
                if (lexer.getCurrentToken().getType() == TokenType.OPEN_PAREN) {
                    // consume '('
                    lexer.fetchNextToken();
                    Node factor = null;
                    switch (operationId) {
                        case 0: factor = new SinFunction( parseExpression() ); break;
                        case 1: factor = new CosFunction( parseExpression() ); break;
                        case 2: factor = new LnFunction( parseExpression() ); break;
                        case 3: factor = new RootFunction( parseExpression() ); break;
                        case 4: factor = new Absolute( parseExpression() ); break;
                        case 5: factor = new TanFunction( parseExpression() ); break;
                    }
                    // consume ')'
                    lexer.fetchNextToken();                
                    return factor;
                } else {
                    //final Node factor = new VariableNode(startToken.getStartPosition(), startToken.getEndPosition(), startToken.getText());
                    final Node factor = new Variable( startToken.getText() );
                    return factor;
                }
            }
            case OPEN_PAREN: {
                // consume '('
                lexer.fetchNextToken();     
                final Node factor =  parseExpression();
                if (lexer.getCurrentToken().getType() != TokenType.CLOSED_PAREN) {
                    // ERROR
                }
                // consume ')'
                lexer.fetchNextToken();
                return factor;
            }

            default: 
            // Error 
            return null;
        }
    }

}
