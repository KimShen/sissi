package com.sissi.ucenter.relation.muc.apply.register;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XField;
import com.sissi.ucenter.relation.muc.apply.RequestConfig;

/**
 * 拒绝更新个人信息请求(Information).索引策略:{"jid":1,"affiliations.jid":1}
 * 
 * @author kim 2014年5月4日
 */
public class MongoRegisterRejectContext extends RegisterApplyContext {

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoRegisterRejectContext(MongoConfig config, JIDBuilder jidBuilder) {
		super("0");
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean apply(JID invoker, JID target, Fields fields) {
		JID jid = this.jidBuilder.build(fields.findField(RequestConfig.JID.toString(), XField.class).getValue().toString());
		this.config.collection().update(BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, target.asStringWithBare()).add(Dictionary.FIELD_INFORMATIONS + "." + Dictionary.FIELD_JID, jid.asStringWithBare()).get(), BasicDBObjectBuilder.start("$pull", BasicDBObjectBuilder.start(Dictionary.FIELD_INFORMATIONS, BasicDBObjectBuilder.start(Dictionary.FIELD_JID, jid.asStringWithBare()).get()).get()).get());
		return false;
	}
}
