package com.shorturl.shortener.servlets;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.shorturl.appserver.LookupManager;
import com.shorturl.common.numbers.Base62;
import com.shorturl.common.numbers.RangedRandomGenerator;
import com.shorturl.datamodel.ShortUrlDetails;
import com.shorturl.ejb.interfaces.ShortUrlBeanLocal;

/**
 * Servlet implementation class ShortenUrl
 */
@WebServlet("/shortenurl")
public class ShortenUrlServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(ShortenUrlServlet.class);
	private static final long serialVersionUID = 1L;
	private static final RangedRandomGenerator<Long> randomNumGenerator = new RangedRandomGenerator<Long>(1L,
			56800235583L);

	/**
	 * Default constructor.
	 */
	public ShortenUrlServlet() {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long randomNumber = randomNumGenerator.getRandomNumber();
		String shortenedUrl = Base62.encode(randomNumber);

		response.setContentType("text/plain");
		try {
			ShortUrlDetails linkdetails = new ShortUrlDetails();
			linkdetails.setShortUrl(shortenedUrl);
			linkdetails.setLandingPageUrl(request.getParameter("lp_url"));
			linkdetails.setShareUrl(request.getParameter("share_url"));
			linkdetails.setBannerTitle(request.getParameter("cta_title"));
			linkdetails.setBannerDescription(request.getParameter("cta_msg"));
			ShortUrlBeanLocal shortUrlBeanLocal = (ShortUrlBeanLocal) LookupManager.lookup(ShortUrlBeanLocal.JNDI_NAME);
			shortUrlBeanLocal.addOrUpdateShortUrlDetails(linkdetails);

			response.getWriter().write("{\"status\":\"success\", \"short-url\":\"https://surfweb.io/s/" + shortenedUrl + "\"}");
		} catch (NamingException exception) {
			LOG.error(exception);
			response.getWriter().write("{\"status\":\"failure\"}");
		}
	}
}
