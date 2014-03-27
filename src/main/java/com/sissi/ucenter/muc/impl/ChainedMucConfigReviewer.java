package com.sissi.ucenter.muc.impl;

import java.util.List;

import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.muc.MucConfigReviewer;
import com.sissi.ucenter.muc.MucStatus;

/**
 * @author kim 2014年3月27日
 */
public class ChainedMucConfigReviewer implements MucConfigReviewer {

	private final List<MucConfigReviewer> reviews;

	public ChainedMucConfigReviewer(List<MucConfigReviewer> reviews) {
		super();
		this.reviews = reviews;
	}

	@Override
	public MucStatus review(Fields fields, MucStatus status) {
		for (MucConfigReviewer review : reviews) {
			review.review(fields, status);
		}
		return status;
	}
}
