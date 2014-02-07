package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = XData.XMLNS, localName = XOption.NAME)
@XmlRootElement(name = XOption.NAME)
public class XOption implements Field<XValue>, Collector {

	public final static String NAME = "option";

	private String name;

	private XValue xValue;

	public XOption() {
	}

	public XOption(String name, String value) {
		this.name = name;
		this.xValue = new XValue(value);
	}

	public String getName() {
		return this.name;
	}

	@XmlAttribute(name = "label")
	public String getLabel() {
		return this.name;
	}

	@XmlElement
	public XValue getValue() {
		return this.xValue != null ? this.xValue : null;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}

	@Override
	public void set(String localName, Object ob) {
		this.xValue = XValue.class.cast(ob);
	}
}
