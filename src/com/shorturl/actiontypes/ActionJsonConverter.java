/**
 * 
 */
package com.shorturl.actiontypes;

import javax.servlet.http.HttpServletRequest;

/**
 * Does the necessary conversion for accessing the action type related information
 * <ol>
 * <li>Parse the JSON formatted String from database to the JSON Object</li>
 * <li>Extract the data required for the {@link ActionType Action Type's} json representation</li>
 * </ol>
 * @author Pradeep Kumar
 */
public interface ActionJsonConverter {

	/**
	 * Extract the action's required data from the HTTP Request. Needed for storing the data
	 * from the HTTP Request in the database
	 * @param request {@link HttpServletRequest} containing the action's required data
	 * @return String representation of the action's required data in JSON format
	 */
	public String convertToString(HttpServletRequest request);
}
