/**
 * 
 */
package com.shorturl.actiontypes;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Pradeep Kumar
 *
 */
public class ActionSpecificBannerConstructors {
	private static Map<ActionType, ActionSpecificBannerConstructor> actionTypeHandlers;

	static {
		actionTypeHandlers = new EnumMap<>(ActionType.class);
		actionTypeHandlers.put(ActionType.CTABANNER, new CtaBannerActionBannerGenerator());
//		actionTypeHandlers.put(ActionType.NEWSLETTER, null);
		actionTypeHandlers.put(ActionType.NONE, new NoActionBannerGenerator());
	}

	private ActionSpecificBannerConstructors() {
	}

	public static ActionSpecificBannerConstructor getBannerConstructor(String actionType) {
		return getActionSpecificBannerConstructor(ActionType.valueOf(actionType));
	}

	public static ActionSpecificBannerConstructor getActionSpecificBannerConstructor(ActionType actionType) {
		return actionTypeHandlers.get(actionType);
	}
}
