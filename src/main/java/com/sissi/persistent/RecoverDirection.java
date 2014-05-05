package com.sissi.persistent;

/**
 * MUC历史消息服务数据流方向
 * 
 * @author kim 2014年4月16日
 */
public enum RecoverDirection {

	UP, DOWN;

	public static RecoverDirection parse(String type) {
		try {
			return type == null ? DOWN : RecoverDirection.valueOf(type.toUpperCase());
		} catch (Exception e) {
			return DOWN;
		}
	}
}