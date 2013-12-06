package com.sissi.pipeline.out;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.Output.OutputBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.BanContext;
import com.sissi.write.Writer.Transfer;

/**
 * @author kim 2013年12月6日
 */
public class BanOutputBuilder implements OutputBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private BanContext context;

	private JIDBuilder jidBuilder;

	public BanOutputBuilder(BanContext context, JIDBuilder jidBuilder) {
		super();
		this.context = context;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public Output build(Transfer writeable) {
		return new BanOutput();
	}

	private class BanOutput implements Output {

		@Override
		public Boolean output(JIDContext context, Element node) {
			JID mayBan = BanOutputBuilder.this.jidBuilder.build(node.getFrom());
			if (this.isEmpty(context.getJid()) || this.isEmpty(mayBan)) {
				return true;
			}
			Boolean isBan = this.isMatchNode(node) ? BanOutputBuilder.this.context.isBan(context.getJid(), mayBan) : false;
			if (isBan) {
				BanOutputBuilder.this.log.warn("Ban on " + context.getJid().asString() + " / " + node.getFrom());
			}
			return !isBan;
		}

		@Override
		public void close() {
		}

		private Boolean isEmpty(JID jid) {
			return (jid == null || jid.getUser() == null);
		}

		private Boolean isMatchNode(Element node) {
			return (node.getClass() == Presence.class) || (node.getClass() == Message.class);
		}
	}
}
