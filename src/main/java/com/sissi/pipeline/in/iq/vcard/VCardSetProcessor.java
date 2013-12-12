package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class VCardSetProcessor implements Input {

	private VCardContext vcardContext;

	public VCardSetProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.vcardContext.push(context.getJid(), VCard.class.cast(protocol));
		return true;
	}

}
