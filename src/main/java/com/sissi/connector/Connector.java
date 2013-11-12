package com.sissi.connector;

/**
 * @author kim 2013-10-30
 */
public interface Connector {

	public void start();

	public void stop();

	public Boolean isRunning();
}
