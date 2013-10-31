package com.sisi.connector;

import java.util.concurrent.Future;

import com.sisi.feed.Feeder;

/**
 * @author kim 2013-10-30
 */
public interface ConnectorBuilder {

	public Connector builder(Future<?> future, Feeder feeder);
}
