package com.sissi.ucenter.relation.muc.apply.impl;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.field.Fields;
import com.sissi.ucenter.relation.muc.apply.ApplyContext;
import com.sissi.ucenter.relation.muc.apply.ApplySupport;

/**
 * @author kim 2014年5月4日
 */
public class ChainedApplyContext implements ApplyContext {

	private final List<ApplySupport> supports;

	public ChainedApplyContext(List<ApplySupport> supports) {
		super();
		this.supports = supports;
	}

	public boolean apply(JID invoker, JID target, Fields fields) {
		for (ApplySupport each : this.supports) {
			if (each.support(fields)) {
				return each.apply(invoker, target, fields);
			}
		}
		return false;
	}
}
