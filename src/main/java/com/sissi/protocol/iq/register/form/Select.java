package com.sissi.protocol.iq.register.form;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.data.XOption;

/**
 * @author kim 2013年12月5日
 */
@XmlRootElement(name = Select.NAME)
public class Select extends Input {

	public final static String NAME = "field";

	private List<XOption> option;

	public Select() {
	}

	public Select(String name, String var, Required required, XOption... options) {
		super(Type.LIST_SINGLE.toString(), name, var, required);
		if (options != null) {
			for (XOption each : options) {
				this.add(each);
			}
		}
	}

	public Select add(XOption option) {
		if (this.option == null) {
			this.option = new ArrayList<XOption>();
		}
		this.option.add(option);
		return this;
	}

	@XmlElements({ @XmlElement(name = "option", type = XOption.class) })
	public List<XOption> getOption() {
		return option;
	}
}
