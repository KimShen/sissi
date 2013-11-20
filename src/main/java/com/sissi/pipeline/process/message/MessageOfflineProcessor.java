package com.sissi.pipeline.process.message;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.offline.StorageBox;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-15
 */
public class MessageOfflineProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	private StorageBox storageBox;

	public MessageOfflineProcessor(JIDBuilder jidBuilder, Addressing addressing, StorageBox storageBox) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.storageBox = storageBox;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		if (protocol.getTo() != null && !this.addressing.isOnline(this.jidBuilder.build(protocol.getTo()))) {
			this.storageBox.close(protocol);
			return false;
		}
		return true;
	}
}
