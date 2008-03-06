/*
 * Copyright Aduna (http://www.aduna-software.com/) (c) 2007.
 *
 * Licensed under the Aduna BSD-style license.
 */
package org.openrdf.query.resultio;

import info.aduna.lang.service.ServiceRegistry;

/**
 * A registry that keeps track of the available
 * {@link TupleQueryResultParserFactory}s.
 * 
 * @author Arjohn Kampman
 */
public class TupleQueryResultParserRegistry extends
		ServiceRegistry<TupleQueryResultFormat, TupleQueryResultParserFactory>
{

	private static TupleQueryResultParserRegistry defaultRegistry;

	/**
	 * Gets the default TupleQueryResultParserRegistry.
	 * 
	 * @return The default registry.
	 */
	public static TupleQueryResultParserRegistry getInstance() {
		if (defaultRegistry == null) {
			defaultRegistry = new TupleQueryResultParserRegistry();
		}

		return defaultRegistry;
	}

	public TupleQueryResultParserRegistry() {
		super(TupleQueryResultParserFactory.class);
	}

	protected TupleQueryResultFormat getKey(TupleQueryResultParserFactory factory) {
		return factory.getTupleQueryResultFormat();
	}
}
