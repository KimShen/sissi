package com.sissi.pipeline.out;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.Transfer;
import com.sissi.protocol.Element;
import com.sissi.ucenter.block.BlockContext;

/**
 * 阻止推送给黑名单JID的XMPP节
 * 
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
		protected JID applicant(JID current, Element node) {
			return Block2FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}

		@Override
		protected JID verifier(JID current, Element node) {
			return current;
		}
	}
}
