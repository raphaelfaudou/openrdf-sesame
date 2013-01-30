/* 
 * Licensed to Aduna under one or more contributor license agreements.  
 * See the NOTICE.txt file distributed with this work for additional 
 * information regarding copyright ownership. 
 *
 * Aduna licenses this file to you under the terms of the Aduna BSD 
 * License (the "License"); you may not use this file except in compliance 
 * with the License. See the LICENSE.txt file distributed with this work 
 * for the full License.
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.openrdf.query.resultio.sparqlxml;

/**
 * Interface defining tags and attribute names that are used in SPARQL Results
 * Documents. See <a href="http://www.w3.org/TR/rdf-sparql-XMLres/">SPARQL Query
 * Results XML Format</a> for the definition of this format.
 * 
 * @author Arjohn Kampman
 */
interface SPARQLResultsXMLConstants {

	public static final String NAMESPACE = "http://www.w3.org/2005/sparql-results#";

	public static final String ROOT_TAG = "sparql";

	public static final String HEAD_TAG = "head";

	public static final String VAR_TAG = "variable";

	public static final String VAR_NAME_ATT = "name";

	public static final String BOOLEAN_TAG = "boolean";

	public static final String BOOLEAN_TRUE = "true";

	public static final String BOOLEAN_FALSE = "false";

	public static final String RESULT_SET_TAG = "results";

	public static final String RESULT_TAG = "result";

	public static final String BINDING_TAG = "binding";

	public static final String BINDING_NAME_ATT = "name";

	public static final String URI_TAG = "uri";

	public static final String BNODE_TAG = "bnode";

	public static final String LITERAL_TAG = "literal";

	public static final String LITERAL_LANG_ATT = "xml:lang";

	public static final String LITERAL_DATATYPE_ATT = "datatype";

	public static final String UNBOUND_TAG = "unbound";
}
