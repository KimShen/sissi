package com.sissi.protocol.iq.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = XData.XMLNS, localName = XData.NAME)
@XmlType(namespace = XData.XMLNS)
@XmlRootElement(name = XData.NAME)
public class XData extends XFieldWrap implements Field<Object>, Collector {

	public final static String NAME = "x";

	public final static String XMLNS = "jabber:x:data";

	private String type;

	public XData() {
		super(false);
	}

	public XData(boolean isEmbed) {
		super(isEmbed);
	}

	public XData(boolean isEmbed, List<Field<?>> fields) {
		this(isEmbed, XDataType.FORM.toString(), fields);
	}

	public XData(boolean isEmbed, String type, List<Field<?>> fields) {
		super(isEmbed);
		super.add(fields);
		this.type = type;
	}

	public boolean type(XDataType type) {
		return XDataType.parse(this.getType()) == type && (type == XDataType.CANCEL ? !this.hasChild() : this.hasChild());
	}

	public XData add(Field<?> field) {
		super.add(field);
		return this;
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
}
