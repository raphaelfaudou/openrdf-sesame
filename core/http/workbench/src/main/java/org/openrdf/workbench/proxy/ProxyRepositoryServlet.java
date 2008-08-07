package org.openrdf.workbench.proxy;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.workbench.RepositoryServlet;
import org.openrdf.workbench.base.BaseRepositoryServlet;
import org.openrdf.workbench.exceptions.BadRequestException;
import org.openrdf.workbench.exceptions.MissingInitParameterException;
import org.openrdf.workbench.util.BasicServletConfig;
import org.openrdf.workbench.util.DynamicHttpRequest;

public class ProxyRepositoryServlet extends BaseRepositoryServlet {
	private static final String HEADER_IFMODSINCE = "If-Modified-Since";
	private static final String HEADER_LASTMOD = "Last-Modified";
	private static final String SERVER_COOKIE = "workbench-server";
	private static final String DEFAULT_PATH_PARAM = "default-command";
	private Map<String, RepositoryServlet> servlets = new HashMap<String, RepositoryServlet>();
	private long lastModified;

	@Override
	@SuppressWarnings("unchecked")
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		lastModified = System.currentTimeMillis();
		if (config.getInitParameter(DEFAULT_PATH_PARAM) == null)
			throw new MissingInitParameterException(DEFAULT_PATH_PARAM);
		Enumeration<String> names = config.getInitParameterNames();
		while (names.hasMoreElements()) {
			String path = names.nextElement();
			if (path.startsWith("/")) {
				try {
					servlets.put(path, createServlet(path));
				} catch (InstantiationException e) {
					throw new ServletException(e);
				} catch (IllegalAccessException e) {
					throw new ServletException(e);
				} catch (ClassNotFoundException e) {
					throw new ServletException(e);
				}
			}
		}
	}

	@Override
	public void destroy() {
		for (RepositoryServlet servlet : servlets.values()) {
			servlet.destroy();
		}
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (isCachable(req)) {
			String server = req.getParameter(SERVER_COOKIE);
			long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
			if (server != null || ifModifiedSince < (lastModified / 1000 * 1000)) {
				resp.setDateHeader(HEADER_LASTMOD, lastModified);
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}
		}
		String pathInfo = req.getPathInfo();
		if (pathInfo == null) {
			String defaultPath = config.getInitParameter(DEFAULT_PATH_PARAM);
			resp.sendRedirect(req.getRequestURI() + defaultPath);
		} else if ("/".equals(pathInfo)) {
			String defaultPath = config.getInitParameter(DEFAULT_PATH_PARAM);
			resp.sendRedirect(req.getRequestURI() + defaultPath.substring(1));
		} else {
			RepositoryServlet servlet = servlets.get(pathInfo);
			if (servlet == null)
				throw new BadRequestException("Unconfigured path: " + pathInfo);
			DynamicHttpRequest hreq = new DynamicHttpRequest(req);
			hreq.setServletPath(hreq.getServletPath() + hreq.getPathInfo());
			hreq.setPathInfo(null);
			servlet.service(hreq, resp);
		}
		if ("POST".equals(req.getMethod())) {
			lastModified = System.currentTimeMillis();
		}
	}

	private boolean isCachable(HttpServletRequest req) {
		if (!"GET".equals(req.getMethod()))
			return false;
		// MSIE does not cache different url parameters separately
		return req.getRequestURL().toString().indexOf(';') < 0;
	}

	private RepositoryServlet createServlet(String path)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ServletException {
		Class<?> klass = Class.forName(config.getInitParameter(path));
		RepositoryServlet servlet = (RepositoryServlet) klass.newInstance();
		servlet.setRepositoryManager(manager);
		servlet.setRepositoryInfo(info);
		servlet.setRepository(repository);
		servlet.init(new BasicServletConfig(path, config));
		return servlet;
	}

}
