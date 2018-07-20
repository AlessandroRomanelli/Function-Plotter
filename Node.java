/**
 * A Node in an abstract syntax tree (AST)
 * for a simple expression language.
 * It does not support types other than doubles.
 */
public abstract class Node {

    /**
     * Compile this AST into a bytecode program:
     * Append instructions to the given Program.
     * @param program The program to which the generated instruction will be
     * appended to.
     */
    public abstract void generateCode(final Program program);

    /**
     * Converts this AST into an optimized AST
     * @param program The program to needs the new instructions being appened.
     * @returns A new, optimized AST.
     */
    public abstract Node optimize(final Program program);

    /**
     * Generate a string-representation of the subtree.
     * When you implement this method in subclasses,
     * where possible use recursive calls to left.toString() and
     * to right.toString() to do this.
     * @return A string which represents the subtree.
     */
    public abstract String toString();

    /**
     * Checks whether the current AST is a constant operations' tree, without
     * variables.
     * @return A boolean to determine whether the AST is constant.
     */
    public abstract boolean isConstant();
    
    /**
     * Converts this AST into the correspctive derivative AST.
     * @return a new AST that represents the derivative.
     */
    public abstract Node derivative();
    
    /**
     * A method to check whether two ASTs are the same.
     * @return A boolean to represent whether the two AST are equal.
     */
    public boolean isEqual(final Node that) {
        return this.toString().equals(that.toString());
    }

}
