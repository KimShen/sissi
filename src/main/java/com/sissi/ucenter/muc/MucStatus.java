package com.sissi.ucenter.muc;

/**
 * @author kim 2014年3月5日
 */
public interface MucStatus {

	public boolean hidden();

	public boolean owner();

	public MucItem item();

	public MucStatus add(String code);

	public <T extends MucStatus> T cast(Class<T> clazz);
}
