package com.sissi.protocol.iq.vcard.field;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.Field;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "vcard-temp", localName = "PHOTO")
@XmlRootElement(name = "PHOTO")
public class Photo implements Collector, Field {

	private final static String TYPE = "TYPE";

	private final static String BINVAL = "BINVAL";

	private Type type;

	private Binval binval;

	public Photo() {
		super();
	}

	public Photo(String type, String binval) {
		super();
		this.type = new Type(type);
		this.binval = new Binval(binval);
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case TYPE:
			this.type = Type.class.cast(ob);
			return;
		case BINVAL:
			this.binval = (Binval) ob;
			return;
		}
	}

	@XmlElement(name = "TYPE")
	public Type getType() {
		return this.type;
	}

	@XmlElement(name = "BINVAL")
	public Binval getBinval() {
		return binval;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	@Override
	public Boolean isEmpty() {
		return this.getValue() == null;
	}

	@Override
	public Object getValue() {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("type", this.getType().getText());
		values.put(this.binval.getName(), this.binval.getValue());
		return values;
	}
}
