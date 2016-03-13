/**
 * 
 */
package com.shorturl.template;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * Freemarker Configuration Manager. Freemarker requires the Configuration to be
 * loaded only once as it is resource intensive. This class performs the task
 * 
 * @author Pradeep Kumar
 */
public class FreemarkerConfigManager {
	private static Configuration config = null;

	static {
		config = new Configuration(Configuration.VERSION_2_3_23);
		ClassLoader classloader = FreemarkerConfigManager.class.getClassLoader();
		config.setTemplateLoader(new ClassTemplateLoader(classloader, "META-INF/fmtemplates"));
		config.setDefaultEncoding("UTF-8");
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	/**
	 * Singleton Class - Prevent Instantiation
	 */
	private FreemarkerConfigManager() {
	}

	public static Configuration getConfiguration() {
		return config;
	}
}
