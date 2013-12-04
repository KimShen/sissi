package com.sissi.protocol.iq.register;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.RegisterContext.Field;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = "jabber:iq:register", localName = "query")
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement(name = "query")
public class Register extends Protocol implements Collector {

	private final static String XMLNS = "jabber:iq:register";

	private final static Comparator<Class<? extends Field>> COMPARATOR = new Comparator<Class<? extends Field>>() {

		@Override
		public int compare(Class<? extends Field> f1, Class<? extends Field> f2) {
			return 1;
		}
	};

	private Map<Class<? extends Field>, Field> fields;

	@XmlElements({ @XmlElement(name = "username", type = Username.class), @XmlElement(name = "password", type = Password.class) })
	public Collection<Field> getFields() {
		return fields.values();
	}

	public <T> T findField(Class<T> field) {
		return field.cast(this.fields.get(field));
	}

	public Register add(Field field) {
		if (this.fields == null) {
			this.fields = new TreeMap<Class<? extends Field>, Field>(COMPARATOR);
		}
		fields.put(field.getClass(), field);
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Register clear() {
		super.clear();
		this.fields = null;
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add((Field) ob);
	}
}
