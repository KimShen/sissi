package com.sisi.feed;

import java.util.concurrent.Future;

/**
 * @author kim 2013-10-30
 */
public interface ConnectorBuilder {

	public Connector builder(Future<Object> future, Feeder feeder);
}
