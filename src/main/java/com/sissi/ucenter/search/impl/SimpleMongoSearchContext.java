package com.sissi.ucenter.search.impl;

import java.util.List;

import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDBuilder;
import com.sissi.field.FieldMapping;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.search.field.Item;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * Simple搜索
 * 
 * @author kim 2014年6月8日
 */
public class SimpleMongoSearchContext extends MongoSearchContext {

	public SimpleMongoSearchContext(MongoConfig config, JIDBuilder jidBuilder, short limit, List<String> filters, FieldMapping mapping) {
		super(config, jidBuilder, limit, filters, mapping);
	}

	@Override
	protected Fields build(DBObject each, Fields target) {
		return target.add(new Item(super.jidBuilder.build(MongoUtils.asString(each, Dictionary.FIELD_USERNAME), null).asStringWithBare(), MongoUtils.asString(each, VCardContext.FIELD_NICK), null, null, null));
	}
}
