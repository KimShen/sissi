package com.sissi.connector;

import java.util.concurrent.Future;

import com.sissi.feed.Feeder;

/**
 * @author kim 2013-10-30
 */
public interface ConnectorBuilder {

	public Connector builder(Future<?> future, Feeder feeder);
}
