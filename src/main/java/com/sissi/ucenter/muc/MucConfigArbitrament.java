package com.sissi.ucenter.muc;

/**
 * @author kim 2014年3月5日
 */
public interface MucConfigArbitrament {

	public boolean arbitrate(MucConfigParam param, Object request);

	public String support();
}
