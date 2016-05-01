/**
 * 
 */
package com.shorturl.template;

import java.io.StringReader;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import org.jboss.logging.Logger;

import com.shorturl.actiontypes.ActionSpecificBannerConstructor;
import com.shorturl.actiontypes.ActionSpecificBannerConstructors;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * Freemarker Configuration Manager. Freemarker requires the Configuration to be
 * loaded only once as it is resource intensive. This class performs the task
 * 
 * @author Pradeep Kumar
 */
public class FreemarkerConfigManager {
	private static final String TYPE = "type";
	private static final String TYPE_INFO = "type-info";
	private static final Logger LOG = Logger.getLogger(FreemarkerConfigManager.class);

	private static Configuration config = null;

	static {
		config = new Configuration(Configuration.VERSION_2_3_23);
		ClassLoader classloader = FreemarkerConfigManager.class.getClassLoader();
		config.setTemplateLoader(new ClassTemplateLoader(classloader, "META-INF/fmtemplates"));
		config.setDefaultEncoding("UTF-8");
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		config.setSharedVariable("handleActionJson", getActionTypeHandler());
	}

	// Prevent Instantiation
	private FreemarkerConfigManager() {
	}

	public static Configuration getConfiguration() {
		return config;
	}

	private static TemplateMethodModelEx getActionTypeHandler() {
		return new TemplateMethodModelEx() {

			@Override
			@SuppressWarnings("rawtypes")
			public Object exec(List arg0) throws TemplateModelException {
				if (!arg0.get(0).getClass().equals(SimpleScalar.class)) {
					throw new TemplateModelException("Expected SimpleScalar (String) type, but received [" + arg0.get(0).getClass() + "]");
				}
				String actionJson = ((SimpleScalar) arg0.get(0)).getAsString();
				JsonObject jsonObject = Json.createReader(new StringReader(actionJson)).readObject();

				// TODO - change the logger level to debug/trace
				LOG.info("Action type : " + jsonObject.getString(TYPE));
				String actionType = jsonObject.getString(TYPE);
				ActionSpecificBannerConstructor bannerConstructor = ActionSpecificBannerConstructors.getBannerConstructor(actionType);
				try {
					return bannerConstructor.constructTypeBasedBanner(jsonObject.getJsonObject(TYPE_INFO));
				} catch (Exception e) {
					throw new TemplateModelException(e);
				}
			}
		};
	}
}
