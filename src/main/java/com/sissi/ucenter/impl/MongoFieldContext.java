package com.sissi.ucenter.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月12日
 */
public class MongoFieldContext {

	protected DBObject getEntities(Fields fields, BasicDBObjectBuilder builder) {
		for (Field<?> field : fields) {
			if (field.hasChild()) {
				this.embedOrNot(builder, field);
			} else {
				builder.add(field.getName(), field.getValue());
			}
		}
		return builder.get();
	}

	private void embedOrNot(BasicDBObjectBuilder builder, Field<?> field) {
		if (field.getChildren().isEmbed()) {
			this.getEntities(field.getChildren(), builder);
		} else {
			builder.add(field.getName(), this.getEntities(field.getChildren(), BasicDBObjectBuilder.start()));
		}
	}

}