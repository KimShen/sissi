package com.sissi.protocol.iq.bytestreams;

import java.util.Comparator;

/**
 * @author kim 2013年12月25日
 */
public class StreamhostComparator implements Comparator<Streamhost> {

	private BytestreamsProxy proxy;

	public StreamhostComparator(BytestreamsProxy proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public int compare(Streamhost stream1, Streamhost stream2) {
		return this.proxy.getJid().equals(stream1.getJid()) ? -1 : 1;
	}
}
