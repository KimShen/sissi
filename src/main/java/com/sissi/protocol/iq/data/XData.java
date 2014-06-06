package com.sissi.protocol.iq.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = XData.XMLNS, localName = XData.NAME)
@XmlType(namespace = XData.XMLNS)
@XmlRootElement(name = XData.NAME)
public class XData extends XFieldWrap implements Fields, Field<Object>, Collector {

	public final static String NAME = "x";

	public final static String XMLNS = "jabber:x:data";

	private String type;

	public XData() {
		super(false);
	}

	public XData(boolean embed) {
		super(embed);
	}

	public XData(boolean embed, List<Field<?>> fields) {
		this(embed, XDataType.FORM.toString(), fields);
	}

	public XData(boolean embed, String type, List<Field<?>> fields) {
		super(embed);
		super.add(fields);
		this.type = type;
	}

	public XData add(Field<?> field) {
		super.add(field);
		return this;
	}

	public boolean type(XDataType type) {
		return XDataType.parse(this.getType()) == type && (type == XDataType.CANCEL ? !this.hasChild() : true);
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public XData setType(XDataType type) {
		return this.setType(type.toString());
	}

	public XData setType(String type) {
		this.type = type;
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public List<Field<?>> getValue() {
		return super.getFields();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void set(String localName, Object ob) {
		super.add(Field.class.cast(ob));
	}

	public boolean empty() {
		return !super.getFields().isEmpty();
	}

	public XData clone() {
		XData data = new XData();
		data.setType(this.getType());
		data.add(this.getChildren());
		return data;
	}
}
