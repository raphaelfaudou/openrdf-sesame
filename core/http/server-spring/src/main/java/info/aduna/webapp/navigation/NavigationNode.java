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
package info.aduna.webapp.navigation;

/**
 * NavigationNode represents a node in a NavigationModel.
 * 
 * @author Herko ter Horst
 */
public interface NavigationNode extends Cloneable {

	/**
	 * Get the ID of the node.
	 * 
	 * @return the ID of the node
	 */
	public String getId();

	/**
	 * Is the node hidden?
	 * 
	 * @return true if the node is hidden, false otherwise
	 */
	public boolean isHidden();

	/**
	 * Set the hidden status of the node.
	 * 
	 * @param hidden
	 *            the new hidden status of the node
	 */
	public void setHidden(boolean hidden);

	/**
	 * Is the node enabled/active?
	 * 
	 * @return true if the node is enabled, false otherwise
	 */
	public boolean isEnabled();

	/**
	 * Set the enabled status of the node.
	 * 
	 * @param enabled
	 *            the new enabled status of the node
	 */
	public void setEnabled(boolean enabled);

	/**
	 * Get the parent node of this node.
	 * 
	 * @return the parent node of this node, or null if this node is the root
	 *         NavigationModel
	 */
	public NavigationNode getParent();

	/**
	 * Set the parent of this node.
	 * 
	 * @param parent
	 *            the new parent of this node
	 */
	public void setParent(NavigationNode parent);

	/**
	 * Is this node a parent of the specified node?
	 * 
	 * @param node
	 *            the node to check
	 * @return true if this node is a direct or indirect parent of the specified
	 *         node, false otherwise
	 */
	public boolean isParent(NavigationNode node);

	/**
	 * Get the depth of this node in the hierarchy. The root NavigationModel has
	 * depth 0, all other nodes have a depth equal to the depth of their parent +
	 * 1.
	 * 
	 * @return the depth of the node in the hierarhcy
	 */
	public int getDepth();

	public String getPathPrefix();
	
	public String getPathSeparator();
	
	public String getPath();

	public void setPath(String path);

	public String getIconPrefix();

	public String getIconSeparator();
	
	public String getIconSuffix();

	public String getIcon();

	public void setIcon(String icon);

	public String getI18nPrefix();

	public String getI18nSeparator();
	
	public String getI18nSuffix();

	public String getI18n();

	public void setI18n(String i18n);
	
	public String getViewSuffix();
	
	public void setViewSuffix(String suffix);
}