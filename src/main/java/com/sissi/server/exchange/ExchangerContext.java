package com.sissi.server.exchange;

import com.sissi.pipeline.Transfer;

/**
 * @author kim 2013年12月22日
 */
public interface ExchangerContext {

	/**
	 * 接收方等待发起方激活
	 * 
	 * @param host
	 * @param cascade 级联关闭,如果为True则Exchange允许显式关闭接收方
	 * @param transfer
	 * @return
	 */
	public Exchanger wait(String host, boolean cascade, Transfer transfer);

	/**
	 * 激活
	 * 
	 * @param host
	 * @return 如果无法激活则返回Null
	 */
	public Exchanger activate(String host);

	/**
	 * 是否存在待激活的接收方
	 * 
	 * @param host
	 * @return
	 */
	public boolean exists(String host);
}
