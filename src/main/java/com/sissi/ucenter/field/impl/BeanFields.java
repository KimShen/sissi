package com.sissi.ucenter.field.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月11日
 */
public class BeanFields implements Fields {

	private final static ArrayList<Field<?>> EMPTY = new ArrayList<Field<?>>();

	private Boolean isEmbed;

	private List<Field<?>> fields;

	public BeanFields(Boolean isEmbed) {
		super();
		this.isEmbed = isEmbed;
	}

	public BeanFields(Boolean isEmbed, List<Field<?>> field) {
		super();
		this.isEmbed = isEmbed;
		this.fields = field;
	}

	public BeanFields add(Field<?> field) {
		if (this.fields == null) {
			this.fields = new ArrayList<Field<?>>();
		}
		this.fields.add(field);
		return this;
	}

	public BeanFields add(Fields fields) {
		for (Field<?> each : fields) {
			this.add(each);
		}
		return this;
	}

	public BeanFields add(List<Field<?>> fields) {
		for (Field<?> each : fields) {
			this.add(each);
		}
		return this;
	}

	public List<Field<?>> getFields() {
		return this.fields;
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.fields != null ? this.fields.iterator() : EMPTY.iterator();
	}

	@Override
	public Boolean isEmbed() {
		return this.isEmbed;
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		for (Field<?> each : this) {
			if (each.getName().equals(name)) {
				return clazz.cast(each);
			}
		}
		return null;
	}
}
