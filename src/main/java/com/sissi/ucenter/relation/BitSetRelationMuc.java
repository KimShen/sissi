package com.sissi.ucenter.relation;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;

import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月14日
 */
abstract class BitSetRelationMuc implements RelationMuc {

	private final String subscription;

	private final BitSet bitSet;

	public BitSetRelationMuc(String subscription) {
		super();
		this.subscription = subscription;
		try {
			this.bitSet = BitSet.valueOf(this.subscription.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public BitSetRelationMuc(byte[] subscription) {
		super();
		this.bitSet = BitSet.valueOf(subscription);
		try {
			this.subscription = new String(subscription, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getSubscription() {
		return this.subscription;
	}

	@Override
	public String getRole() {
		return ItemRole.toString(this.bitSet.nextClearBit(128) - 1);
	}

	@Override
	public String getAffiliaion() {
		return ItemAffiliation.toString(this.bitSet.nextClearBit(0) - 1);
	}
}
