package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月6日
 */
@MappingMetadata(uri = "urn:xmpp:blocking", localName = "unblock")
@XmlRootElement(name = "unblock")
public class UnBlock extends Block {

}