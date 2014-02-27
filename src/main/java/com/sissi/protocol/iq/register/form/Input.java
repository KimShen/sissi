package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XRequired;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = Input.NAME)
public class Input implements Field<String> {

	public final static String NAME = "field";

	private String var;

	private String type;

	private String name;

	private String value;

	private XRequired required;

	public Input() {

	}

	public Input(String type, String name, String var, XRequired required) {
		this.type = XFieldType.parse(type).toString();
		this.required = required;
		this.name = name;
		this.var = var;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@XmlAttribute(name = "label")
	public String getLabel() {
		return this.name;
	}

	@XmlAttribute
	public String getVar() {
		return this.var;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@XmlElement
	public XRequired getRequired() {
		return this.required;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}