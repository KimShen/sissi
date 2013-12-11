package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class VCardGet2SelfProcessor implements Input {

	private VCardContext vcardContext;

	public VCardGet2SelfProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.vcardContext.pull(context.getJid(), VCard.class.cast(protocol)).getParent().reply().setTo(context.getJid()).setType(Type.RESULT));
		return true;
	}
}
