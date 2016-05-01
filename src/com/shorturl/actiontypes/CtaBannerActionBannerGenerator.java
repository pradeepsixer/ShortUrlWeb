/**
 * 
 */
package com.shorturl.actiontypes;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import com.shorturl.template.FreemarkerConfigManager;
import com.shorturl.template.TemplateConstants;

import freemarker.template.Template;

/**
 *
 * @author Pradeep Kumar
 */
public class CtaBannerActionBannerGenerator implements ActionSpecificBannerConstructor {

	/* (non-Javadoc)
	 * @see com.shorturl.actiontypes.ActionSpecificBannerConstructor#constructTypeBasedBanner(javax.json.JsonObject)
	 */
	@Override
	public String constructTypeBasedBanner(JsonObject actionJson) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Template ctaBottomBannerTemplate = FreemarkerConfigManager.getConfiguration().getTemplate(TemplateConstants.CTA_BOTTOM_BANNER_TEMPLATE);

		Map<String, String> modelMap = new HashMap<>();
		modelMap.put("bannerTitle", actionJson.getString("cta-title"));
		modelMap.put("bannerDescription", actionJson.getString("cta-message"));
		modelMap.put("targetUrl", actionJson.getString("target-url"));

		ctaBottomBannerTemplate.process(modelMap, stringWriter);
		return stringWriter.toString();
	}
}
