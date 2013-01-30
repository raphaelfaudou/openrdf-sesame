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
package info.aduna.webapp.system;

import info.aduna.app.AppConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SystemInfoController implements Controller {

	private String view;

	private AppConfiguration config;

	private ServerInfo server;

	public SystemInfoController() {
		server = new ServerInfo();
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView();
		result.setViewName(view);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("appConfig", config);
		model.put("server", server);
		model.put("memory", new MemoryInfo());
		model.put("javaProps", getJavaPropStrings());
		model.put("envVars", getEnvVarStrings());
		result.addAllObjects(model);

		return result;
	}

	public AppConfiguration getConfig() {
		return config;
	}

	public void setConfig(AppConfiguration config) {
		this.config = config;
	}

	public static class ServerInfo {
		private String os;

		private String java;

		private String user;

		public ServerInfo() {
			os = System.getProperty("os.name") + " "
					+ System.getProperty("os.version") + " ("
					+ System.getProperty("os.arch") + ")";
			java = System.getProperty("java.vendor") + " "
					+ System.getProperty("java.vm.name") + " "
					+ System.getProperty("java.version");
			user = System.getProperty("user.name");
		}

		public String getOs() {
			return os;
		}

		public String getJava() {
			return java;
		}

		public String getUser() {
			return user;
		}
	}

	public static class MemoryInfo {

		private int maximum;
		private int used;
		private float percentageInUse;

		public MemoryInfo() {
			Runtime runtime = Runtime.getRuntime();
			long usedMemory = runtime.totalMemory() - runtime.freeMemory();
			long maxMemory = runtime.maxMemory();

			// Memory usage (percentage)
			percentageInUse = (float) ((float) usedMemory / (float) maxMemory);

			// Memory usage in MB
			used = (int) (usedMemory / 1024 / 1024);
			maximum = (int) (maxMemory / 1024 / 1024);
		}

		public int getMaximum() {
			return maximum;
		}

		public int getUsed() {
			return used;
		}

		public float getPercentageInUse() {
			return percentageInUse;
		}
	}
	
	private Map getJavaPropStrings() {
		Properties sysProps = System.getProperties();
		ArrayList keyList = new ArrayList(sysProps.keySet());
		Collections.sort(keyList);
		Map result = new LinkedHashMap(keyList.size());
		Iterator<String> sysPropNames = keyList.iterator();
		while (sysPropNames.hasNext()) {
			String name = sysPropNames.next();
			if (!name.startsWith("aduna")) {
				result.put(name, sysProps.get(name));
			}
		}
		return result;
	}
	
	private Map getEnvVarStrings() {
		Map<String, String> envProps = System.getenv();
		ArrayList keyList = new ArrayList(envProps.keySet());
		Collections.sort(keyList);
		Map result = new LinkedHashMap(keyList.size());
		Iterator<String> envPropNames = keyList.iterator();
		while (envPropNames.hasNext()) {
			String name = envPropNames.next();
			result.put(name, envProps.get(name));
		}
		return result;
	}
}
