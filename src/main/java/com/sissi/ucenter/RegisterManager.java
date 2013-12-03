package com.sissi.ucenter;

import java.util.List;

import com.sissi.protocol.iq.login.Register.Field;

/**
 * @author kim 2013年12月3日
 */
public interface RegisterManager {

	public Boolean register(List<Field> fields);
}
