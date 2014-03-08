package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Element;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.offline.Delay;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.history.HistoryRecover;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月7日
 */
public class PresenceMucJoin2SelfMessageHistoryProcessor extends ProxyProcessor {

	private final HistoryRecover historyRecover;

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucJoin2SelfMessageHistoryProcessor(HistoryRecover historyRecover, MucConfigBuilder mucConfigBuilder) {
		super();
		this.historyRecover = historyRecover;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMuc x = protocol.cast(Presence.class).findField(XMuc.NAME, XMuc.class);
		if (x.hasHistory()) {
			JID group = super.build(protocol.getTo());
			MucConfig config = this.mucConfigBuilder.build(group);
			for (Element element : this.historyRecover.pull(group, x.history())) {
				Delay delay = Message.class.cast(element).getDelay();
				delay.setFrom(config.allowed(context.jid(), MucConfig.HIDDEN_COMPUTER, super.build(delay.getFrom())) ? super.build(element.getFrom()).asStringWithBare() : delay.getFrom());
				context.write(element);
			}
		}
		return true;
	}
}
