package com.sissi.ucenter.muc;

import java.util.Set;

/**
 * @author kim 2014年3月5日
 */
public interface MucStatus {

	public boolean hidden();

	public boolean owner();

	public MucItem getItem();

	public MucStatus add(String code);

	public MucStatus add(Set<String> codes);

	public <T extends MucStatus> T cast(Class<T> clazz);
}
