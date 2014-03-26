package com.sissi.protocol.iq.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月5日
 */
@XmlRootElement(name = Select.NAME)
public class Select extends XInput {

	public final static String NAME = "field";

	private List<XOption> option;

	public Select() {
	}

	public Select(String type, String name, String var, XOption... options) {
		this(type, name, var, (String) null, options);
	}

	public Select(String type, String name, String var, String value, XOption... options) {
		this(type, name, var, value, null, options);
	}

	public Select(String type, String name, String var, XRequired required, XOption... options) {
		this(type, name, var, null, required, options);
	}

	public Select(String type, String name, String var, String value, XRequired required, XOption... options) {
		super(XFieldType.parse(type).toString(), name, var, value, required);
		if (options != null) {
			for (XOption each : options) {
				this.add(each);
			}
		}
	}

	private Select add(XOption option) {
		if (this.option == null) {
			this.option = new ArrayList<XOption>();
		}
		this.option.add(option);
		return this;
	}

	@XmlElements({ @XmlElement(name = XOption.NAME, type = XOption.class) })
	public List<XOption> getOption() {
		return this.option;
	}
}
