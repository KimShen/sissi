package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.history.HistoryRecover;

/**
 * @author kim 2014年3月7日
 */
public class PresenceMucJoin2SelfMessageHistoryProcessor extends ProxyProcessor {

	private final HistoryRecover historyRecover;

	public PresenceMucJoin2SelfMessageHistoryProcessor(HistoryRecover historyRecover) {
		super();
		this.historyRecover = historyRecover;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMuc x = protocol.cast(Presence.class).findField(XMuc.NAME, XMuc.class);
		if (x.isHistory()) {
			context.write(this.historyRecover.pull(super.build(protocol.getTo()), x.history()));
		}
		return true;
	}
}
