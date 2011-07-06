/* Generated By:JavaCC: Do not edit this line. SyntaxTreeBuilderVisitor.java Version 5.0 */
package org.openrdf.query.parser.sparql.ast;

public interface SyntaxTreeBuilderVisitor
{
  public Object visit(SimpleNode node, Object data) throws VisitorException;
  public Object visit(ASTQueryContainer node, Object data) throws VisitorException;
  public Object visit(ASTBaseDecl node, Object data) throws VisitorException;
  public Object visit(ASTPrefixDecl node, Object data) throws VisitorException;
  public Object visit(ASTSelectQuery node, Object data) throws VisitorException;
  public Object visit(ASTSelect node, Object data) throws VisitorException;
  public Object visit(ASTProjectionElem node, Object data) throws VisitorException;
  public Object visit(ASTConstructQuery node, Object data) throws VisitorException;
  public Object visit(ASTConstruct node, Object data) throws VisitorException;
  public Object visit(ASTDescribeQuery node, Object data) throws VisitorException;
  public Object visit(ASTDescribe node, Object data) throws VisitorException;
  public Object visit(ASTAskQuery node, Object data) throws VisitorException;
  public Object visit(ASTDatasetClause node, Object data) throws VisitorException;
  public Object visit(ASTWhereClause node, Object data) throws VisitorException;
  public Object visit(ASTGroupClause node, Object data) throws VisitorException;
  public Object visit(ASTOrderClause node, Object data) throws VisitorException;
  public Object visit(ASTGroupCondition node, Object data) throws VisitorException;
  public Object visit(ASTHavingClause node, Object data) throws VisitorException;
  public Object visit(ASTOrderCondition node, Object data) throws VisitorException;
  public Object visit(ASTLimit node, Object data) throws VisitorException;
  public Object visit(ASTOffset node, Object data) throws VisitorException;
  public Object visit(ASTGraphPatternGroup node, Object data) throws VisitorException;
  public Object visit(ASTBasicGraphPattern node, Object data) throws VisitorException;
  public Object visit(ASTOptionalGraphPattern node, Object data) throws VisitorException;
  public Object visit(ASTGraphGraphPattern node, Object data) throws VisitorException;
  public Object visit(ASTUnionGraphPattern node, Object data) throws VisitorException;
  public Object visit(ASTMinusGraphPattern node, Object data) throws VisitorException;
  public Object visit(ASTConstraint node, Object data) throws VisitorException;
  public Object visit(ASTFunctionCall node, Object data) throws VisitorException;
  public Object visit(ASTTriplesSameSubject node, Object data) throws VisitorException;
  public Object visit(ASTPropertyList node, Object data) throws VisitorException;
  public Object visit(ASTObjectList node, Object data) throws VisitorException;
  public Object visit(ASTTriplesSameSubjectPath node, Object data) throws VisitorException;
  public Object visit(ASTPropertyListPath node, Object data) throws VisitorException;
  public Object visit(ASTPathAlternative node, Object data) throws VisitorException;
  public Object visit(ASTPathSequence node, Object data) throws VisitorException;
  public Object visit(ASTPathElt node, Object data) throws VisitorException;
  public Object visit(ASTIRI node, Object data) throws VisitorException;
  public Object visit(ASTPathOneInPropertySet node, Object data) throws VisitorException;
  public Object visit(ASTPathMod node, Object data) throws VisitorException;
  public Object visit(ASTBlankNodePropertyList node, Object data) throws VisitorException;
  public Object visit(ASTCollection node, Object data) throws VisitorException;
  public Object visit(ASTVar node, Object data) throws VisitorException;
  public Object visit(ASTOr node, Object data) throws VisitorException;
  public Object visit(ASTAnd node, Object data) throws VisitorException;
  public Object visit(ASTCompare node, Object data) throws VisitorException;
  public Object visit(ASTMath node, Object data) throws VisitorException;
  public Object visit(ASTNot node, Object data) throws VisitorException;
  public Object visit(ASTNumericLiteral node, Object data) throws VisitorException;
  public Object visit(ASTCount node, Object data) throws VisitorException;
  public Object visit(ASTSum node, Object data) throws VisitorException;
  public Object visit(ASTMin node, Object data) throws VisitorException;
  public Object visit(ASTMax node, Object data) throws VisitorException;
  public Object visit(ASTAvg node, Object data) throws VisitorException;
  public Object visit(ASTSample node, Object data) throws VisitorException;
  public Object visit(ASTGroupConcat node, Object data) throws VisitorException;
  public Object visit(ASTIf node, Object data) throws VisitorException;
  public Object visit(ASTIn node, Object data) throws VisitorException;
  public Object visit(ASTNotIn node, Object data) throws VisitorException;
  public Object visit(ASTCoalesce node, Object data) throws VisitorException;
  public Object visit(ASTStr node, Object data) throws VisitorException;
  public Object visit(ASTLang node, Object data) throws VisitorException;
  public Object visit(ASTLangMatches node, Object data) throws VisitorException;
  public Object visit(ASTDatatype node, Object data) throws VisitorException;
  public Object visit(ASTBound node, Object data) throws VisitorException;
  public Object visit(ASTSameTerm node, Object data) throws VisitorException;
  public Object visit(ASTIsIRI node, Object data) throws VisitorException;
  public Object visit(ASTIsBlank node, Object data) throws VisitorException;
  public Object visit(ASTIsLiteral node, Object data) throws VisitorException;
  public Object visit(ASTIsNumeric node, Object data) throws VisitorException;
  public Object visit(ASTBNodeFunc node, Object data) throws VisitorException;
  public Object visit(ASTIRIFunc node, Object data) throws VisitorException;
  public Object visit(ASTStrDt node, Object data) throws VisitorException;
  public Object visit(ASTStrLang node, Object data) throws VisitorException;
  public Object visit(ASTBind node, Object data) throws VisitorException;
  public Object visit(ASTRegexExpression node, Object data) throws VisitorException;
  public Object visit(ASTExistsFunc node, Object data) throws VisitorException;
  public Object visit(ASTNotExistsFunc node, Object data) throws VisitorException;
  public Object visit(ASTRDFLiteral node, Object data) throws VisitorException;
  public Object visit(ASTTrue node, Object data) throws VisitorException;
  public Object visit(ASTFalse node, Object data) throws VisitorException;
  public Object visit(ASTString node, Object data) throws VisitorException;
  public Object visit(ASTQName node, Object data) throws VisitorException;
  public Object visit(ASTBlankNode node, Object data) throws VisitorException;
  public Object visit(ASTGraphRefAll node, Object data) throws VisitorException;
  public Object visit(ASTGraphOrDefault node, Object data) throws VisitorException;
  public Object visit(ASTLoad node, Object data) throws VisitorException;
  public Object visit(ASTClear node, Object data) throws VisitorException;
  public Object visit(ASTDrop node, Object data) throws VisitorException;
  public Object visit(ASTAdd node, Object data) throws VisitorException;
  public Object visit(ASTMove node, Object data) throws VisitorException;
  public Object visit(ASTCopy node, Object data) throws VisitorException;
  public Object visit(ASTCreate node, Object data) throws VisitorException;
  public Object visit(ASTInsertData node, Object data) throws VisitorException;
  public Object visit(ASTDeleteData node, Object data) throws VisitorException;
  public Object visit(ASTDeleteWhere node, Object data) throws VisitorException;
  public Object visit(ASTDeleteClause node, Object data) throws VisitorException;
  public Object visit(ASTInsertClause node, Object data) throws VisitorException;
  public Object visit(ASTModify node, Object data) throws VisitorException;
}
/* JavaCC - OriginalChecksum=4b9d839e889a5fa09d2e688b153dba5d (do not edit this line) */
