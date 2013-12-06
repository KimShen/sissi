package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Stream;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "urn:xmpp:blocking", localName = "block")
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement
public class Blocked extends Block {

}