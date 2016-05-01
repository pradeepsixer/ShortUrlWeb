/**
 * 
 */
package com.shorturl.actiontypes;

import javax.servlet.http.HttpServletRequest;

import com.shorturl.common.CommonConstants.StringConstants;

/**
 * @author Pradeep Kumar
 *
 */
public class NoActionTypeConverter implements ActionJsonConverter {

	/* (non-Javadoc)
	 * @see com.shorturl.actiontypes.ActionJsonConverter#convertToString(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String convertToString(HttpServletRequest request) {
		// Since there is nothing to build, return the empty string
		return StringConstants.EMPTY_STRING;
	}

}
