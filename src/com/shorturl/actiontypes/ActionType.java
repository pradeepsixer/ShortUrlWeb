/**
 * 
 */
package com.shorturl.actiontypes;

/**
 * The Type of Actions that are applicable, while creating a Short URL
 * @author Pradeep Kumar
 */
public enum ActionType {
	/**
	 * Indicates that no action is to be created
	 */
	NONE,

	/**
	 * Indicates that the type of response created is a CTA Banner
	 */
	CTABANNER,

	/**
	 * Indicates that the type of response is a newsletter subscription
	 */
	NEWSLETTER
}
