package com.sissi.config;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * @author kim 2014年1月22日
 */
public interface MongoCollection {

	public WriteResult save(DBObject entity);

	public WriteResult save(DBObject entity, WriteConcern concern);

	public WriteResult update(DBObject query, DBObject entity);

	public WriteResult update(DBObject query, DBObject entity, Boolean upsert, Boolean batch);

	public WriteResult update(DBObject query, DBObject entity, Boolean upsert, Boolean batch, WriteConcern concern);

	public WriteResult remove(DBObject query);

	public DBCursor find(DBObject query);

	public DBCursor find(DBObject query, DBObject filter);

	public DBObject findOne(DBObject query);

	public DBObject findOne(DBObject query, DBObject filter);
}
