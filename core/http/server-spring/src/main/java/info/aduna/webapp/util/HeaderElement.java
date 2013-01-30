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
package info.aduna.webapp.util;

import info.aduna.text.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * An element in an HTTP header value. An HTTP header element has a value and
 * zero or more parameters consisting of a key and a value. An example header
 * element is <tt>audio/*; q=0.2</tt>.
 */
public class HeaderElement {
	
	/*----------------*
	 * Static methods *
	 *----------------*/

	public static HeaderElement parse(String encodedValue) {
		HeaderElement result = new HeaderElement();

		List<String> tokens = HttpServerUtil.splitHeaderString(encodedValue, ';');

		if (!tokens.isEmpty()) {
			// First token is the value of the header element
			String token = tokens.get(0);

			// Remove any whitespace and double quotes from the token
			token = StringUtil.trimDoubleQuotes( token.trim() );
			
			result.setValue(token);
			
			// Add parameters to the header element
			for (int i = 1; i < tokens.size(); i++) {
				token = (String)tokens.get(i);
				
				int splitIdx = token.indexOf('=');
				
				if (splitIdx == -1) {
					// No value, only key
					token = StringUtil.trimDoubleQuotes( token.trim() );
					
					// Ignore empty parameters
					if (token.length() > 0) {
						result.addParameter(token);
					}
				}
				else {
					String key = token.substring(0, splitIdx).trim();
					String value = token.substring(splitIdx + 1).trim();
					value = StringUtil.trimDoubleQuotes(value);
					result.addParameter(key, value);
				}
			}
		}

		return result;
	}

	/*-----------*
	 * Variables *
	 *-----------*/
	
	private String value;
	
	private final List<Parameter> parameters;
	
	/*--------------*
	 * Constructors *
	 *--------------*/
	
	public HeaderElement() {
		this("");
	}

	public HeaderElement(String value) {
		setValue(value);
		parameters = new ArrayList<Parameter>();
	}
	
	/*---------*
	 * Methods *
	 *---------*/
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getParameterCount() {
		return parameters.size();
	}
	
	public Parameter getParameter(int i) {
		return parameters.get(i);
	}
	
	public Parameter getParameter(String key) {
		for (int i = 0; i < parameters.size(); i++) {
			Parameter param = parameters.get(i);
			if (param.getKey().equals(key)) {
				return param;
			}
		}
		
		return null;
	}
	
	public String getParameterValue(String key) {
		Parameter param = getParameter(key);
		
		if (param != null) {
			return param.getValue();
		}
		
		return null;
	}
	
	public List<Parameter> getParameters() {
		return Collections.unmodifiableList(parameters);
	}
	
	public void addParameter(String key) {
		addParameter(key, null);
	}

	public void addParameter(String key, String value) {
		addParameter( new Parameter(key, value) );
	}
	
	public void addParameter(Parameter param) {
		parameters.add(param);
	}
	
	public Parameter removeParameter(int idx) {
		return parameters.remove(idx);
	}
	
	public boolean removeParameter(Parameter param) {
		return parameters.remove(param);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof HeaderElement) {
			HeaderElement other = (HeaderElement)obj;
			
			return
				value.equals(other.getValue()) &&
				parameters.equals(other.getParameters());
		}
		
		return false;
	}
	
	public int hashCode() {
		return value.hashCode();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(32);
		sb.append(value);
		
		for (int i = 0; i < parameters.size(); i++) {
			Parameter param = parameters.get(i);

			sb.append("; ");
			sb.append(param.toString());
		}
		
		return sb.toString();
	}
}
