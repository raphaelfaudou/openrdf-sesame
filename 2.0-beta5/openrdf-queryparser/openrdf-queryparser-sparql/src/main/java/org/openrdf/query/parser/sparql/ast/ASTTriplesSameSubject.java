/* Generated By:JJTree: Do not edit this line. ASTTriplesSameSubject.java */

package org.openrdf.query.parser.sparql.ast;

public class ASTTriplesSameSubject extends SimpleNode {

	public ASTTriplesSameSubject(int id) {
		super(id);
	}

	public ASTTriplesSameSubject(SyntaxTreeBuilder p, int id) {
		super(p, id);
	}

	/** Accept the visitor. */
	@Override
	public Object jjtAccept(SyntaxTreeBuilderVisitor visitor, Object data)
		throws VisitorException
	{
		return visitor.visit(this, data);
	}
}
