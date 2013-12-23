package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = Block.XMLNS, localName = Blocked.NAME)
@XmlType(namespace = Block.XMLNS)
@XmlRootElement
public class Blocked extends Block {

	public final static String NAME = "block";
}