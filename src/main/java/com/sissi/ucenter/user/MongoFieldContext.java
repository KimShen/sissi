package com.sissi.ucenter.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月12日
 */
abstract class MongoFieldContext {

	private final Log log = LogFactory.getLog(this.getClass());

	protected DBObject getEntities(Fields fields, BasicDBObjectBuilder builder) {
		for (Field<?> field : fields) {
			if (field.hasChild()) {
				this.embedOrNot(builder, field);
			} else {
				builder.add(field.getName(), field.getValue());
			}
		}
		DBObject entity = builder.get();
		this.log.debug("Entity: " + entity);
		return entity;
	}

	private void embedOrNot(BasicDBObjectBuilder builder, Field<?> field) {
		if (field.getChildren().isEmbed()) {
			this.getEntities(field.getChildren(), builder);
		} else {
			builder.add(field.getName(), this.getEntities(field.getChildren(), BasicDBObjectBuilder.start()));
		}
	}

}