package com.sissi.protocol.iq.search.field;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.FieldValue;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.search.Search;

/**
 * @author kim 2014年6月6日
 */
@Metadata(uri = Search.XMLNS, localName = First.NAME)
@XmlRootElement(name = First.NAME)
public class First extends FieldValue {

	public final static First FIELD = new First();

	public final static String NAME = "first";

	public First() {
		super();
	}

	public First(String text) {
		super(text);
	}

	@Override
	public String getName() {
		return NAME.toUpperCase();
	}
}
