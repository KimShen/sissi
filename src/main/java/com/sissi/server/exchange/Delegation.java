package com.sissi.server.exchange;

import java.io.OutputStream;

/**
 * @author kim 2014年2月26日
 */
public interface Delegation {

	public OutputStream allocate(String sid);

	public Delegation recover(Exchanger exchanger);
}
