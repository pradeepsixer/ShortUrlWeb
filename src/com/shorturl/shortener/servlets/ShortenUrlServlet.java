package com.shorturl.shortener.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.shorturl.actiontypes.ActionJsonConverters;
import com.shorturl.actiontypes.ActionType;
import com.shorturl.appserver.LookupManager;
import com.shorturl.common.DomainUtil;
import com.shorturl.common.numbers.Base62;
import com.shorturl.common.numbers.RangedRandomGenerator;
import com.shorturl.datamodel.ShortUrlDetails;
import com.shorturl.ejb.interfaces.ShortUrlBeanLocal;
import com.shorturl.ejb.interfaces.UserDetailsBeanLocal;

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
		try {
			long randomNumber = randomNumGenerator.getRandomNumber();
			String shortenedUrl = Base62.encode(randomNumber);

			response.setContentType("text/plain");
			ShortUrlDetails linkdetails = new ShortUrlDetails();
			linkdetails.setShortUrl(shortenedUrl);
			linkdetails.setLandingPageUrl(request.getParameter("lp_url"));
			// TODO - Must change this to fetching actionconverter based on HttpRequest
			linkdetails.setActionJson(
					ActionJsonConverters.getActionJsonConverter(ActionType.CTABANNER).convertToString(request));

			ShortUrlBeanLocal shortUrlBeanLocal = (ShortUrlBeanLocal) LookupManager.lookup(ShortUrlBeanLocal.JNDI_NAME);
			LOG.info("The inserted ShortUrlDetails has the id - " + linkdetails.getShortUrlId());

			HttpSession httpSession = request.getSession(false);
			if (httpSession != null && httpSession.getAttribute(LoginConstants.SUCCESSFUL_AUTH) != null
					&& ((Boolean) httpSession.getAttribute(LoginConstants.SUCCESSFUL_AUTH))) {
				String emailId = (String) httpSession.getAttribute(LoginConstants.EMAIL_ID);

				UserDetailsBeanLocal userDetailsBeanLocal = (UserDetailsBeanLocal) LookupManager.lookup(UserDetailsBeanLocal.JNDI_NAME);
				linkdetails.setUserDetails(userDetailsBeanLocal.getUserDetailsByEmailid(emailId));
			}
			linkdetails = shortUrlBeanLocal.addOrUpdateShortUrlDetails(linkdetails);

			response.getWriter().write("{\"status\":\"success\", \"short-url\":\"" + DomainUtil.calcDomainUrl(request)
					+ "s/" + shortenedUrl + "\"}");
		} catch (Exception exception) {
			LOG.error(exception);
			response.getWriter().write("{\"status\":\"failure\"}");
		}
	}
}
