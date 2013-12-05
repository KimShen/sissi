package com.sissi.ucenter.impl;

import java.util.List;

import com.sissi.ucenter.RegisterContext.Field;
import com.sissi.ucenter.RegisterInvitationFieldsBuilder;

/**
 * @author kim 2013年12月5日
 */
public class ConfigRegisterInvitationFieldsBuilder implements RegisterInvitationFieldsBuilder {

	private List<Field> fields;

	public ConfigRegisterInvitationFieldsBuilder(List<Field> fields) {
		super();
		this.fields = fields;
	}

	@Override
	public List<Field> build() {
		return fields;
	}
}
