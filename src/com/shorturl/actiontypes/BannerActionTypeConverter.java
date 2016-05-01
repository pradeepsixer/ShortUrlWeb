/**
 * 
 */
package com.shorturl.actiontypes;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Pradeep Kumar
 */
class BannerActionTypeConverter implements ActionJsonConverter{

	/* (non-Javadoc)
	 * @see com.shorturl.actiontypes.ActionJsonConverter#convertToString(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String convertToString(HttpServletRequest request) {
		JsonObject json = Json.createObjectBuilder().add("type", ActionType.CTABANNER.toString())
				.add("type-info",
						Json.createObjectBuilder().add("target-url", request.getParameter("target_url"))
								.add("cta-title", request.getParameter("cta_title"))
								.add("cta-message", request.getParameter("cta_msg")))
				.build();
		return json.toString();
	}
}
