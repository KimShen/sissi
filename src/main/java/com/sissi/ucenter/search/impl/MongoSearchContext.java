package com.sissi.ucenter.search.impl;

import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.field.FieldMapping;
import com.sissi.field.Fields;
import com.sissi.ucenter.impl.MongoFieldsContext;
import com.sissi.ucenter.search.SearchContext;

/**
 * 索引策略: 动态,由FieldMapping所能解析的Field.name决定
 * 
 * @author kim 2014年6月6日
 */
abstract class MongoSearchContext extends MongoFieldsContext implements SearchContext {

	private final short limit;

	private final DBObject filter;

	private final MongoConfig config;

	protected final JIDBuilder jidBuilder;

	public MongoSearchContext(MongoConfig config, JIDBuilder jidBuilder, short limit, List<String> filters, FieldMapping mapping) {
		super(mapping);
		this.limit = limit;
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.filter = this.filter(filters);
	}

	private DBObject filter(List<String> filters) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		for (String filter : filters) {
			builder.add(filter, 0);
		}
		return builder.get();
	}

	@Override
	public <T extends Fields> T search(Fields source, Fields target, Class<T> clazz) {
		try (DBCursor cursor = this.config.collection().find(super.entities(source, BasicDBObjectBuilder.start()), this.filter).limit(this.limit)) {
			while (cursor.hasNext()) {
				this.build(cursor.next(), target);
			}
		}
		return clazz.cast(target);
	}

	abstract protected Fields build(DBObject each, Fields target);
}
