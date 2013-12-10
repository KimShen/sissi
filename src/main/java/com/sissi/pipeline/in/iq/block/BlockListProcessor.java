package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.block.BlockList;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.ucenter.BanContext;

/**
 * @author kim 2013年12月6日
 */
public class BlockListProcessor extends UtilProcessor {

	private final BanContext banContext;

	public BlockListProcessor(BanContext banContext) {
		super();
		this.banContext = banContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		BlockList list = BlockList.class.cast(protocol);
		for (String each : this.banContext.iBanedWho(context.getJid())) {
			list.add(new Item().setJid(super.build(each, null).asStringWithBare()));
		}
		context.write(list.getParent().reply().setType(Type.RESULT));
		return true;
	}
}
