package com.sissi.pipeline.in.iq.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * @author kim 2014年3月14日
 */
public class MucSetRoleMatcher extends ClassMatcher {

	private final ItemRole role;

	public MucSetRoleMatcher(String role) {
		super(XMucAdmin.class);
		this.role = ItemRole.parse(role);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.role.equals(protocol.cast(XMucAdmin.class).getItem().getRole());
	}
}
