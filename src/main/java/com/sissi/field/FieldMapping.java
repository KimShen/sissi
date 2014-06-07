package com.sissi.field;

/**
 * 属性名映射
 * 
 * @author kim 2014年6月7日
 */
public interface FieldMapping {

	/**
	 * @param field
	 * @return 映射后的Field.name
	 */
	public String mapping(Field<?> field);
}
