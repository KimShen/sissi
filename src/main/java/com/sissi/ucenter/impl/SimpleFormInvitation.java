package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.Password;
import com.sissi.protocol.iq.register.Username;
import com.sissi.ucenter.Field;
import com.sissi.ucenter.RegisterInvitation;

/**
 * @author kim 2013年12月4日
 */
public class SimpleFormInvitation implements RegisterInvitation {

	@SuppressWarnings("serial")
	private final static List<Field> FIELDS = new ArrayList<Field>() {
		{
			add(Username.FIELD);
			add(Password.FIELD);
		}
	};

	@Override
	public List<Field> build() {
		return FIELDS;
	}
}
