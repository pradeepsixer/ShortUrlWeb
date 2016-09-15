package com.shorturl.shortener.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.jboss.logging.Logger;

import com.shorturl.appserver.LookupManager;
import com.shorturl.common.HttpConstants.HttpHeaders;
import com.shorturl.common.HttpConstants.StatusCodes;
import com.shorturl.security.DigestUtil;
import com.shorturl.template.FreemarkerConfigManager;
import com.shorturl.template.TemplateConstants;

import freemarker.template.Template;

/**
 * Servlet implementation class LoginServlet
 */
@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(LoginServlet.class);

	private static final String LOGIN_FAILED = "loginfailed";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1. Check whether the user is already logged in
		 * 2. If yes, redirect him to the dashboard
		 * 3. If no, check whether the parameter map contains username and password.
		 * 4. If the username and password is found in the parameter map, then validate them
		 * 5. If validation succeeded, then forward the user to the dashboard, else, display the error.
		 * 6. If the username/password is not found among the parameters, then display the login form.
		 */
		try {
			HttpSession httpSession = request.getSession(false);
			if (httpSession != null && httpSession.getAttribute(LoginConstants.SUCCESSFUL_AUTH) != null && ((Boolean) httpSession.getAttribute(LoginConstants.SUCCESSFUL_AUTH))) {
				response.sendRedirect("/dashboard");
			} else {
				// TODO Must change this to JAAS / Security Domain based authentication
				if (request.getParameterMap().containsKey(LoginConstants.EMAIL_ID) && request.getParameterMap().containsKey(LoginConstants.PASSWORD) 
						&& request.getHeader(HttpHeaders.REFERER) != null && request.getHeader(HttpHeaders.REFERER).contains("/login") && httpSession != null) {
					String emailId = request.getParameter(LoginConstants.EMAIL_ID);
					String password = request.getParameter(LoginConstants.PASSWORD);
					if (emailId != null && password != null) {
						DataSource dataSource = LookupManager.getDatasource();
						Connection connection = dataSource.getConnection();
						PreparedStatement statement = connection.prepareStatement("SELECT password FROM user_details WHERE emailid=?");
						statement.setString(1, emailId);
						ResultSet result = statement.executeQuery();

						boolean loginfailed = false;
						if (result.next()) {
							String lookupPwd = result.getString(1);

							MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
							String base64EncPwd = DigestUtil.base64EncodedHash(messageDigest, password);
							if (base64EncPwd.equals(lookupPwd)) {
								// Credential Match Detected
								httpSession.setAttribute(LoginConstants.SUCCESSFUL_AUTH, true);
								httpSession.setAttribute(LoginConstants.EMAIL_ID, emailId);
								response.sendRedirect("/dashboard");
							} else {
								loginfailed = true;
							}
						} else {
							loginfailed = true;
						}
						if (loginfailed) {
							// Show the Login Error
							Template template = FreemarkerConfigManager.getConfiguration()
									.getTemplate(TemplateConstants.LOGIN_TEMPLATE);
							response.setContentType("text/html");

							Map<Object, Object> map = new HashMap<Object, Object>();
							map.put(LOGIN_FAILED, true);
							template.process(map, response.getWriter());
						}
						result.close();
						statement.close();
						connection.close();
					}
				} else {
					// Show the login page
					httpSession = request.getSession(true);
					Template template = FreemarkerConfigManager.getConfiguration()
							.getTemplate(TemplateConstants.LOGIN_TEMPLATE);
					response.setContentType("text/html");

					Map<Object, Object> map = new HashMap<Object, Object>();
					map.put(LOGIN_FAILED, false);
					template.process(map, response.getWriter());
				}
			}
		} catch (Exception e) {
			LOG.error("Error in processing the request", e);
			response.sendError(StatusCodes.INTERNAL_SERVER_ERROR);
		}
	}
}
