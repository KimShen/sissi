package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "urn:xmpp:blocking", localName = "block")
@XmlRootElement
public class Blocked extends Block {

}