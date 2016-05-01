/**
 * 
 */
package com.shorturl.actiontypes;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Pradeep Kumar
 */
public class ActionJsonConverters {
	private static Map<ActionType, ActionJsonConverter> actionJsonConvertersMap;

	static {
		actionJsonConvertersMap = new EnumMap<>(ActionType.class);
		actionJsonConvertersMap.put(ActionType.CTABANNER, new BannerActionTypeConverter());
		actionJsonConvertersMap.put(ActionType.NONE, new NoActionTypeConverter());
//		actionJsonConvertersMap.put(ActionType.NEWSLETTER, null);
	}

	private ActionJsonConverters () {
	}

	/**
	 * Get the {@link ActionJsonConverter} corresponding to the given action type.
	 * @param actionType {@link ActionType}'s String Representation
	 * @return {@link ActionJsonConverter} corresponding to the given action type
	 */
	public static ActionJsonConverter getActionJsonConverter(String actionType) {
		return getActionJsonConverter(ActionType.valueOf(actionType.toUpperCase()));
	}

	/**
	 * Get the {@link ActionJsonConverter} corresponding to the given action type.
	 * @param actionType {@link ActionType}
	 * @return {@link ActionJsonConverter} corresponding to the given action type
	 */
	public static ActionJsonConverter getActionJsonConverter(ActionType actionType) {
		return actionJsonConvertersMap.get(actionType);
	}

	// TODO Must write a UnsupportedActionType
}
