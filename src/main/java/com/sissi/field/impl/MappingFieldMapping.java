package com.sissi.field.impl;

import java.util.Map;
import java.util.Set;

import com.sissi.field.Field;
import com.sissi.field.FieldMapping;

/**
 * 返回Field.name映射后的名称,如果不存在则直接返回Field.name
 * 
 * @author kim 2014年6月7日
 */
public class MappingFieldMapping implements FieldMapping {

	private final Map<String, String> mapping;

	private final Set<String> blocks;

	/**
	 * @param mapping
	 * @param blocks 黑名单
	 */
	public MappingFieldMapping(Map<String, String> mapping, Set<String> blocks) {
		super();
		this.blocks = blocks;
		this.mapping = mapping;
	}

	@Override
	public String mapping(Field<?> field) {
		if (this.blocks.contains(field.getName())) {
			return null;
		}
		String name = this.mapping.get(field.getName());
		return name != null ? name : field.getName();
	}
}
