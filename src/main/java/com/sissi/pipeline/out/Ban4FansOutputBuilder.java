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
public class Ban4FansOutputBuilder extends BanOutputBuilder {

	public Ban4FansOutputBuilder(BanContext context, JIDBuilder jidBuilder) {
		super(context, jidBuilder);
	}

	@Override
	protected BanOutput buildBan(Transfer writeable) {
		return new Ban4FansOutput();
	}

	private class Ban4FansOutput extends BanOutput {

		@Override
		protected JID user(JIDContext context, Element node) {
			return context.getJid();
		}

		@Override
		protected JID contacter(JIDContext context, Element node) {
			return Ban4FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}
	}
}
