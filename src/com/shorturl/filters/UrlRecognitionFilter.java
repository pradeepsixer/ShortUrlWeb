/**
 * 
 */
package com.shorturl.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.shorturl.appserver.LookupManager;
import com.shorturl.datamodel.ShortUrlDetails;
import com.shorturl.ejb.interfaces.ShortUrlBeanLocal;
import com.shorturl.template.FreemarkerConfigManager;
import com.shorturl.template.TemplateConstants;

import freemarker.template.Template;

/**
 * This filter recognizes the different kinds of URLs and forwards it to the
 * appropriate ResponseBuilder
 * 
 * TODO After the ResponseBuilder classes are defined, link them.
 * 
 * @author Pradeep Kumar
 */
public class UrlRecognitionFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(UrlRecognitionFilter.class);

	// TODO - Delete this as this is only intended for testing purpose
	private static final Set<String> sessionIdsSet = new HashSet<>();

	private static final String VIEW_COUNT = "viewCount";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		/*
		 * First get the session associated with the client. If the session does
		 * not exist, then create the session for the client and link the set of
		 * URLs visited by the client.
		 * 
		 * If within a session, the client has already visited the page, then do
		 * not increment the count for the url. Else, increment the page view
		 * count for the shortened url
		 */

		try {
			if (request instanceof HttpServletRequest) {
				String requestURI = ((HttpServletRequest) request).getRequestURI();
				HttpSession httpSession = ((HttpServletRequest) request).getSession(true);
				((HttpServletRequest) request).getHeader("Referer");

				// If the Request URI starts with /s/, it refers to a shortened
				// url and db lookup must be done
				if (requestURI.startsWith("/s/")) {
					String sessionId = httpSession.getId().intern();
					String shortUrl = requestURI.substring(3, requestURI.length());
					Set<String> viewedUrlSet = null;

					LOG.info("url recognition : " + sessionId);
					synchronized (sessionId) {
						viewedUrlSet = (Set<String>) httpSession.getAttribute(VIEW_COUNT);
						if (viewedUrlSet == null) {
							viewedUrlSet = new HashSet<>();
							httpSession.setAttribute(VIEW_COUNT, viewedUrlSet);
						}
					}
					ShortUrlBeanLocal shortUrlBean = (ShortUrlBeanLocal) LookupManager
							.lookup(ShortUrlBeanLocal.JNDI_NAME);
					ShortUrlDetails shortUrlDetails = shortUrlBean
							.getShortUrlDetails(requestURI.substring(3, requestURI.length()));

					Template template = FreemarkerConfigManager.getConfiguration()
							.getTemplate(TemplateConstants.BANNER_TEMPLATE);
					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("shareUrl", shortUrlDetails.getShareUrl());
					paramMap.put("landingPageUrl", shortUrlDetails.getLandingPageUrl());
					paramMap.put("bannerTitle", shortUrlDetails.getBannerTitle());
					paramMap.put("bannerDescription", shortUrlDetails.getBannerDescription());
					paramMap.put("shortUrl", shortUrl);

					response.setContentType("text/html");
					template.process(paramMap, response.getWriter());

					synchronized (sessionId) {
						if (!viewedUrlSet.contains(shortUrl)) {
							viewedUrlSet.add(shortUrl);
							incrementViewCount(shortUrl);
							LOG.info("Incrementing the count as this url is being visited for the first time in this session");
						} else {
							// TODO Must remove this block, as this is only intended for testing purpose
							LOG.info("Not incrementing since the url has already been visited during this session");
						}
					}
					return;
				}
			} else {
				LOG.info("Not a HTTP Request");
			}
		} catch (Exception e) {
			LOG.info("Error in processing the request", e);
		}
		filterChain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Create a FilterConfig member, if in case FilterConfig becomes
		// necessary to process
	}

	private void incrementViewCount(String shortUrl) {
		// TODO Must add the thread to the executor service
		Thread incrementThread = new Thread(new IncrementThread(shortUrl));
		incrementThread.start();
	}

	// TODO Must change this to appropriate class
	private static class IncrementThread implements Runnable {
		private String shortUrl = null;

		private IncrementThread(String shorturl) {
			this.shortUrl = shorturl;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				ShortUrlBeanLocal shortUrlBean = (ShortUrlBeanLocal) LookupManager.lookup(ShortUrlBeanLocal.JNDI_NAME);
				shortUrlBean.incrementViewCount(shortUrl);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
	}
}
