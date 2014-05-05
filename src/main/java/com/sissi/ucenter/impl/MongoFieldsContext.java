package com.sissi.ucenter.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.field.Field;
import com.sissi.field.Fields;

/**
 * Fields提取器
 * 
 * @author kim 2013年12月12日
 */
abstract public class MongoFieldsContext {

	protected DBObject entities(Fields fields, BasicDBObjectBuilder builder) {
		for (Field<?> field : fields) {
			this.entity(field, builder, false);
		}
		return builder.get();
	}

	protected DBObject entity(Field<?> field, BasicDBObjectBuilder builder) {
		return this.entity(field, builder, true);
	}

	/**
	 * @param field
	 * @param builder
	 * @param build 是否构建并返回
	 * @return
	 */
	private DBObject entity(Field<?> field, BasicDBObjectBuilder builder, boolean build) {
		if (field.hasChild()) {
			this.embed(field, builder);
		} else {
			// 如果不存在Field子节点则保存值并终止
			builder.add(field.getName(), field.getValue());
		}
		return build ? builder.get() : null;
	}

	private void embed(Field<?> field, BasicDBObjectBuilder builder) {
		Fields fields = field.getChildren();
		if (fields.isEmbed()) {
			this.entities(fields, builder);
		} else {
			// 非嵌入式则创建新Key保存Field子节点,Key = Field.name
			builder.add(field.getName(), this.entities(fields, BasicDBObjectBuilder.start()));
		}
	}

}