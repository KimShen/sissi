package com.sissi.ucenter.relation.muc.validate.validator;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ItemNotFound;
import com.sissi.ucenter.relation.muc.validate.ItemStatus;
import com.sissi.ucenter.relation.muc.validate.ItemValidator;
import com.sissi.ucenter.relation.muc.validate.status.ValidItemStatus;
import com.sissi.ucenter.relation.muc.validate.status.WrongItemStatus;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * Item存在性校验
 * 
 * @author kim 2014年4月19日
 */
public class ItemNotFoundValidator implements ItemValidator {

	private final ItemStatus status = new WrongItemStatus(new ServerError().setType(ProtocolType.CANCEL).add(ItemNotFound.DETAIL));

	private final VCardContext vcardContext;

	public ItemNotFoundValidator(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	/*
	 * Item必须存在VCard
	 * 
	 * @see com.sissi.ucenter.relation.muc.validate.ItemValidator#valdate(com.sissi.context.JIDContext, com.sissi.context.JID, com.sissi.context.JID)
	 */
	@Override
	public ItemStatus valdate(JIDContext invoker, JID group, JID item) {
		return this.vcardContext.exists(item) ? ValidItemStatus.INSTANCE : this.status;
	}
}
