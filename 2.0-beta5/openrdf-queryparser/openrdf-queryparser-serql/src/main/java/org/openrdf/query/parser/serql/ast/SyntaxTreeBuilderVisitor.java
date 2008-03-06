/* Generated By:JJTree: Do not edit this line. .\SyntaxTreeBuilderVisitor.java */

package org.openrdf.query.parser.serql.ast;

public interface SyntaxTreeBuilderVisitor
{
  public Object visit(SimpleNode node, Object data) throws VisitorException;
  public Object visit(ASTQueryContainer node, Object data) throws VisitorException;
  public Object visit(ASTNamespaceDecl node, Object data) throws VisitorException;
  public Object visit(ASTTupleUnion node, Object data) throws VisitorException;
  public Object visit(ASTTupleMinus node, Object data) throws VisitorException;
  public Object visit(ASTTupleIntersect node, Object data) throws VisitorException;
  public Object visit(ASTGraphUnion node, Object data) throws VisitorException;
  public Object visit(ASTGraphMinus node, Object data) throws VisitorException;
  public Object visit(ASTGraphIntersect node, Object data) throws VisitorException;
  public Object visit(ASTSelectQuery node, Object data) throws VisitorException;
  public Object visit(ASTSelect node, Object data) throws VisitorException;
  public Object visit(ASTProjectionElem node, Object data) throws VisitorException;
  public Object visit(ASTConstructQuery node, Object data) throws VisitorException;
  public Object visit(ASTConstruct node, Object data) throws VisitorException;
  public Object visit(ASTQueryBody node, Object data) throws VisitorException;
  public Object visit(ASTFrom node, Object data) throws VisitorException;
  public Object visit(ASTWhere node, Object data) throws VisitorException;
  public Object visit(ASTLimit node, Object data) throws VisitorException;
  public Object visit(ASTOffset node, Object data) throws VisitorException;
  public Object visit(ASTBasicPathExpr node, Object data) throws VisitorException;
  public Object visit(ASTOptPathExpr node, Object data) throws VisitorException;
  public Object visit(ASTBasicPathExprTail node, Object data) throws VisitorException;
  public Object visit(ASTOptPathExprTail node, Object data) throws VisitorException;
  public Object visit(ASTEdge node, Object data) throws VisitorException;
  public Object visit(ASTNode node, Object data) throws VisitorException;
  public Object visit(ASTNodeElem node, Object data) throws VisitorException;
  public Object visit(ASTReifiedStat node, Object data) throws VisitorException;
  public Object visit(ASTOr node, Object data) throws VisitorException;
  public Object visit(ASTAnd node, Object data) throws VisitorException;
  public Object visit(ASTBooleanConstant node, Object data) throws VisitorException;
  public Object visit(ASTNot node, Object data) throws VisitorException;
  public Object visit(ASTIsResource node, Object data) throws VisitorException;
  public Object visit(ASTIsLiteral node, Object data) throws VisitorException;
  public Object visit(ASTIsURI node, Object data) throws VisitorException;
  public Object visit(ASTIsBNode node, Object data) throws VisitorException;
  public Object visit(ASTExists node, Object data) throws VisitorException;
  public Object visit(ASTCompare node, Object data) throws VisitorException;
  public Object visit(ASTCompareAny node, Object data) throws VisitorException;
  public Object visit(ASTCompareAll node, Object data) throws VisitorException;
  public Object visit(ASTLike node, Object data) throws VisitorException;
  public Object visit(ASTIn node, Object data) throws VisitorException;
  public Object visit(ASTCompOperator node, Object data) throws VisitorException;
  public Object visit(ASTVar node, Object data) throws VisitorException;
  public Object visit(ASTDatatype node, Object data) throws VisitorException;
  public Object visit(ASTLang node, Object data) throws VisitorException;
  public Object visit(ASTLabel node, Object data) throws VisitorException;
  public Object visit(ASTNamespace node, Object data) throws VisitorException;
  public Object visit(ASTLocalName node, Object data) throws VisitorException;
  public Object visit(ASTURI node, Object data) throws VisitorException;
  public Object visit(ASTQName node, Object data) throws VisitorException;
  public Object visit(ASTBNode node, Object data) throws VisitorException;
  public Object visit(ASTLiteral node, Object data) throws VisitorException;
  public Object visit(ASTString node, Object data) throws VisitorException;
  public Object visit(ASTNull node, Object data) throws VisitorException;
}
