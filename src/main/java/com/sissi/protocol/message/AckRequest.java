package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月3日
 */
@Metadata(uri = Message.XMLNS, localName = AckRequest.NAME)
@XmlRootElement
public class AckRequest extends Ack {

	public final static AckRequest REQUEST = new AckRequest();

	public final static String NAME = "request";

	private AckRequest() {

	}
}
