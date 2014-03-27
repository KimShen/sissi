package com.sissi.ucenter.muc;

import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年3月27日
 */
public interface MucConfigReviewer {

	public MucStatus review(Fields fields, MucStatus status);
}
