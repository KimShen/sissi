package com.sissi.ucenter.vcard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月11日
 */
public class ListVCardFields implements Fields {

	private final static ArrayList<Field<?>> EMPTY = new ArrayList<Field<?>>();

	private Boolean isEmbed;

	private List<Field<?>> field;

	public ListVCardFields(Boolean isEmbed) {
		super();
		this.isEmbed = isEmbed;
	}

	public ListVCardFields(Boolean isEmbed, List<Field<?>> field) {
		super();
		this.isEmbed = isEmbed;
		this.field = field;
	}

	public ListVCardFields add(Field<?> field) {
		if (this.field == null) {
			this.field = new ArrayList<Field<?>>();
		}
		this.field.add(field);
		return this;
	}

	public ListVCardFields add(Fields fields) {
		for (Field<?> each : fields) {
			this.add(each);
		}
		return this;
	}

	public ListVCardFields add(List<Field<?>> fields) {
		for (Field<?> each : fields) {
			this.add(each);
		}
		return this;
	}

	public List<Field<?>> getFields() {
		return this.field != null ? this.field : EMPTY;
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.field != null ? this.field.iterator() : EMPTY.iterator();
	}

	@Override
	public Boolean isEmbed() {
		return isEmbed;
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
