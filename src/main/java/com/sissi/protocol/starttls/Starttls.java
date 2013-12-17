package com.sissi.protocol.starttls;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月17日
 */
@MappingMetadata(uri = Starttls.XMLNS, localName = Starttls.NAME)
public class Starttls extends Protocol {

	public final static String NAME = "starttls";

	public final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-tls";
}
