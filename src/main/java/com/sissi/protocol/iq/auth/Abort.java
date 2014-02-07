package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年1月6日
 */
@Metadata(uri = Auth.XMLNS, localName = Abort.NAME)
@XmlRootElement
public class Abort extends Protocol {

	public final static String NAME = "abort";

}
