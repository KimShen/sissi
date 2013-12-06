package com.sissi.pipeline.out;

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
public class Ban2FansOutputBuilder implements OutputBuilder {

	private BanContext context;

	private JIDBuilder jidBuilder;

	public Ban2FansOutputBuilder(BanContext context, JIDBuilder jidBuilder) {
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
			JID mayBan = Ban2FansOutputBuilder.this.jidBuilder.build(node.getFrom());
			if (this.isEmpty(context, mayBan)) {
				return true;
			}
			return !(this.isMatchNode(node) ? Ban2FansOutputBuilder.this.context.isBan(mayBan, context.getJid()) : false);
		}

		@Override
		public void close() {
		}

		private Boolean isEmpty(JID jid) {
			return (jid == null || jid.getUser() == null);
		}

		private boolean isEmpty(JIDContext context, JID mayBan) {
			return this.isEmpty(context.getJid()) || this.isEmpty(mayBan);
		}

		private Boolean isMatchNode(Element node) {
			return (node.getClass() == Presence.class) || (node.getClass() == Message.class);
		}
	}
}
