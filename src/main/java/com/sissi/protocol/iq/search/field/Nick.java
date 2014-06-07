package com.sissi.protocol.iq.search.field;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.FieldValue;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.search.Search;

/**
 * @author kim 2014年6月6日
 */
@Metadata(uri = Search.XMLNS, localName = Nick.NAME)
@XmlRootElement(name = Nick.NAME)
public class Nick extends FieldValue {

	public final static Nick FIELD = new Nick();

	public final static String NAME = "nick";

	public Nick() {
		super();
	}

	public Nick(String text) {
		super(text);
	}

	@Override
	public String getName() {
		return NAME.toUpperCase();
	}
}
