package com.sissi.protocol.iq.search.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.field.FieldValue;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.search.Search;

/**
 * @author kim 2014年6月6日
 */
@Metadata(uri = Search.XMLNS, localName = Last.NAME)
@XmlType(namespace = Search.XMLNS)
@XmlRootElement(name = Last.NAME)
public class Last extends FieldValue {

	public final static Last FIELD = new Last();

	public final static String NAME = "last";

	public Last() {
		super();
	}

	public Last(String text) {
		super(text);
	}

	@Override
	public String getName() {
		return NAME.toUpperCase();
	}
}
