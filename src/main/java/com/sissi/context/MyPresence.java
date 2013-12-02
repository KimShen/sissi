package com.sissi.context;

/**
 * @author kim 2013-11-21
 */
public interface MyPresence {

	public String getTypeText();

	public String getShowText();

	public String getStatusText();

	public MyPresence setTypeText(String type);

	public MyPresence setShowText(String show);

	public MyPresence setStatusText(String status);

	public MyPresence clear();

	public interface MyPresenceBuilder {

		public MyPresence build(JIDContext context);
	}
}
