package com.shorturl.shortener.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.shorturl.appserver.LookupManager;
import com.shorturl.ejb.interfaces.ShortUrlBeanLocal;

/**
 * Servlet implementation class IncrementViewCountServlet
 */
@WebServlet(description = "Servlet for incrementing the view count of short url", urlPatterns = { "/updvwct" })
public class IncrementViewCountServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(IncrementViewCountServlet.class);

	private static final long serialVersionUID = 1L;
	private static final String CLICK_COUNT = "clickCount";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IncrementViewCountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String referer = request.getHeader("Referer");
			String shortUrl = request.getParameter("u");

			/*
			 * 1. First check whether the "Referer" header is included 2. If so,
			 * does it contain the shorturl 3. If both the above conditions are
			 * satisfied, then fetch the session details 4. Check for whether
			 * the client has already clicked the button in the current session.
			 * If not, then increment the click count.
			 */
			if (referer != null && shortUrl != null && referer.contains(shortUrl)) {
				HttpSession httpSession = request.getSession(false);

				/*
				 * If the session is null, the client is calling the increment code directly,
				 * or perhaps through a bot, so do not increment the view count
				 */
				if (httpSession != null) {
					String sessionId = httpSession.getId().intern();
					Set<String> clickedUrlSet = null;
					synchronized (sessionId) {
						clickedUrlSet = (Set<String>) httpSession.getAttribute(CLICK_COUNT);
						if (clickedUrlSet == null) {
							clickedUrlSet = new HashSet<>();
							httpSession.setAttribute(CLICK_COUNT, clickedUrlSet);
						}
						if (!clickedUrlSet.contains(shortUrl)) {
							clickedUrlSet.add(shortUrl);
							incrementClickCount(shortUrl);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void incrementClickCount(String shortUrl) {
		// TODO Must add the thread to the executor services
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
				shortUrlBean.incrementClickCount(shortUrl);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
	}
}
