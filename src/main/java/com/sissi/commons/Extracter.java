package com.sissi.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * @author kim 2014年2月23日
 */
public class Extracter {

	private final static List<?> list = Collections.unmodifiableList(new ArrayList<Object>());

	private final static Map<String, Object> map = Collections.unmodifiableMap(new HashMap<String, Object>());

	public static String asString(DBObject db, String key) {
		Object value = Extracter.as(db, key);
		return value != null ? value.toString() : null;
	}

	public static String asString(DBObject db, String key, String def) {
		Object value = Extracter.as(db, key);
		return value != null ? value.toString() : def;
	}

	@SuppressWarnings("unchecked")
	public static String[] asStrings(DBObject db, String key) {
		List<String> value = List.class.cast(db.get(key));
		return value != null ? value.toArray(new String[] {}) : null;
	}

	public static int asInt(DBObject db, String key) {
		Object value = Extracter.as(db, key);
		return value != null ? Integer.parseInt(value.toString()) : null;
	}

	public static int[] asInts(DBObject db, String key) {
		Object value = Extracter.as(db, key);
		return value != null ? ArrayUtils.toPrimitive(BasicDBList.class.cast(value).toArray(new Integer[] {})) : null;
	}

	public static long asLong(DBObject db, String key) {
		Object value = Extracter.as(db, key);
		return value != null ? Long.parseLong(value.toString()) : null;
	}

	public static boolean asBoolean(DBObject db, String key) {
		return Extracter.asBoolean(db, key, Boolean.FALSE);
	}

	public static boolean asBoolean(DBObject db, String key, boolean def) {
		Object value = Extracter.as(db, key);
		return value != null ? Boolean.class.cast(value) : def;
	}

	public static DBObject asDBObject(DBObject db, String key) {
		Object value = Extracter.as(db, key);
		return value != null ? DBObject.class.cast(value) : null;
	}

	public static List<?> asList(DBObject db, String key) {
		Object value = Extracter.as(db, key);
		return value != null ? List.class.cast(value) : Extracter.list;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> asMap(DBObject db) {
		return db != null ? db.toMap() : Extracter.map;
	}

	private static Object as(DBObject db, String key) {
		return db != null ? db.get(key) : null;
	}
}
