package com.sissi.ucenter.history;

import com.sissi.protocol.offline.History.HistoryDirection;

/**
 * @author kim 2014年3月6日
 */
public interface HistoryQuery {

	public int limit(int limit, int def);

	public long since(long limit, long def);
	
	public boolean direction(HistoryDirection direction);
}
