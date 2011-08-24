/* Generated By:JavaCC: Do not edit this line. SyntaxTreeBuilderTreeConstants.java Version 5.0 */
package org.openrdf.query.parser.sparql.ast;

public interface SyntaxTreeBuilderTreeConstants
{
  public int JJTUPDATESEQUENCE = 0;
  public int JJTUPDATECONTAINER = 1;
  public int JJTQUERYCONTAINER = 2;
  public int JJTVOID = 3;
  public int JJTBASEDECL = 4;
  public int JJTPREFIXDECL = 5;
  public int JJTSELECTQUERY = 6;
  public int JJTSELECT = 7;
  public int JJTPROJECTIONELEM = 8;
  public int JJTCONSTRUCTQUERY = 9;
  public int JJTCONSTRUCT = 10;
  public int JJTDESCRIBEQUERY = 11;
  public int JJTDESCRIBE = 12;
  public int JJTASKQUERY = 13;
  public int JJTDATASETCLAUSE = 14;
  public int JJTWHERECLAUSE = 15;
  public int JJTBINDINGSCLAUSE = 16;
  public int JJTGROUPCLAUSE = 17;
  public int JJTORDERCLAUSE = 18;
  public int JJTGROUPCONDITION = 19;
  public int JJTHAVINGCLAUSE = 20;
  public int JJTORDERCONDITION = 21;
  public int JJTLIMIT = 22;
  public int JJTOFFSET = 23;
  public int JJTGRAPHPATTERNGROUP = 24;
  public int JJTBASICGRAPHPATTERN = 25;
  public int JJTOPTIONALGRAPHPATTERN = 26;
  public int JJTGRAPHGRAPHPATTERN = 27;
  public int JJTUNIONGRAPHPATTERN = 28;
  public int JJTMINUSGRAPHPATTERN = 29;
  public int JJTSERVICEGRAPHPATTERN = 30;
  public int JJTCONSTRAINT = 31;
  public int JJTFUNCTIONCALL = 32;
  public int JJTTRIPLESSAMESUBJECT = 33;
  public int JJTPROPERTYLIST = 34;
  public int JJTOBJECTLIST = 35;
  public int JJTTRIPLESSAMESUBJECTPATH = 36;
  public int JJTPROPERTYLISTPATH = 37;
  public int JJTPATHALTERNATIVE = 38;
  public int JJTPATHSEQUENCE = 39;
  public int JJTPATHELT = 40;
  public int JJTIRI = 41;
  public int JJTPATHONEINPROPERTYSET = 42;
  public int JJTPATHMOD = 43;
  public int JJTBLANKNODEPROPERTYLIST = 44;
  public int JJTCOLLECTION = 45;
  public int JJTVAR = 46;
  public int JJTOR = 47;
  public int JJTAND = 48;
  public int JJTCOMPARE = 49;
  public int JJTMATH = 50;
  public int JJTNOT = 51;
  public int JJTNUMERICLITERAL = 52;
  public int JJTCOUNT = 53;
  public int JJTSUM = 54;
  public int JJTMIN = 55;
  public int JJTMAX = 56;
  public int JJTAVG = 57;
  public int JJTSAMPLE = 58;
  public int JJTGROUPCONCAT = 59;
  public int JJTMD5 = 60;
  public int JJTSHA1 = 61;
  public int JJTSHA224 = 62;
  public int JJTSHA256 = 63;
  public int JJTSHA384 = 64;
  public int JJTSHA512 = 65;
  public int JJTNOW = 66;
  public int JJTYEAR = 67;
  public int JJTMONTH = 68;
  public int JJTDAY = 69;
  public int JJTHOURS = 70;
  public int JJTMINUTES = 71;
  public int JJTSECONDS = 72;
  public int JJTTIMEZONE = 73;
  public int JJTTZ = 74;
  public int JJTRAND = 75;
  public int JJTABS = 76;
  public int JJTCEIL = 77;
  public int JJTFLOOR = 78;
  public int JJTROUND = 79;
  public int JJTSUBSTR = 80;
  public int JJTSTRLEN = 81;
  public int JJTUPPERCASE = 82;
  public int JJTLOWERCASE = 83;
  public int JJTSTRSTARTS = 84;
  public int JJTSTRENDS = 85;
  public int JJTCONCAT = 86;
  public int JJTCONTAINS = 87;
  public int JJTENCODEFORURI = 88;
  public int JJTIF = 89;
  public int JJTIN = 90;
  public int JJTNOTIN = 91;
  public int JJTCOALESCE = 92;
  public int JJTSTR = 93;
  public int JJTLANG = 94;
  public int JJTLANGMATCHES = 95;
  public int JJTDATATYPE = 96;
  public int JJTBOUND = 97;
  public int JJTSAMETERM = 98;
  public int JJTISIRI = 99;
  public int JJTISBLANK = 100;
  public int JJTISLITERAL = 101;
  public int JJTISNUMERIC = 102;
  public int JJTBNODEFUNC = 103;
  public int JJTIRIFUNC = 104;
  public int JJTSTRDT = 105;
  public int JJTSTRLANG = 106;
  public int JJTBIND = 107;
  public int JJTREGEXEXPRESSION = 108;
  public int JJTEXISTSFUNC = 109;
  public int JJTNOTEXISTSFUNC = 110;
  public int JJTRDFLITERAL = 111;
  public int JJTTRUE = 112;
  public int JJTFALSE = 113;
  public int JJTSTRING = 114;
  public int JJTQNAME = 115;
  public int JJTBLANKNODE = 116;
  public int JJTGRAPHREFALL = 117;
  public int JJTGRAPHORDEFAULT = 118;
  public int JJTQUADSNOTTRIPLES = 119;
  public int JJTLOAD = 120;
  public int JJTCLEAR = 121;
  public int JJTDROP = 122;
  public int JJTADD = 123;
  public int JJTMOVE = 124;
  public int JJTCOPY = 125;
  public int JJTCREATE = 126;
  public int JJTINSERTDATA = 127;
  public int JJTDELETEDATA = 128;
  public int JJTDELETEWHERE = 129;
  public int JJTDELETECLAUSE = 130;
  public int JJTINSERTCLAUSE = 131;
  public int JJTMODIFY = 132;


  public String[] jjtNodeName = {
    "UpdateSequence",
    "UpdateContainer",
    "QueryContainer",
    "void",
    "BaseDecl",
    "PrefixDecl",
    "SelectQuery",
    "Select",
    "ProjectionElem",
    "ConstructQuery",
    "Construct",
    "DescribeQuery",
    "Describe",
    "AskQuery",
    "DatasetClause",
    "WhereClause",
    "BindingsClause",
    "GroupClause",
    "OrderClause",
    "GroupCondition",
    "HavingClause",
    "OrderCondition",
    "Limit",
    "Offset",
    "GraphPatternGroup",
    "BasicGraphPattern",
    "OptionalGraphPattern",
    "GraphGraphPattern",
    "UnionGraphPattern",
    "MinusGraphPattern",
    "ServiceGraphPattern",
    "Constraint",
    "FunctionCall",
    "TriplesSameSubject",
    "PropertyList",
    "ObjectList",
    "TriplesSameSubjectPath",
    "PropertyListPath",
    "PathAlternative",
    "PathSequence",
    "PathElt",
    "IRI",
    "PathOneInPropertySet",
    "PathMod",
    "BlankNodePropertyList",
    "Collection",
    "Var",
    "Or",
    "And",
    "Compare",
    "Math",
    "Not",
    "NumericLiteral",
    "Count",
    "Sum",
    "Min",
    "Max",
    "Avg",
    "Sample",
    "GroupConcat",
    "MD5",
    "SHA1",
    "SHA224",
    "SHA256",
    "SHA384",
    "SHA512",
    "Now",
    "Year",
    "Month",
    "Day",
    "Hours",
    "Minutes",
    "Seconds",
    "Timezone",
    "Tz",
    "Rand",
    "Abs",
    "Ceil",
    "Floor",
    "Round",
    "Substr",
    "StrLen",
    "UpperCase",
    "LowerCase",
    "StrStarts",
    "StrEnds",
    "Concat",
    "Contains",
    "EncodeForURI",
    "If",
    "In",
    "NotIn",
    "Coalesce",
    "Str",
    "Lang",
    "LangMatches",
    "Datatype",
    "Bound",
    "SameTerm",
    "IsIRI",
    "IsBlank",
    "IsLiteral",
    "IsNumeric",
    "BNodeFunc",
    "IRIFunc",
    "StrDt",
    "StrLang",
    "Bind",
    "RegexExpression",
    "ExistsFunc",
    "NotExistsFunc",
    "RDFLiteral",
    "True",
    "False",
    "String",
    "QName",
    "BlankNode",
    "GraphRefAll",
    "GraphOrDefault",
    "QuadsNotTriples",
    "Load",
    "Clear",
    "Drop",
    "Add",
    "Move",
    "Copy",
    "Create",
    "InsertData",
    "DeleteData",
    "DeleteWhere",
    "DeleteClause",
    "InsertClause",
    "Modify",
  };
}
/* JavaCC - OriginalChecksum=4ba4bd0b82b45c4386736aaec1767896 (do not edit this line) */
