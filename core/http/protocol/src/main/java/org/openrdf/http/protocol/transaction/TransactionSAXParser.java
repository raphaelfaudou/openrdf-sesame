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
package org.openrdf.http.protocol.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import info.aduna.xml.SimpleSAXAdapter;

import org.openrdf.http.protocol.transaction.operations.AddStatementOperation;
import org.openrdf.http.protocol.transaction.operations.ClearNamespacesOperation;
import org.openrdf.http.protocol.transaction.operations.ClearOperation;
import org.openrdf.http.protocol.transaction.operations.RemoveNamespaceOperation;
import org.openrdf.http.protocol.transaction.operations.RemoveStatementsOperation;
import org.openrdf.http.protocol.transaction.operations.SPARQLUpdateOperation;
import org.openrdf.http.protocol.transaction.operations.SetNamespaceOperation;
import org.openrdf.http.protocol.transaction.operations.TransactionOperation;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.query.impl.DatasetImpl;

/**
 * Parses an RDF transaction document into a collection of
 * {@link TransactionOperation} objects.
 * 
 * @author Arjohn Kampman
 * @author Leo Sauermann
 */
class TransactionSAXParser extends SimpleSAXAdapter {

	private ValueFactory valueFactory;

	private List<TransactionOperation> txn;

	private List<Value> parsedValues = new ArrayList<Value>();

	private SPARQLUpdateOperation currentSPARQLUpdate = null;

	private DatasetImpl currentDataset;

	public TransactionSAXParser() {
		this(new ValueFactoryImpl());
	}

	public TransactionSAXParser(ValueFactory valueFactory) {
		this.valueFactory = valueFactory;
	}

	/**
	 * get the parsed transaction
	 * 
	 * @return the parsed transaction
	 */
	public Collection<TransactionOperation> getTxn() {
		return txn;
	}

	@Override
	public void startDocument()
		throws SAXException
	{
		txn = new ArrayList<TransactionOperation>();
	}

	@Override
	public void startTag(String tagName, Map<String, String> atts, String text)
		throws SAXException
	{
		if (TransactionXMLConstants.URI_TAG.equals(tagName)) {
			parsedValues.add(valueFactory.createURI(text));
		}
		else if (TransactionXMLConstants.BNODE_TAG.equals(tagName)) {
			parsedValues.add(valueFactory.createBNode(text));
		}
		else if (TransactionXMLConstants.LITERAL_TAG.equals(tagName)) {
			String lang = atts.get(TransactionXMLConstants.LANG_ATT);
			String datatype = atts.get(TransactionXMLConstants.DATATYPE_ATT);
			String encoding = atts.get(TransactionXMLConstants.ENCODING_ATT);

			if (encoding != null && "base64".equalsIgnoreCase(encoding)) {
				text = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(text));
			}
			Literal lit;
			if (lang != null) {
				lit = valueFactory.createLiteral(text, lang);
			}
			else if (datatype != null) {
				URI dtURI = valueFactory.createURI(datatype);
				lit = valueFactory.createLiteral(text, dtURI);
			}
			else {
				lit = valueFactory.createLiteral(text);
			}

			parsedValues.add(lit);
		}
		else if (TransactionXMLConstants.NULL_TAG.equals(tagName)) {
			parsedValues.add(null);
		}
		else if (TransactionXMLConstants.SET_NAMESPACE_TAG.equals(tagName)) {
			String prefix = atts.get(TransactionXMLConstants.PREFIX_ATT);
			String name = atts.get(TransactionXMLConstants.NAME_ATT);
			txn.add(new SetNamespaceOperation(prefix, name));
		}
		else if (TransactionXMLConstants.REMOVE_NAMESPACE_TAG.equals(tagName)) {
			String prefix = atts.get(TransactionXMLConstants.PREFIX_ATT);
			txn.add(new RemoveNamespaceOperation(prefix));
		}
		else if (TransactionXMLConstants.CLEAR_NAMESPACES_TAG.equals(tagName)) {
			txn.add(new ClearNamespacesOperation());
		}
		else if (TransactionXMLConstants.SPARQL_UPDATE_TAG.equals(tagName)) {
			if (currentSPARQLUpdate != null) {
				throw new SAXException("unexpected start of SPARQL Update operation");
			}
			currentSPARQLUpdate = new SPARQLUpdateOperation();

			String baseURI = atts.get(TransactionXMLConstants.BASE_URI_ATT);
			boolean includeInferred = Boolean.parseBoolean(atts.get(TransactionXMLConstants.INCLUDE_INFERRED_ATT));

			currentSPARQLUpdate.setIncludeInferred(includeInferred);
			currentSPARQLUpdate.setBaseURI(baseURI);
		}
		else if (TransactionXMLConstants.UPDATE_STRING_TAG.equals(tagName)) {
			currentSPARQLUpdate.setUpdateString(text);
		}
		else if (TransactionXMLConstants.DATASET_TAG.equals(tagName)) {
			currentDataset = new DatasetImpl();
		}
		else if (TransactionXMLConstants.DEFAULT_INSERT_GRAPH.equals(tagName)) {
			currentDataset.setDefaultInsertGraph(valueFactory.createURI(text));
		}
		else if (TransactionXMLConstants.GRAPH_TAG.equals(tagName)) {
			parsedValues.add(valueFactory.createURI(text));
		}
	}

	@Override
	public void endTag(String tagName)
		throws SAXException
	{
		if (TransactionXMLConstants.ADD_STATEMENT_TAG.equals(tagName)) {
			txn.add(createAddStatementOperation());
		}
		else if (TransactionXMLConstants.REMOVE_STATEMENTS_TAG.equals(tagName)) {
			txn.add(createRemoveStatementsOperation());
		}
		else if (TransactionXMLConstants.CLEAR_TAG.equals(tagName)) {
			txn.add(createClearOperation());
		}
		else if (TransactionXMLConstants.SPARQL_UPDATE_TAG.equals(tagName)) {
			txn.add(currentSPARQLUpdate);
			currentSPARQLUpdate = null;
		}
		else if (TransactionXMLConstants.DEFAULT_GRAPHS_TAG.equals(tagName)) {
			for (Value parsedValue : parsedValues) {
				try {
					currentDataset.addDefaultGraph((URI)parsedValue);
				}
				catch (ClassCastException e) {
					throw new SAXException("unexpected value in default graph list: " + parsedValue);
				}
			}
			parsedValues.clear();
		}
		else if (TransactionXMLConstants.NAMED_GRAPHS_TAG.equals(tagName)) {
			for (Value parsedValue : parsedValues) {
				try {
					currentDataset.addNamedGraph((URI)parsedValue);
				}
				catch (ClassCastException e) {
					throw new SAXException("unexpected value in named graph list: " + parsedValue);
				}
			}
			parsedValues.clear();
		}
		else if (TransactionXMLConstants.DEFAULT_REMOVE_GRAPHS_TAG.equals(tagName)) {
			for (Value parsedValue : parsedValues) {
				try {
					currentDataset.addDefaultRemoveGraph((URI)parsedValue);
				}
				catch (ClassCastException e) {
					throw new SAXException("unexpected value in default remove graph list: " + parsedValue);
				}
			}
			parsedValues.clear();
		}
		else if (TransactionXMLConstants.DATASET_TAG.equals(tagName)) {
			currentSPARQLUpdate.setDataset(currentDataset);
			currentDataset = null;
		}
	}

	private TransactionOperation createClearOperation()
		throws SAXException
	{
		Resource[] contexts = createContexts(0);
		parsedValues.clear();

		return new ClearOperation(contexts);
	}

	private TransactionOperation createAddStatementOperation()
		throws SAXException
	{
		if (parsedValues.size() < 3) {
			throw new SAXException("At least three values required for AddStatementOperation, found: "
					+ parsedValues.size());
		}

		try {
			Resource subject = (Resource)parsedValues.get(0);
			URI predicate = (URI)parsedValues.get(1);
			Value object = parsedValues.get(2);
			Resource[] contexts = createContexts(3);

			parsedValues.clear();

			if (subject == null || predicate == null || object == null) {
				throw new SAXException(
						"Subject, predicate and object cannot be null for an AddStatementOperation");
			}
			return new AddStatementOperation(subject, predicate, object, contexts);
		}
		catch (ClassCastException e) {
			throw new SAXException("Invalid argument(s) for AddStatementOperation", e);
		}
	}

	private TransactionOperation createRemoveStatementsOperation()
		throws SAXException
	{
		if (parsedValues.size() < 3) {
			throw new SAXException("At least three values required for RemoveStatementsOperation, found: "
					+ parsedValues.size());
		}

		try {
			Resource subject = (Resource)parsedValues.get(0);
			URI predicate = (URI)parsedValues.get(1);
			Value object = parsedValues.get(2);
			Resource[] contexts = createContexts(3);

			parsedValues.clear();

			return new RemoveStatementsOperation(subject, predicate, object, contexts);
		}
		catch (ClassCastException e) {
			throw new SAXException("Invalid argument(s) for RemoveStatementsOperation", e);
		}
	}

	private Resource[] createContexts(int startIdx)
		throws SAXException
	{
		List<Resource> contexts = new ArrayList<Resource>();

		for (int i = startIdx; i < parsedValues.size(); i++) {
			Value contextCandidate = parsedValues.get(i);

			if (contextCandidate == null || contextCandidate instanceof Resource) {
				contexts.add((Resource)contextCandidate);
			}
			else {
				throw new SAXException("Invalid context value: " + contextCandidate.getClass());
			}
		}

		return contexts.toArray(new Resource[contexts.size()]);
	}
}
