package com.sissi.pipeline.in.iq.muc.owner.submit;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.muc.Owner;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.relation.muc.status.CodeStatusExtracter;

/**
 * 提交配置, 通过Message广播状态码
 * 
 * @author kim 2014年3月27日
 */
public class MucOwnerSubmitWarningProcessor extends ProxyProcessor {

	private final CodeStatusExtracter extracter;

	public MucOwnerSubmitWarningProcessor(CodeStatusExtracter extracter) {
		super();
		this.extracter = extracter;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XUser user = this.extracter.extract(protocol.cast(Owner.class).getX(), new XUser()).cast(XUser.class);
		if (user.contain()) {
			JID group = super.build(protocol.parent().getTo());
			Protocol message = new Message().noneThread().muc(user).setType(MessageType.GROUPCHAT).setFrom(group);
			for (JID jid : super.whoSubscribedMe(group)) {
				super.findOne(jid, true).write(message);
			}
		}
		return true;
	}
}
