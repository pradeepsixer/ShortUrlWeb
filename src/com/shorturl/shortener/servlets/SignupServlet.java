package com.shorturl.shortener.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.shorturl.appserver.LookupManager;
import com.shorturl.common.HttpConstants.HttpHeaders;
import com.shorturl.common.HttpConstants.StatusCodes;
import com.shorturl.common.string.StringUtil;
import com.shorturl.exceptions.MalformedHttpRequestException;
import com.shorturl.security.DigestUtil;
import com.shorturl.template.FreemarkerConfigManager;
import com.shorturl.template.TemplateConstants;

import freemarker.template.Template;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(SignupServlet.class);

	private static final long serialVersionUID = 1L;

	private static final String EMAIL_ID = "email_id";
	private static final String PASSWORD = "password";
	private static final String DURATION = "duration";
	private static final String PROFILE_NAME = "profile_name";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession httpSession = request.getSession(false);
			if (request.getParameterMap().containsKey(EMAIL_ID) && httpSession != null &&
					request.getHeader(HttpHeaders.REFERER) != null && request.getHeader(HttpHeaders.REFERER).contains("/signup")) {
				
				Connection connection = LookupManager.getDatasource().getConnection();
				PreparedStatement statement = connection.prepareStatement(getNewUserStatement(request));
				statement.executeUpdate();
				response.sendRedirect("/login");
			} else {
				Template template = FreemarkerConfigManager.getConfiguration().getTemplate(TemplateConstants.SIGNUP_TEMPLATE);
				template.process(Collections.emptyMap(), response.getWriter());
			}
		}catch (MalformedHttpRequestException e) {
			LOG.error(e);
			response.sendError(StatusCodes.BAD_REQUEST);
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

	private String getNewUserStatement(HttpServletRequest request) throws Exception {
		String emailId = request.getParameter(EMAIL_ID);
		String password = request.getParameter(PASSWORD);
		Short duration = Short.parseShort(request.getParameter(DURATION));
		String profileName = request.getParameter(PROFILE_NAME);

		if (StringUtil.isEmpty(emailId) || StringUtil.isEmpty(password) || StringUtil.isEmpty(profileName) || duration == null) {
			throw new MalformedHttpRequestException("Required field(s) for signup are missing");
		}
		MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
		String encPwd = DigestUtil.base64EncodedHash(msgDigest, password);

		StringBuilder statementBuilder = new StringBuilder(1000);
		statementBuilder.append("INSERT INTO user_details(emailid, profile_name, password, bitmask, expiration_date) ");
		statementBuilder.append("VALUES('" + emailId +"', '" + profileName +"', '" + encPwd + "',0,");
		statementBuilder.append("date(from_unixtime(" + (new Date()).getTime() / 1000 + ")) + interval " + duration + " month)");
		return statementBuilder.toString();
	}
}
