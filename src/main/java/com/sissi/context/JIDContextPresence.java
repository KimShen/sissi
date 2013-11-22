package com.sissi.context;

/**
 * @author kim 2013-11-21
 */
public interface JIDContextPresence {

	public String getTypeText();

	public String getShowText();

	public String getStatusText();

	public JIDContextPresence setTypeText(String type);

	public JIDContextPresence setShowText(String show);

	public JIDContextPresence setStatusText(String status);

	public JIDContextPresence clear();
}
