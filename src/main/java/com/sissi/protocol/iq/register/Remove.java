package com.sissi.protocol.iq.register;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年5月9日
 */
@Metadata(uri = Register.XMLNS, localName = Remove.NAME)
@XmlRootElement(name = Remove.NAME)
public class Remove {

	public final static String NAME = "remove";
}
