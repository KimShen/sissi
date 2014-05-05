package com.sissi.ucenter.relation.muc.room.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.RelationContext;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;

/**
 * 索引策略1: {"jid":1}</p>索引策略2: {"affiliations.jid":1}
 * 
 * @author kim 2014年2月20日
 */
public class MongoRoomBuilder implements RoomBuilder {

	private final Map<String, Object> configs = new HashMap<String, Object>();

	private final Map<String, RoomConfigParser> parsers = new HashMap<String, RoomConfigParser>();

	private final Map<RoomConfig, RoomConfigApprover> readers = new HashMap<RoomConfig, RoomConfigApprover>();

	/**
	 * {"$unwind":"$affiliations"}
	 */
	private final DBObject unwind = BasicDBObjectBuilder.start().add("$unwind", "$" + Dictionary.FIELD_AFFILIATIONS).get();

	/**
	 * {"configs":1,"creator":1,"activate":1}
	 */
	private final DBObject filter = BasicDBObjectBuilder.start().add(Dictionary.FIELD_CONFIGS, 1).add(Dictionary.FIELD_CREATOR, 1).add(Dictionary.FIELD_ACTIVATE, 1).get();

	/**
	 * {"$project":{"nick":"$affiliations.nick"}}
	 */
	private final DBObject project = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start(Dictionary.FIELD_NICK, "$" + Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_NICK).get()).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private final RelationContext relationContext;

	public MongoRoomBuilder(MongoConfig config, JIDBuilder jidBuilder, RelationContext relationContext, List<RoomConfigParser> parsers, List<RoomConfigApprover> readers) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.relationContext = relationContext;
		for (RoomConfigParser each : parsers) {
			this.parsers.put(each.support(), each);
		}
		for (RoomConfigApprover each : readers) {
			this.readers.put(each.support(), each);
		}
	}

	/*
	 * {"jid":group.bare},{"configs":1,"creator":1,"activate":1}
	 * 
	 * @see com.sissi.ucenter.relation.muc.room.RoomBuilder#build(com.sissi.context.JID)
	 */
	@Override
	public Room build(JID group) {
		DBObject db = this.config.collection().findOne(BasicDBObjectBuilder.start(Dictionary.FIELD_JID, group.asStringWithBare()).get(), this.filter);
		return new MongoRoom(group, this.jidBuilder.build(MongoUtils.asString(db, Dictionary.FIELD_CREATOR)), MongoUtils.asDBObject(db, Dictionary.FIELD_CONFIGS));
	}

	private class MucConfigParamImpl implements RoomConfigParam {

		private final JID user;

		private final JID group;

		private final JID creator;

		private final DBObject configs;

		public MucConfigParamImpl(JID user, JID group, JID creator, DBObject configs) {
			super();
			this.user = user;
			this.group = group;
			this.creator = creator;
			this.configs = configs;
		}

		public JID user() {
			return this.user;
		}

		public boolean affiliation() {
			return this.affiliation(MongoUtils.asString(this.configs, Dictionary.FIELD_AFFILIATION));
		}

		public boolean affiliation(String affiliation) {
			return this.creator() || ItemAffiliation.parse(this.relation().affiliation()).contains(affiliation);
		}

		public boolean hidden(boolean compute) {
			boolean whois = MongoUtils.asBoolean(this.configs, Dictionary.FIELD_WHOIS);
			return compute ? !this.creator() && !ItemRole.parse(MongoRoomBuilder.this.relationContext.ourRelation(this.user, this.group).cast(MucRelation.class).role()).contains(ItemRole.MODERATOR) && whois : whois;
		}

		/*
		 * JID为创建者或房间不存在
		 * 
		 * @see com.sissi.ucenter.relation.muc.room.RoomConfigParam#creator()
		 */
		@Override
		public boolean creator() {
			return this.user.like(this.creator) || MongoRoomBuilder.this.config.collection().findOne(BasicDBObjectBuilder.start(Dictionary.FIELD_JID, this.group.asStringWithBare()).get()) == null;
		}

		public boolean activate(boolean compute) {
			boolean activate = MongoUtils.asBoolean(this.configs, Dictionary.FIELD_ACTIVATE, true);
			return compute ? this.creator() || activate : activate;
		}

		public MucRelation relation() {
			return MongoRoomBuilder.this.relationContext.ourRelation(this.user, this.group).cast(MucRelation.class);
		}

		@SuppressWarnings("unchecked")
		public Map<String, Object> configs() {
			return this.configs != null ? this.configs.toMap() : MongoRoomBuilder.this.configs;
		}
	}

	private class MongoRoom implements Room {

		private final JID group;

		private final JID creator;

		private final DBObject configs;

		private MongoRoom(JID group, JID creator, DBObject configs) {
			super();
			this.group = group;
			this.creator = creator;
			this.configs = configs;
		}

		/**
		 * {"jid":group.bare}
		 * 
		 * @return
		 */
		private DBObject build() {
			return BasicDBObjectBuilder.start(Dictionary.FIELD_JID, this.group.asStringWithBare()).get();
		}

		public <T> T pull(String key, Class<T> clazz) {
			return this.configs != null ? clazz.cast(this.configs.get(key)) : null;
		}

		/*
		 * {"jid":group.bare}
		 * 
		 * @see com.sissi.ucenter.relation.muc.room.Room#destory()
		 */
		public Room destory() {
			MongoRoomBuilder.this.config.collection().remove(this.build());
			return this;
		}

		/*
		 * {"$set":{"configs.Xxx1":Xxx,"configs.Xxx2":Xxx}}
		 * 
		 * @see com.sissi.ucenter.relation.muc.room.Room#push(com.sissi.field.Fields)
		 */
		public Room push(Fields fields) {
			BasicDBObjectBuilder update = BasicDBObjectBuilder.start();
			for (Field<?> field : fields) {
				RoomConfigParser parser = MongoRoomBuilder.this.parsers.get(field.getName());
				if (parser != null) {
					update.add(Dictionary.FIELD_CONFIGS + "." + parser.field(), parser.parse(field));
				}
			}
			if (!update.isEmpty()) {
				MongoRoomBuilder.this.config.collection().update(this.build(), BasicDBObjectBuilder.start("$set", update.get()).get());
			}
			return this;
		}

		/*
		 * {"$set":{"configs.Xxx":Xxx}}
		 * 
		 * @see com.sissi.ucenter.relation.muc.room.Room#push(com.sissi.field.Field)
		 */
		public Room push(Field<?> field) {
			RoomConfigParser parser = MongoRoomBuilder.this.parsers.get(field.getName());
			if (parser != null) {
				MongoRoomBuilder.this.config.collection().update(this.build(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS + "." + parser.field(), parser.parse(field)).get()).get());
			}
			return this;
		}

		public String reserved(JID jid) {
			// {"$match":{"jid":group.bare}}, {"$unwind":"$affiliations"}, {"$match":{"affiliations.jid":jid.bare}}, {"$project":{"nick":"$affiliations.nick"}}
			AggregationOutput output = MongoRoomBuilder.this.config.collection().aggregate(BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_JID, this.group.asStringWithBare()).get()).get(), MongoRoomBuilder.this.unwind, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, jid.asStringWithBare()).get()).get(), MongoRoomBuilder.this.project);
			List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
			return result.isEmpty() ? null : MongoUtils.asString(DBObject.class.cast(result.get(0)), Dictionary.FIELD_NICK);
		}

		@Override
		public boolean allowed(JID jid, RoomConfig key) {
			return this.allowed(jid, key, null);
		}

		@Override
		public boolean allowed(JID jid, RoomConfig key, Object value) {
			RoomConfigApprover approver = MongoRoomBuilder.this.readers.get(key);
			return approver != null ? approver.approve(new MucConfigParamImpl(jid, this.group, this.creator, this.configs), value) : false;
		}
	}
}
