package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.register.form.Form.Type;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = "field")
public class Input implements Field<String> {

	private String var;

	private String type;

	private String name;
	
	private String value;

	private Required required;

	public Input() {

	}

	public Input(String type, String name, String var, Required required) {
		this.type = Type.parse(type).toString();
		this.name = name;
		this.var = var;
		this.required = required;
	}

	@Override
	@XmlAttribute(name = "label")
	public String getName() {
		return this.name;
	}

	@XmlAttribute
	public String getVar() {
		return this.var;
	}

	@XmlElement
	public Required getRequired() {
		return this.required;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}
}