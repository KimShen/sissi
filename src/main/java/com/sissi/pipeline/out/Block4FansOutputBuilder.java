package com.sissi.pipeline.out;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.Transfer;
import com.sissi.protocol.Element;
import com.sissi.ucenter.block.BlockContext;

/**
 * 阻止来自于黑名单JID的XMPP节
 * 
 * @author kim 2013年12月6日
 */
public class Block4FansOutputBuilder extends BlockOutputBuilder {

	public Block4FansOutputBuilder(BlockContext context, JIDBuilder jidBuilder) {
		super(context, jidBuilder);
	}

	@Override
	public BlockOutput build(Transfer transfer) {
		return new Block4FansOutput();
	}

	private class Block4FansOutput extends BlockOutput {

		@Override
		protected JID applicant(JID current, Element node) {
			return current;
		}

		@Override
		protected JID verifier(JID current, Element node) {
			return Block4FansOutputBuilder.super.jidBuilder.build(node.getFrom());
		}
	}
}
