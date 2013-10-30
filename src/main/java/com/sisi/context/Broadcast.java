package com.sisi.context;

import com.sisi.protocol.core.Message;

/**
 * @author kim 2013-10-27
 */
public interface Broadcast {

	public void broadcast(String message);
	
	public void broadcast(Message message);
}
