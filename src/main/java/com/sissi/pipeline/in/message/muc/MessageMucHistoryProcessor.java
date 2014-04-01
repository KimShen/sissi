package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Element;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.offline.Delay;
import com.sissi.ucenter.history.HistoryRecover;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年4月2日
 */
public class MessageMucHistoryProcessor extends ProxyProcessor {

	private final HistoryRecover historyRecover;

	private final MucConfigBuilder mucConfigBuilder;

	public MessageMucHistoryProcessor(HistoryRecover historyRecover, MucConfigBuilder mucConfigBuilder) {
		super();
		this.historyRecover = historyRecover;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		for (Element element : this.historyRecover.pull(group, protocol.cast(Message.class).getHistory())) {
			Delay delay = Message.class.cast(element).getDelay();
			delay.setFrom(config.allowed(context.jid(), MucConfig.HIDDEN_COMPUTER, super.build(delay.getFrom())) ? super.build(element.getFrom()).asStringWithBare() : delay.getFrom());
			context.write(element);
		}
		return true;
	}
}
