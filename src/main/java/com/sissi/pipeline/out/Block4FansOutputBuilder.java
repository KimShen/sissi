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
public class Block4FansOutputBuilder extends BlockOutputBuilder {

	public Block4FansOutputBuilder(BlockContext context, JIDBuilder jidBuilder) {
		super(context, jidBuilder);
	}

	@Override
	public BlockOutput build(Transfer transfer)  {
		return new Block4FansOutput();
	}

	private class Block4FansOutput extends BlockOutput {

		@Override
		protected JID user(JIDContext context, Element node) {
			return context.getJid();
		}

		@Override
		protected JID contacter(JIDContext context, Element node) {
			return Block4FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}
	}
}
