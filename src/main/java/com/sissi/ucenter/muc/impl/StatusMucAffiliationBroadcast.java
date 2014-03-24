package com.sissi.ucenter.muc.impl;

import com.sissi.addressing.Addressing;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucAffiliationBroadcast;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucItem;
import com.sissi.ucenter.muc.MucRelationContext;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月24日
 */
public class StatusMucAffiliationBroadcast implements MucAffiliationBroadcast {

	private final Addressing addressing;

	private final MucStatusJudger mucStatusJudger;

	private final MucRelationContext mucRelationContext;

	public StatusMucAffiliationBroadcast(Addressing addressing, MucStatusJudger mucStatusJudger, MucRelationContext mucRelationContext) {
		super();
		this.addressing = addressing;
		this.mucStatusJudger = mucStatusJudger;
		this.mucRelationContext = mucRelationContext;
	}

	@Override
	public MucAffiliationBroadcast broadcast(JID jid, JID group, JIDContext invoker, MucItem item, MucConfig config) {
		for (Relation relation : this.mucRelationContext.ourRelations(jid, group)) {
			for (JID to : this.mucRelationContext.whoSubscribedMe(group)) {
				this.addressing.findOne(to, true).write(item.presence(group.resource(relation.name()), config.pull(MongoConfig.FIELD_AFFILIATION, String.class)).reset().add(this.mucStatusJudger.judege(new XUser(group, to, config.allowed(to, MucConfig.HIDDEN_NATIVE, null)).item(item.hidden(config.allowed(to, MucConfig.HIDDEN_COMPUTER, jid)).relation(relation.cast(RelationMuc.class).affiliation(item.getAffiliation())))).cast(XUser.class)));
			}
		}
		return this;
	}
}
