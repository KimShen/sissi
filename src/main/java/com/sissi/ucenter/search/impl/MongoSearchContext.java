package com.sissi.ucenter.search.impl;

import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDBuilder;
import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XItem;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.ucenter.impl.MongoFieldsContext;
import com.sissi.ucenter.search.SearchContext;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * @author kim 2014年6月6日
 */
public class MongoSearchContext extends MongoFieldsContext implements SearchContext {

	private final short limit;

	private final DBObject filter;

	private final MongoConfig config;

	private final List<String> blocks;

	private final JIDBuilder jidBuilder;

	public MongoSearchContext(MongoConfig config, JIDBuilder jidBuilder, short limit, List<String> blocks) {
		super();
		this.limit = limit;
		this.config = config;
		this.blocks = blocks;
		this.filter = this.filter();
		this.jidBuilder = jidBuilder;
	}

	@Override
	public <T extends Fields> T search(Fields source, Fields target, Class<T> clazz) {
		try (DBCursor cursor = this.config.collection().find(this.build(source), this.filter).limit(this.limit)) {
			while (cursor.hasNext()) {
				DBObject each = cursor.next();
				target.add(Field.class.cast(new XItem().add(new XField().setVar(Dictionary.FIELD_JID).add(new XValue(this.jidBuilder.build(MongoUtils.asString(each, Dictionary.FIELD_USERNAME), null).asStringWithBare()))).add(new XField().setVar(VCardContext.FIELD_NICKNAME).add(new XValue(MongoUtils.asString(each, VCardContext.FIELD_NICKNAME))))));
			}
		}
		return clazz.cast(target);
	}

	private DBObject filter() {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		for (String block : blocks) {
			builder.add(block, 0);
		}
		return builder.get();
	}

	private DBObject build(Fields source) {
		DBObject query = super.entities(source, BasicDBObjectBuilder.start());
		for (String block : this.blocks) {
			query.removeField(block);
		}
		return query;
	}
}
