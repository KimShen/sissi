package com.sissi.ucenter.search.impl;

import java.util.List;

import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDBuilder;
import com.sissi.field.Field;
import com.sissi.field.FieldMapping;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XItem;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 表单搜索
 * 
 * @author kim 2014年6月8日
 */
public class MultiMongoSearchContext extends MongoSearchContext {

	public MultiMongoSearchContext(MongoConfig config, JIDBuilder jidBuilder, short limit, List<String> filters, FieldMapping mapping) {
		super(config, jidBuilder, limit, filters, mapping);
	}

	@Override
	protected Fields build(DBObject each, Fields target) {
		return target.add(Field.class.cast(new XItem().add(new XField().setVar(Dictionary.FIELD_JID).add(new XValue(super.jidBuilder.build(MongoUtils.asString(each, Dictionary.FIELD_USERNAME), null).asStringWithBare()))).add(new XField().setVar(VCardContext.FIELD_NICK.toLowerCase()).add(new XValue(MongoUtils.asString(each, VCardContext.FIELD_NICK))))));
	}
}
