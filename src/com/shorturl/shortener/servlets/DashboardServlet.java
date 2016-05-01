package com.shorturl.shortener.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.shorturl.appserver.LookupManager;
import com.shorturl.common.HttpConstants.StatusCodes;
import com.shorturl.datamodel.ShortUrlDetails;
import com.shorturl.ejb.interfaces.UserDetailsBeanLocal;
import com.shorturl.template.FreemarkerConfigManager;
import com.shorturl.template.TemplateConstants;
import com.shorturl.template.datamodel.SimpleObjectBean;

import freemarker.template.Template;

/**
 * Servlet implementation class DashboardServlet
 */
@SuppressWarnings("serial")
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashboardServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(DashboardServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession httpSession = request.getSession(false);
			if (httpSession != null && httpSession.getAttribute(LoginConstants.SUCCESSFUL_AUTH) != null
					&& ((Boolean) httpSession.getAttribute(LoginConstants.SUCCESSFUL_AUTH))) {
				Template template = FreemarkerConfigManager.getConfiguration()
						.getTemplate(TemplateConstants.DASHBOARD_TEMPLATE);
				String emailId = (String) httpSession.getAttribute(LoginConstants.EMAIL_ID);
				UserDetailsBeanLocal userDetailsBean = (UserDetailsBeanLocal) LookupManager.lookup(UserDetailsBeanLocal.JNDI_NAME);
				List<ShortUrlDetails> shortUrlDetailsList = userDetailsBean.getUserDetailsByEmailid(emailId).getShortUrlDetails();
				SimpleObjectBean objectBean = new SimpleObjectBean(shortUrlDetailsList);

				response.setContentType("text/html");
				template.process(objectBean, response.getWriter());
			} else {
				response.sendRedirect("/login");
			}
		} catch (Exception e) {
			LOG.error(e);
			response.sendError(StatusCodes.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
