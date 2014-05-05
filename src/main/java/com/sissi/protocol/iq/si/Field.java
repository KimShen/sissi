package com.sissi.protocol.iq.si;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.data.XOption;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = Si.XMLNS, localName = Field.NAME)
@XmlType(namespace = Si.XMLNS)
@XmlRootElement
public class Field implements Collector {

	public final static String NAME = "field";

	private String var;

	private String type;

	private List<XOption> options;

	@XmlElements({ @XmlElement(name = XOption.NAME, type = XOption.class) })
	public List<XOption> getOptions() {
		return options;
	}

	@XmlAttribute
	public String getVar() {
		return this.var;
	}

	public Field setVar(String var) {
		this.var = var;
		return this;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public Field setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		if (this.options == null) {
			this.options = new ArrayList<XOption>();
		}
		this.options.add(XOption.class.cast(ob));
	}
}
