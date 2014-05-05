package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月25日
 */
@Metadata(uri = Presence.XMLNS, localName = PresencePriority.NAME)
@XmlRootElement
public class PresencePriority {

	public final static String NAME = "priority";

	private final Log log = LogFactory.getLog(PresencePriority.class);

	private String text;

	public PresencePriority() {
		super();
	}

	public PresencePriority(String text) {
		super();
		this.text = text;
	}

	public Integer priority() {
		try {
			return this.text != null ? Integer.parseInt(this.getText()) : null;
		} catch (Exception e) {
			log.debug(e.toString());
			Trace.trace(log, e);
			return null;
		}
	}

	public int priority(int def) {
		Integer priority = this.priority();
		return priority != null ? priority : def;
	}

	public String getText() {
		return this.text;
	}

	public PresencePriority setText(String text) {
		this.text = text;
		return this;
	}
}
