package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.register.form.Form.Type;
import com.sissi.ucenter.RegisterContext.Field;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = "field")
public class Input implements Field {

	private String value;

	private String type;

	private String name;

	private String var;

	private Required required;

	public Input() {

	}

	public Input(Type type, String name, String var) {
		this(type, null, name, var);
	}

	public Input(Type type, String value, String name, String var) {
		this(type, value, name, var, null);
	}

	public Input(Type type, String name, String var, Required required) {
		this(type, null, name, var, required);
	}

	public Input(Type type, String value, String name, String var, Required required) {
		super();
		this.type = type.toString();
		this.value = value;
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
	public String getType() {
		return type;
	}

	@XmlAttribute
	public String getVar() {
		return var;
	}

	@XmlElement
	public Required getRequired() {
		return required;
	}

	@Override
	@XmlElement(name = "value")
	public String getText() {
		return value;
	}
}
