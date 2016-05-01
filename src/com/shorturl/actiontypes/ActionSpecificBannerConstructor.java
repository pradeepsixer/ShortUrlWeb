/**
 * 
 */
package com.shorturl.actiontypes;

import javax.json.JsonObject;

/**
 * Handler for the Action Type. From the Action's JSON, constructs the bottom banner
 * @author Pradeep Kumar
 */
public interface ActionSpecificBannerConstructor {
	/**
	 * Construct the Banner according to the {@link ActionType}
	 * @param actionJson The data for constructing the banner
	 * @return HTML for the banner
	 */
	public String constructTypeBasedBanner(JsonObject actionJson) throws Exception;
}
