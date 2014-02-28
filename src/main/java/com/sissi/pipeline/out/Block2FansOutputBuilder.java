package com.sissi.pipeline.out;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.ucenter.user.BlockContext;
import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月6日
 */
public class Block2FansOutputBuilder extends BlockOutputBuilder {

	public Block2FansOutputBuilder(BlockContext context, JIDBuilder jidBuilder) {
		super(context, jidBuilder);
	}

	@Override
	public BlockOutput build(Transfer transfer) {
		return new Block2FansOutput();
	}

	private class Block2FansOutput extends BlockOutput {

		@Override
		protected JID user(JID current, Element node) {
			return Block2FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}

		@Override
		protected JID contacter(JID current, Element node) {
			return current;
		}
	}
}
