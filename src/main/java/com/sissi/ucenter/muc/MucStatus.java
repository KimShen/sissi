package com.sissi.ucenter.muc;

import java.util.Set;

/**
 * @author kim 2014年3月5日
 */
public interface MucStatus {

	public String group();

	public boolean owner();

	public boolean hidden();

	public boolean contain(String code);

	public MucItem getItem();

	public MucStatus clear();

	public MucStatus add(String code);

	public MucStatus add(Set<String> codes);

	public <T extends MucStatus> T cast(Class<T> clazz);
}
