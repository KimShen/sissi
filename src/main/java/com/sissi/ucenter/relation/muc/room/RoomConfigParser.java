package com.sissi.ucenter.relation.muc.room;

import com.sissi.field.Field;

/**
 * 房间配置解析器
 * 
 * @author kim 2014年3月25日
 */
public interface RoomConfigParser {

	/**
	 * 解析
	 * 
	 * @param field 输入数据
	 * @return
	 */
	public Object parse(Field<?> field);

	/**
	 * 解析后的参数名称
	 * 
	 * @return
	 */
	public String field();

	/**
	 * 解析前的参数名称
	 * 
	 * @return
	 */
	public String support();
}
