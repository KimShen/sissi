package com.sissi.pipeline.out;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.ucenter.BlockContext;
import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月6日
 */
public class Block2FansOutputBuilder extends BlockOutputBuilder {

	public Block2FansOutputBuilder(BlockContext context, JIDBuilder jidBuilder) {
		super(context, jidBuilder);
	}

	@Override
	protected BlockOutput buildBlock(Transfer writeable) {
		return new Ban2FansOutput();
	}

	private class Ban2FansOutput extends BlockOutput {

		@Override
		protected JID user(JIDContext context, Element node) {
			return Block2FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}

		@Override
		protected JID contacter(JIDContext context, Element node) {
			return context.getJid();
		}
	}
}
