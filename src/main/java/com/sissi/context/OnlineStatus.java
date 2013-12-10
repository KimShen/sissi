package com.sissi.context;

/**
 * @author kim 2013-11-21
 */
public interface OnlineStatus {

	public String getTypeAsText();

	public String getShowAsText();

	public String getStatusAsText();

	public OnlineStatus asType(String type);

	public OnlineStatus asShow(String show);

	public OnlineStatus asStatus(String status);

	public OnlineStatus clear();

	public interface OnlineStatusBuilder {

		public OnlineStatus build(JIDContext context);
	}
}
