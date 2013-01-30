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

import info.aduna.xml.DocumentUtil;

import java.io.IOException;
import java.net.URL;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XPath-based parser for NavigationModel configuration files.
 * 
 * @author Herko ter Horst
 */
public class NavigationXmlParser {

	private XPath xpath = XPathFactory.newInstance().newXPath();

	public NavigationModel parse(URL navigationXml) {
		NavigationModel result = new NavigationModel();
		parseInto(result, navigationXml);
		return result;
	}

	public void parseInto(NavigationModel result, URL navigationXml) {
		try {
			Document document = DocumentUtil.getDocument(navigationXml);
			Node rootNode = (Node) xpath.evaluate("/navigation", document,
					XPathConstants.NODE);
			fillModel(result, rootNode);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

	private void fillModel(NavigationModel result, Node modelNode)
			throws XPathExpressionException {
		String id = xpath.evaluate("@id", modelNode);
		result.setId(id);

		String pathPrefix = xpath.evaluate("path-prefix", modelNode);
		if (!"".equals(pathPrefix)) {
			result.setPathPrefix(pathPrefix);
		}
		String pathSeparator = xpath.evaluate("path-separator", modelNode);
		if (!"".equals(pathSeparator)) {
			result.setPathSeparator(pathSeparator);
		}

		String iconPrefix = xpath.evaluate("icon-prefix", modelNode);
		if (!"".equals(iconPrefix)) {
			result.setIconPrefix(iconPrefix);
		}
		String iconSeparator = xpath.evaluate("icon-separator", modelNode);
		if (!"".equals(iconSeparator)) {
			result.setIconSeparator(iconSeparator);
		}
		String iconSuffix = xpath.evaluate("icon-suffix", modelNode);
		if (!"".equals(iconSuffix)) {
			result.setIconSuffix(iconSuffix);
		}

		String i18nPrefix = xpath.evaluate("i18n-prefix", modelNode);
		if (!"".equals(i18nPrefix)) {
			result.setI18nPrefix(i18nPrefix);
		}
		String i18nSeparator = xpath.evaluate("i18n-separator", modelNode);
		if (!"".equals(i18nSeparator)) {
			result.setI18nSeparator(i18nSeparator);
		}
		String i18nSuffix = xpath.evaluate("i18n-suffix", modelNode);
		if (!"".equals(i18nSuffix)) {
			result.setI18nSuffix(i18nSuffix);
		}

		setAttributes(result, modelNode);

		setGroupsAndViews(result, modelNode);
	}

	private void setAttributes(NavigationNode navNode, Node xmlNode)
			throws XPathExpressionException {
		boolean hidden = getBooleanAttribute(
				xpath.evaluate("@hidden", xmlNode), false);
		navNode.setHidden(hidden);

		boolean enabled = getBooleanAttribute(xpath.evaluate("@enabled",
				xmlNode), true);
		navNode.setEnabled(enabled);

		String path = xpath.evaluate("path", xmlNode);
		if (!"".equals(path)) {
			navNode.setPath(path);
		}

		String icon = xpath.evaluate("icon", xmlNode);
		if (!"".equals(icon)) {
			navNode.setIcon(icon);
		}

		String i18n = xpath.evaluate("i18n", xmlNode);
		if (!"".equals(i18n)) {
			navNode.setI18n(i18n);
		}

		String viewSuffix = xpath.evaluate("view-suffix", xmlNode);
		if (!"".equals(viewSuffix)) {
			navNode.setViewSuffix(viewSuffix);
		}
	}

	private void setGroupsAndViews(Group parent, Node xmlNode)
			throws XPathExpressionException {
		NodeList groupList = (NodeList) xpath.evaluate("group", xmlNode,
				XPathConstants.NODESET);
		int groupCount = groupList.getLength();
		for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
			Node groupNode = groupList.item(groupIndex);

			Group group = new Group(xpath.evaluate("@id", groupNode));
			parent.addGroup(group);
			setAttributes(group, groupNode);
			setGroupsAndViews(group, groupNode);
		}

		NodeList viewList = (NodeList) xpath.evaluate("view", xmlNode,
				XPathConstants.NODESET);
		int viewCount = viewList.getLength();
		for (int viewIndex = 0; viewIndex < viewCount; viewIndex++) {
			Node viewNode = viewList.item(viewIndex);

			View view = new View(xpath.evaluate("@id", viewNode));
			parent.addView(view);
			setAttributes(view, viewNode);
		}
	}

	private boolean getBooleanAttribute(String attrValue, boolean defaultValue) {
		boolean result = defaultValue;
		if (attrValue != null && !attrValue.trim().equals("")) {
			result = attrValue.equalsIgnoreCase("true")
					|| attrValue.equalsIgnoreCase("yes")
					|| attrValue.equalsIgnoreCase("on");
		}
		return result;
	}
}
