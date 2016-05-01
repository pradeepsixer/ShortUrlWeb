package com.shorturl.template.datamodel;

/**
 * A simple object bean - for just a single object 
 * @author Pradeep Kumar
 */
public class SimpleObjectBean {
	private Object object;

	/**
	 * Create a simple object bean with the specified object
	 * @param argObject The object to be wrapped under the {@link SimpleObjectBean}
	 */
	public SimpleObjectBean(Object argObject) {
		this.object = argObject;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
}
