package com.sissi.ucenter.relation;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月12日
 */
abstract public class MongoFieldContext {

	protected DBObject getEntities(Fields fields, BasicDBObjectBuilder builder) {
		for (Field<?> field : fields) {
			this.getEntity(field, builder, false);
		}
		return builder.get();
	}

	protected DBObject getEntity(Field<?> field, BasicDBObjectBuilder builder) {
		return this.getEntity(field, builder, true);
	}

	private DBObject getEntity(Field<?> field, BasicDBObjectBuilder builder, boolean build) {
		if (field.hasChild()) {
			this.embedOrNot(builder, field);
		} else {
			builder.add(field.getName(), field.getValue());
		}
		return build ? builder.get() : null;
	}

	private void embedOrNot(BasicDBObjectBuilder builder, Field<?> field) {
		Fields fields = field.getChildren();
		if (fields.isEmbed()) {
			this.getEntities(fields, builder);
		} else {
			builder.add(field.getName(), this.getEntities(fields, BasicDBObjectBuilder.start()));
		}
	}

}