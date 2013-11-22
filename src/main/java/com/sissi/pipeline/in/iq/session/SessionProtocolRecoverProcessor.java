package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.offline.StorageBox;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class SessionProtocolRecoverProcessor implements Input {

	private StorageBox storageBox;

	public SessionProtocolRecoverProcessor(StorageBox storageBox) {
		super();
		this.storageBox = storageBox;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (Protocol each : storageBox.pull(context.getJid().getBare())) {
			context.write(each);
		}
		return true;
	}
}
