package com.sissi.ucenter.vcard;

import java.util.ArrayList;
import java.util.List;

import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月11日
 */
public class GenericityVCardField<T> implements Field<T> {

	private List<Field<?>> children;

	private String name;

	private T value;

	public GenericityVCardField(String name, T value) {
		super();
		this.name = name;
		this.value = value;
	}

	public GenericityVCardField<T> setName(String name) {
		this.name = name;
		return this;
	}

	public GenericityVCardField<T> setValue(T value) {
		this.value = value;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	public GenericityVCardField<T> add(Field<?> field) {
		if (this.children == null) {
			this.children = new ArrayList<Field<?>>();
		}
		this.children.add(field);
		return this;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return this.children != null;
	}
}
