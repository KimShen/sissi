package com.sissi.ucenter.relation;

import com.sissi.protocol.muc.Affiliation;
import com.sissi.protocol.muc.Role;
import com.sissi.ucenter.MucComputer;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2013年12月30日
 */
public class EqualMucComputer implements MucComputer {

	@Override
	public RelationMuc computer(RelationMuc relation) {
		return relation.set(Role.PARTICIPANT.toString(), Affiliation.MEMBER.toString());
	}
}
