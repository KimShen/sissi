package com.sissi.ucenter.user;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoException;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.protocol.iq.register.simple.Username;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.relation.MongoFieldContext;

/**
 * @author kim 2013年12月3日
 */
public class MongoRegisterContext extends MongoFieldContext implements RegisterContext {

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoRegisterContext(MongoConfig config, JIDBuilder jidBuilder) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean register(Fields fields) {
		try {
			return this.valid(fields) ? (this.config.collection().save(super.getEntities(fields, BasicDBObjectBuilder.start())).getError() == null) : false;
		} catch (MongoException e) {
			return false;
		}
	}

	private boolean valid(Fields fields) {
		Field<?> register = fields.findField(Username.NAME, Field.class);
		return register.hasChild() ? this.validUsername(this.extractUsername(register)) : this.validUsername(String.class.cast(register.getValue()));
	}

	private boolean validUsername(String username) {
		return username != null && !username.isEmpty() && this.jidBuilder.build(username, null).valid(true);
	}
	
	private String extractUsername(Field<?> register) {
		for (Field<?> field : register.getChildren()) {
			if (field.getClass() == XValue.class) {
				return String.class.cast(field.getValue());
			}
		}
		return null;
	}
}
