package com.sissi.pipeline.out;

import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.ucenter.BanContext;
import com.sissi.write.Writer.Transfer;

/**
 * @author kim 2013年12月6日
 */
public class Ban2FansOutputBuilder extends BanOutputBuilder {

	public Ban2FansOutputBuilder(BanContext context, JIDBuilder jidBuilder) {
		super(context, jidBuilder);
	}

	@Override
	protected BanOutput buildBan(Transfer writeable) {
		return new Ban2FansOutput();
	}

	private class Ban2FansOutput extends BanOutput {

		@Override
		protected JID user(JIDContext context, Element node) {
			return Ban2FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}

		@Override
		protected JID contacter(JIDContext context, Element node) {
			return context.getJid();
		}
	}
}
