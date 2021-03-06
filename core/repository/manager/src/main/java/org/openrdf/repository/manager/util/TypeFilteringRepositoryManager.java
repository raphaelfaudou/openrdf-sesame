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
package org.openrdf.repository.manager.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.client.HttpClient;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.RepositoryInfo;
import org.openrdf.repository.manager.RepositoryManager;
import org.openrdf.repository.manager.SystemRepository;

/**
 * @author Herko ter Horst
 * @author Arjohn Kampman
 */
public class TypeFilteringRepositoryManager extends RepositoryManager {

	private final String type;

	private final RepositoryManager delegate;

	public TypeFilteringRepositoryManager(String type, RepositoryManager delegate) {
		assert type != null : "type must not be null";
		assert delegate != null : "delegate must not be null";

		this.type = type;
		this.delegate = delegate;
	}

	/**
	 * @return
	 * @see org.openrdf.repository.manager.RepositoryManager#getHttpClient()
	 */
	public HttpClient getHttpClient() {
		return delegate.getHttpClient();
	}

	/**
	 * @param httpClient
	 * @see org.openrdf.repository.manager.RepositoryManager#setHttpClient(org.apache.http.client.HttpClient)
	 */
	public void setHttpClient(HttpClient httpClient) {
		delegate.setHttpClient(httpClient);
	}

	@Override
	public void initialize()
		throws RepositoryException
	{
		delegate.initialize();
	}

	@Override
	public URL getLocation() throws MalformedURLException {
		return delegate.getLocation();
	}

	@Override
	protected Repository createSystemRepository()
		throws RepositoryException
	{
		throw new UnsupportedOperationException(
				"The system repository cannot be created through this wrapper. This method should not have been called, the delegate should take care of it.");
	}

	@Override
	public Repository getSystemRepository() {
		return delegate.getSystemRepository();
	}

	@Override
	public String getNewRepositoryID(String baseName)
		throws RepositoryException, RepositoryConfigException
	{
		return delegate.getNewRepositoryID(baseName);
	}

	@Override
	public Set<String> getRepositoryIDs()
		throws RepositoryException
	{
		Set<String> result = new LinkedHashSet<String>();

		for (String id : delegate.getRepositoryIDs()) {
			try {
				if (isCorrectType(id)) {
					result.add(id);
				}
			}
			catch (RepositoryConfigException e) {
				throw new RepositoryException(e);
			}
		}

		return result;
	}

	@Override
	public boolean hasRepositoryConfig(String repositoryID)
		throws RepositoryException, RepositoryConfigException
	{
		boolean result = false;

		if (isCorrectType(repositoryID)) {
			result = delegate.hasRepositoryConfig(repositoryID);
		}

		return result;
	}

	@Override
	public RepositoryConfig getRepositoryConfig(String repositoryID)
		throws RepositoryConfigException, RepositoryException
	{
		RepositoryConfig result = delegate.getRepositoryConfig(repositoryID);

		if (result != null) {
			if (!isCorrectType(result)) {
				logger.debug(
						"Surpressing retrieval of repository {}: repository type {} did not match expected type {}",
						new Object[] { result.getID(), result.getRepositoryImplConfig().getType(), type });

				result = null;
			}
		}

		return result;
	}

	@Override
	public void addRepositoryConfig(RepositoryConfig config)
		throws RepositoryException, RepositoryConfigException
	{
		if (isCorrectType(config)) {
			delegate.addRepositoryConfig(config);
		}
		else {
			throw new UnsupportedOperationException("Only repositories of type " + type
					+ " can be added to this manager.");
		}
	}

	@Override
	@Deprecated
	public boolean removeRepositoryConfig(String repositoryID)
		throws RepositoryException, RepositoryConfigException
	{
		boolean result = false;

		if (isCorrectType(repositoryID)) {
			result = delegate.removeRepositoryConfig(repositoryID);
		}

		return result;
	}

	@Override
	public Repository getRepository(String id)
		throws RepositoryConfigException, RepositoryException
	{
		Repository result = null;

		if (isCorrectType(id)) {
			result = delegate.getRepository(id);
		}

		return result;
	}

	@Override
	public Set<String> getInitializedRepositoryIDs() {
		Set<String> result = new LinkedHashSet<String>();

		for (String id : delegate.getInitializedRepositoryIDs()) {
			try {
				if (isCorrectType(id)) {
					result.add(id);
				}
			}
			catch (RepositoryConfigException e) {
				logger.error("Failed to verify repository type", e);
			}
			catch (RepositoryException e) {
				logger.error("Failed to verify repository type", e);
			}
		}

		return result;
	}

	@Override
	public Collection<Repository> getInitializedRepositories() {
		List<Repository> result = new ArrayList<Repository>();

		for (String id : getInitializedRepositoryIDs()) {
			try {
				Repository repository = getRepository(id);

				if (repository != null) {
					result.add(repository);
				}
			}
			catch (RepositoryConfigException e) {
				logger.error("Failed to verify repository type", e);
			}
			catch (RepositoryException e) {
				logger.error("Failed to verify repository type", e);
			}
		}

		return result;
	}

	@Override
	protected Repository createRepository(String id)
		throws RepositoryConfigException, RepositoryException
	{
		throw new UnsupportedOperationException(
				"Repositories cannot be created through this wrapper. This method should not have been called, the delegate should take care of it.");
	}

	@Override
	public Collection<RepositoryInfo> getAllRepositoryInfos(boolean skipSystemRepo)
		throws RepositoryException
	{
		List<RepositoryInfo> result = new ArrayList<RepositoryInfo>();

		for (RepositoryInfo repInfo : delegate.getAllRepositoryInfos(skipSystemRepo)) {
			try {
				if (isCorrectType(repInfo.getId())) {
					result.add(repInfo);
				}
			}
			catch (RepositoryConfigException e) {
				throw new RepositoryException(e.getMessage(), e);
			}
		}

		return result;
	}

	@Override
	public RepositoryInfo getRepositoryInfo(String id)
		throws RepositoryException
	{
		try {
			if (isCorrectType(id)) {
				return delegate.getRepositoryInfo(id);
			}

			return null;
		}
		catch (RepositoryConfigException e) {
			throw new RepositoryException(e.getMessage(), e);
		}
	}

	@Override
	public void refresh() {
		delegate.refresh();
	}

	@Override
	public void shutDown() {
		delegate.shutDown();
	}

	@Override
	protected void cleanUpRepository(String repositoryID)
		throws IOException
	{
		throw new UnsupportedOperationException(
				"Repositories cannot be removed through this wrapper. This method should not have been called, the delegate should take care of it.");
	}

	protected boolean isCorrectType(String repositoryID)
		throws RepositoryConfigException, RepositoryException
	{
		// first, check for SystemRepository, because we can't get a repository
		// config object for it
		boolean result = !SystemRepository.ID.equals(repositoryID);
		if (result) {
			result = isCorrectType(delegate.getRepositoryConfig(repositoryID));
		}
		return result;
	}

	protected boolean isCorrectType(RepositoryConfig repositoryConfig) {
		boolean result = false;

		if (repositoryConfig != null) {
			result = repositoryConfig.getRepositoryImplConfig().getType().equals(type);
		}

		return result;
	}
}
