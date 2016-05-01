/**
 * 
 */
package com.shorturl.actiontypes;

import javax.json.JsonObject;

import com.shorturl.common.CommonConstants.StringConstants;

/**
 * @author Pradeep Kumar
 *
 */
public class NoActionBannerGenerator implements ActionSpecificBannerConstructor {

	/* (non-Javadoc)
	 * @see com.shorturl.actiontypes.ActionSpecificBannerConstructor#constructTypeBasedBanner(javax.json.JsonObject)
	 */
	@Override
	public String constructTypeBasedBanner(JsonObject actionJson) throws Exception {
		return StringConstants.EMPTY_STRING;
	}
}
