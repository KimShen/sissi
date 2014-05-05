package com.sissi.ucenter.relation.muc.validate.validator;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.ucenter.relation.muc.validate.ItemStatus;
import com.sissi.ucenter.relation.muc.validate.ItemValidator;
import com.sissi.ucenter.relation.muc.validate.status.ValidItemStatus;

/**
 * @author kim 2014年4月19日
 */
public class ChainedItemValidator implements ItemValidator {

	private final List<ItemValidator> validators;

	public ChainedItemValidator(List<ItemValidator> validators) {
		super();
		this.validators = validators;
	}

	@Override
	public ItemStatus valdate(JIDContext invoker, JID group, JID item) {
		for (ItemValidator each : this.validators) {
			ItemStatus error = each.valdate(invoker, group, item);
			if (!error.valid()) {
				return error;
			}

		}
		return ValidItemStatus.INSTANCE;
	}
}
