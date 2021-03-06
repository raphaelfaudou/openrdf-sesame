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
package info.aduna.xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Utilities to make working with DOM documents easier.
 * 
 * @author Herko ter Horst
 */
public class DocumentUtil {

	/**
	 * Create a Document representing the XML file at the specified location.
	 * 
	 * @param location
	 *            the location of an XML document
	 * @return a Document representing the XML file
	 * @throws IOException
	 *             when there was a problem retrieving or parsing the document.
	 */
	public static Document getDocument(URL location) throws IOException {
		return getDocument(location, false, false, null);
	}

	/**
	 * Create a Document representing the XML file at the specified location.
	 * 
	 * @param location
	 *            the location of an XML document
	 * @param validating
	 *            whether the XML parser used in the construction of the
	 *            document should validate the XML
	 * @param namespaceAware
	 *            whether the XML parser used in the construction of the
	 *            document should be aware of namespaces
	 * @return a Document representing the XML file
	 * @throws IOException
	 *             when there was a problem retrieving or parsing the document.
	 */
	public static Document getDocument(URL location, boolean validating,
			boolean namespaceAware) throws IOException {
		return getDocument(location, validating, namespaceAware, null);
	}

	/**
	 * Create a Document representing the XML file at the specified location.
	 * 
	 * @param location
	 *            the location of an XML document
	 * @param schema
	 *            a Schama instance to validate against
	 * @return a Document representing the XML file
	 * @throws IOException
	 *             when there was a problem retrieving or parsing the document.
	 */
	public static Document getDocument(URL location, Schema schema)
			throws IOException {
		return getDocument(location, false, true, schema);
	}

	private static Document getDocument(URL location, boolean validating,
			boolean namespaceAware, Schema schema) throws IOException {
		Document result = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(validating);
		factory.setNamespaceAware(namespaceAware);
		factory.setSchema(schema);

		InputStream in = null;
		try {
			in = new BufferedInputStream(location.openConnection()
					.getInputStream());
			DocumentBuilder builder = factory.newDocumentBuilder();
			result = builder.parse(in);
		} catch (SAXParseException e) {
			String message = "Parsing error" + ", line " + e.getLineNumber()
					+ ", uri " + e.getSystemId() + ", " + e.getMessage();
			throw toIOE(message, e);
		} catch (SAXException e) {
			throw toIOE(e);
		} catch (ParserConfigurationException e) {
			throw toIOE(e);
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return result;
	}

	private static IOException toIOE(Exception e) {
		return toIOE(e.getMessage(), e);
	}

	private static IOException toIOE(String message, Exception e) {
		IOException result = new IOException(message);
		result.initCause(e);
		return result;
	}
}
