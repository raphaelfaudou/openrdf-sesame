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
package org.openrdf.sail.rdbms;

import java.sql.SQLException;

import info.aduna.concurrent.locks.Lock;
import info.aduna.concurrent.locks.WritePrefReadWriteLockManager;

import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryBase;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.sail.rdbms.exceptions.RdbmsException;
import org.openrdf.sail.rdbms.exceptions.RdbmsRuntimeException;
import org.openrdf.sail.rdbms.managers.BNodeManager;
import org.openrdf.sail.rdbms.managers.LiteralManager;
import org.openrdf.sail.rdbms.managers.PredicateManager;
import org.openrdf.sail.rdbms.managers.UriManager;
import org.openrdf.sail.rdbms.model.RdbmsBNode;
import org.openrdf.sail.rdbms.model.RdbmsLiteral;
import org.openrdf.sail.rdbms.model.RdbmsResource;
import org.openrdf.sail.rdbms.model.RdbmsStatement;
import org.openrdf.sail.rdbms.model.RdbmsURI;
import org.openrdf.sail.rdbms.model.RdbmsValue;
import org.openrdf.sail.rdbms.schema.IdSequence;
import org.openrdf.sail.rdbms.schema.LiteralTable;
import org.openrdf.sail.rdbms.schema.ValueTable;

/**
 * Provides basic value creation both for traditional values as well as values
 * with an internal id. {@link RdbmsValue}s behaviour similar to the default
 * {@link Value} implementation with the addition that they also include an
 * internal id and a version associated with that id. The internal ids should
 * not be accessed directly, but rather either through this class or the
 * corresponding manager class.
 * 
 * @author James Leigh
 * 
 */
public class RdbmsValueFactory extends ValueFactoryBase {

	@Deprecated
	public static final String NIL_LABEL = "nil";

	private ValueFactory vf;

	private BNodeManager bnodes;

	private UriManager uris;

	private LiteralManager literals;

	private PredicateManager predicates;

	private WritePrefReadWriteLockManager lock = new WritePrefReadWriteLockManager();

	private IdSequence ids;

	public void setIdSequence(IdSequence ids) {
		this.ids = ids;
	}

	public void setBNodeManager(BNodeManager bnodes) {
		this.bnodes = bnodes;
	}

	public void setURIManager(UriManager uris) {
		this.uris = uris;
	}

	public void setLiteralManager(LiteralManager literals) {
		this.literals = literals;
	}

	public void setPredicateManager(PredicateManager predicates) {
		this.predicates = predicates;
	}

	public void setDelegate(ValueFactory vf) {
		this.vf = vf;
	}

	public void flush()
		throws RdbmsException
	{
		try {
			bnodes.flush();
			uris.flush();
			literals.flush();
		}
		catch (SQLException e) {
			throw new RdbmsException(e);
		}
		catch (InterruptedException e) {
			throw new RdbmsException(e);
		}
	}

	@Override
	public RdbmsBNode createBNode(String nodeID) {
		RdbmsBNode resource = bnodes.findInCache(nodeID);
		if (resource == null) {
			try {
				BNode impl = vf.createBNode(nodeID);
				resource = new RdbmsBNode(impl);
				bnodes.cache(resource);
			}
			catch (InterruptedException e) {
				throw new RdbmsRuntimeException(e);
			}
		}
		return resource;
	}

	@Override
	public RdbmsLiteral createLiteral(String label) {
		return asRdbmsLiteral(vf.createLiteral(label, XMLSchema.STRING));
	}

	@Override
	public RdbmsLiteral createLiteral(String label, String language) {
		if (LiteralTable.ONLY_INSERT_LABEL)
			return createLiteral(label);
		return asRdbmsLiteral(vf.createLiteral(label, language));
	}

	@Override
	public RdbmsLiteral createLiteral(String label, URI datatype) {
		if (LiteralTable.ONLY_INSERT_LABEL)
			return createLiteral(label);
		return asRdbmsLiteral(vf.createLiteral(label, datatype));
	}

	@Override
	public RdbmsStatement createStatement(Resource subject, URI predicate, Value object) {
		return createStatement(subject, predicate, object, null);
	}

	@Override
	public RdbmsStatement createStatement(Resource subject, URI predicate, Value object, Resource context) {
		RdbmsResource subj = asRdbmsResource(subject);
		RdbmsURI pred = asRdbmsURI(predicate);
		RdbmsValue obj = asRdbmsValue(object);
		RdbmsResource ctx = asRdbmsResource(context);
		return new RdbmsStatement(subj, pred, obj, ctx);
	}

	@Override
	public RdbmsURI createURI(String uri) {
		RdbmsURI resource = uris.findInCache(uri);
		if (resource == null) {
			try {
				URI impl = vf.createURI(uri);
				resource = new RdbmsURI(impl);
				uris.cache(resource);
			}
			catch (InterruptedException e) {
				throw new RdbmsRuntimeException(e);
			}
		}
		return resource;
	}

	@Override
	public RdbmsURI createURI(String namespace, String localName) {
		return createURI(namespace + localName);
	}

	public RdbmsResource getRdbmsResource(Number num, String stringValue) {
		assert stringValue != null : "Null stringValue for ID: " + num;
		Number id = ids.idOf(num);
		if (ids.isURI(id))
			return new RdbmsURI(id, uris.getIdVersion(), vf.createURI(stringValue));
		return new RdbmsBNode(id, bnodes.getIdVersion(), vf.createBNode(stringValue));
	}

	public RdbmsLiteral getRdbmsLiteral(Number num, String label, String language, String datatype) {
		Number id = ids.idOf(num);
		if (datatype == null && language == null)
			return new RdbmsLiteral(id, literals.getIdVersion(), vf.createLiteral(label));
		if (datatype == null)
			return new RdbmsLiteral(id, literals.getIdVersion(), vf.createLiteral(label, language));
		return new RdbmsLiteral(id, literals.getIdVersion(), vf.createLiteral(label, vf.createURI(datatype)));
	}

	public RdbmsResource asRdbmsResource(Resource node) {
		if (node == null)
			return null;
		if (node instanceof URI)
			return asRdbmsURI((URI)node);
		if (node instanceof RdbmsBNode) {
			try {
				bnodes.cache((RdbmsBNode)node);
				return (RdbmsBNode)node;
			}
			catch (InterruptedException e) {
				throw new RdbmsRuntimeException(e);
			}
		}
		return createBNode(((BNode)node).getID());
	}

	public RdbmsURI asRdbmsURI(URI uri) {
		if (uri == null)
			return null;
		if (uri instanceof RdbmsURI) {
			try {
				uris.cache((RdbmsURI)uri);
				return (RdbmsURI)uri;
			}
			catch (InterruptedException e) {
				throw new RdbmsRuntimeException(e);
			}
		}
		return createURI(uri.stringValue());
	}

	public RdbmsValue asRdbmsValue(Value value) {
		if (value == null)
			return null;
		if (value instanceof Literal)
			return asRdbmsLiteral((Literal)value);
		return asRdbmsResource((Resource)value);
	}

	public RdbmsLiteral asRdbmsLiteral(Literal literal) {
		try {
			if (literal instanceof RdbmsLiteral) {
				literals.cache((RdbmsLiteral)literal);
				return (RdbmsLiteral)literal;
			}
			RdbmsLiteral lit = literals.findInCache(literal);
			if (lit == null) {
				lit = new RdbmsLiteral(literal);
				literals.cache(lit);
			}
			return lit;
		}
		catch (InterruptedException e) {
			throw new RdbmsRuntimeException(e);
		}
	}

	public RdbmsResource[] asRdbmsResource(Resource... contexts) {
		RdbmsResource[] ctxs = new RdbmsResource[contexts.length];
		for (int i = 0; i < ctxs.length; i++) {
			ctxs[i] = asRdbmsResource(contexts[i]);
		}
		return ctxs;
	}

	public RdbmsStatement asRdbmsStatement(Statement stmt) {
		if (stmt instanceof RdbmsStatement)
			return (RdbmsStatement)stmt;
		Resource s = stmt.getSubject();
		URI p = stmt.getPredicate();
		Value o = stmt.getObject();
		Resource c = stmt.getContext();
		return createStatement(s, p, o, c);
	}

	public Number getInternalId(Value r)
		throws RdbmsException
	{
		try {
			if (r == null)
				return ValueTable.NIL_ID;
			RdbmsValue value = asRdbmsValue(r);
			if (value instanceof RdbmsURI)
				return uris.getInternalId((RdbmsURI)value);
			if (value instanceof RdbmsBNode)
				return bnodes.getInternalId((RdbmsBNode)value);
			return literals.getInternalId((RdbmsLiteral)value);
		}
		catch (SQLException e) {
			throw new RdbmsException(e);
		}
		catch (InterruptedException e) {
			throw new RdbmsRuntimeException(e);
		}
	}

	public Number getPredicateId(RdbmsURI predicate)
		throws RdbmsException
	{
		try {
			return predicates.getIdOfPredicate(predicate);
		}
		catch (SQLException e) {
			throw new RdbmsException(e);
		}
		catch (InterruptedException e) {
			throw new RdbmsRuntimeException(e);
		}
	}

	public Lock getIdReadLock() throws InterruptedException {
		return lock.getReadLock();
	}

	public Lock tryIdWriteLock() {
		return lock.tryWriteLock();
	}

	public Lock getIdWriteLock() throws InterruptedException {
		return lock.getWriteLock();
	}
}
