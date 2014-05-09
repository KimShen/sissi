package com.sissi.ucenter.register.impl;

import java.util.Set;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDBuilder;
import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.protocol.iq.register.simple.Username;
import com.sissi.ucenter.impl.MongoFieldsContext;
import com.sissi.ucenter.register.RegisterContext;

/**
 * 索引策略: {"username":1}
 * 
 * @author kim 2013年12月3日
 */
public class MongoRegisterContext extends MongoFieldsContext implements RegisterContext {

	private final DBObject remove = BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_ACTIVATE, false).get()).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	/**
	 * 保留用户名
	 */
	private final Set<String> reserved;

	/**
	 * @param reserved 保留用户名
	 * @param config
	 * @param jidBuilder
	 */
	public MongoRegisterContext(Set<String> reserved, MongoConfig config, JIDBuilder jidBuilder) {
		super();
		this.config = config;
		this.reserved = reserved;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean register(String username, Fields fields) {
		try {
			return this.valid(fields) ? MongoUtils.effect(this.config.collection().update(BasicDBObjectBuilder.start(Dictionary.FIELD_USERNAME, username).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(super.entities(fields, BasicDBObjectBuilder.start()).toMap()).add(Dictionary.FIELD_ACTIVATE, true).get()).get(), true, false, WriteConcern.SAFE)) : false;
		} catch (MongoException e) {
			return false;
		}
	}

	public boolean unregister(String username) {
		return MongoUtils.effect(this.config.collection().update(BasicDBObjectBuilder.start(Dictionary.FIELD_USERNAME, username).get(), this.remove, false, false, WriteConcern.SAFE));
	}

	private boolean valid(Fields fields) {
		Field<?> register = fields.findField(Username.NAME, Field.class);
		// 简易表单/复杂表单
		return register.hasChild() ? this.username(this.username(register)) : this.username(String.class.cast(register.getValue()));
	}

	/**
	 * 用户名有效性校验.1,不为Null 2,不为空 3,JIDBuilder.value(true) 4,非保留用户名
	 * 
	 * @param username
	 * @return
	 */
	private boolean username(String username) {
		return username != null && !username.isEmpty() && this.jidBuilder.build(username, null).valid(true) && !this.reserved.contains(username);
	}

	/**
	 * 获取用户名
	 * 
	 * @param register
	 * @return
	 */
	private String username(Field<?> register) {
		for (Field<?> field : register.getChildren()) {
			if (field.getClass() == XValue.class) {
				return String.class.cast(field.getValue());
			}
		}
		return null;
	}
}
